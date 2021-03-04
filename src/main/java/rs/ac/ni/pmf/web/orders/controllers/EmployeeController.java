package rs.ac.ni.pmf.web.orders.controllers;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
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
	public List<EmployeeDTO> getEmployees()
	{
		return _employeeRepository.findAll().stream()
			.map(EmployeeMapper::toDto)
			.collect(Collectors.toList());
	}

	//	@RequestMapping(path = "/employees/{id}", method = RequestMethod.GET)
	@GetMapping("/employees/{id}")
	public EmployeeDTO getEmployee(@PathVariable final long id)
	{
		return _employeeRepository.findById(id)
			.map(EmployeeMapper::toDto)
			.orElseThrow(() -> new RuntimeException("Employee with id " + id + " not found"));
	}

	@PostMapping("/employees")
	@ResponseStatus(code = HttpStatus.CREATED)
	public EmployeeDTO addEmployee(@RequestBody final EmployeeDTO employee)
	{
		final EmployeeEntity entity = EmployeeMapper.toEntity(employee);
		final EmployeeEntity savedEntity = _employeeRepository.save(entity);
		return EmployeeMapper.toDto(savedEntity);
	}
}
