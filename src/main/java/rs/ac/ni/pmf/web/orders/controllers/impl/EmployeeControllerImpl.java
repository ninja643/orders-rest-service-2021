package rs.ac.ni.pmf.web.orders.controllers.impl;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import rs.ac.ni.pmf.web.orders.controllers.EmployeeController;
import rs.ac.ni.pmf.web.orders.controllers.EntityModelBuilder;
import rs.ac.ni.pmf.web.orders.exceptions.EmployeeNotFoundException;
import rs.ac.ni.pmf.web.orders.model.dto.EmployeeDTO;
import rs.ac.ni.pmf.web.orders.model.entity.EmployeeEntity;
import rs.ac.ni.pmf.web.orders.model.mappers.EmployeeMapper;
import rs.ac.ni.pmf.web.orders.repositories.EmployeeRepository;

@RestController
@RequiredArgsConstructor
public class EmployeeControllerImpl implements EmployeeController
{
	private final EmployeeRepository _employeeRepository;

	@Override
	public CollectionModel<EntityModel<EmployeeDTO>> getEmployees()
	{
		final List<EntityModel<EmployeeDTO>> models = _employeeRepository.findAll().stream()
			.map(EmployeeMapper::toDto)
			.map(EntityModelBuilder::buildEmployeeEntityModel)
			.collect(Collectors.toList());

		return CollectionModel.of(
			models,
			linkTo(methodOn(EmployeeControllerImpl.class).getEmployees()).withSelfRel()
		);
	}

	@Override
	public EntityModel<EmployeeDTO> getEmployee(final long id)
	{
		final EmployeeDTO dto = EmployeeMapper.toDto(findEmployee(id));
		return EntityModelBuilder.buildEmployeeEntityModel(dto);
	}

	@Override
	public ResponseEntity<?> addEmployee(final EmployeeDTO employee)
	{
		final EmployeeEntity entity = EmployeeMapper.toEntity(employee);
		final EmployeeEntity savedEntity = _employeeRepository.save(entity);
		final EntityModel<EmployeeDTO> body = EntityModelBuilder.buildEmployeeEntityModel(EmployeeMapper.toDto(savedEntity));

		return ResponseEntity
			.created(body.getRequiredLink(IanaLinkRelations.SELF).toUri())
			.body(body);
	}

	@Override
	public void updateEmployee(final long id, final EmployeeDTO employee)
	{
		final EmployeeEntity entity = findEmployee(id);

		entity.setName(employee.getName());
		entity.setRole(employee.getRole());
		_employeeRepository.save(entity);
	}

	@Override
	public ResponseEntity<?> deleteEmployee(final long id)
	{
		final EmployeeEntity entity = findEmployee(id);
		_employeeRepository.delete(entity);

		return ResponseEntity.noContent().build();
	}

	private EmployeeEntity findEmployee(final long id)
	{
		return _employeeRepository.findById(id)
			.orElseThrow(() -> new EmployeeNotFoundException(id));
	}
}
