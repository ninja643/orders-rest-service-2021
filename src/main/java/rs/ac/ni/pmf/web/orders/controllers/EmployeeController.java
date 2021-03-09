package rs.ac.ni.pmf.web.orders.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import rs.ac.ni.pmf.web.orders.exceptions.EmployeeNotFoundException;
import rs.ac.ni.pmf.web.orders.model.dto.EmployeeDTO;
import rs.ac.ni.pmf.web.orders.model.entity.EmployeeEntity;
import rs.ac.ni.pmf.web.orders.model.mappers.EmployeeMapper;
import rs.ac.ni.pmf.web.orders.repositories.EmployeeRepository;

@RestController
@RequiredArgsConstructor
public class EmployeeController
{
	private final EmployeeRepository _employeeRepository;

	@GetMapping("/employees")
	public CollectionModel<EntityModel<EmployeeDTO>> getEmployees()
	{
		final List<EntityModel<EmployeeDTO>> models = _employeeRepository.findAll().stream()
			.map(EmployeeMapper::toDto)
			.map(EntityModelBuilder::buildEmployeeEntityModel)
			.collect(Collectors.toList());

		return CollectionModel.of(
			models,
			linkTo(methodOn(EmployeeController.class).getEmployees()).withSelfRel()
		);
	}

	//	@RequestMapping(path = "/employees/{id}", method = RequestMethod.GET)
	@GetMapping("/employees/{id}")
	public EntityModel<EmployeeDTO> getEmployee(@PathVariable final long id)
	{
		final EmployeeDTO dto = EmployeeMapper.toDto(findEmployee(id));
		return EntityModelBuilder.buildEmployeeEntityModel(dto);
	}

	@PostMapping("/employees")
	@ResponseStatus(code = HttpStatus.CREATED)
	public EmployeeDTO addEmployee(@RequestBody final EmployeeDTO employee)
	{
		final EmployeeEntity entity = EmployeeMapper.toEntity(employee);
		final EmployeeEntity savedEntity = _employeeRepository.save(entity);
		return EmployeeMapper.toDto(savedEntity);
	}

	@PutMapping("/employees/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateEmployee(@PathVariable final long id, @RequestBody final EmployeeDTO employee)
	{
		final EmployeeEntity entity = findEmployee(id);

		entity.setName(employee.getName());
		entity.setRole(employee.getRole());
		_employeeRepository.save(entity);
	}

	@DeleteMapping("/employees/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteEmployee(@PathVariable final long id)
	{
		final EmployeeEntity entity = findEmployee(id);

		_employeeRepository.delete(entity);
		//		_employeeRepository.deleteById(id);
	}

	private EmployeeEntity findEmployee(final long id)
	{
		return _employeeRepository.findById(id)
			.orElseThrow(() -> new EmployeeNotFoundException(id));
	}
}
