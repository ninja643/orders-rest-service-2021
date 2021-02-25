package rs.ac.ni.pmf.web.orders.controllers;

import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import rs.ac.ni.pmf.web.orders.model.dto.EmployeeDTO;

@RestController
public class EmployeeController
{
	@GetMapping("/employees")
	public List<EmployeeDTO> getEmployees()
	{
		return Arrays.asList(
			EmployeeDTO.builder().id(1L).name("Test1").role("User").build(),
			EmployeeDTO.builder().id(2L).name("Test2").role("Admin").build());
	}

//	@RequestMapping(path = "/employees/{id}", method = RequestMethod.GET)
	@GetMapping("/employees/{id}")
	public EmployeeDTO getEmployee(@PathVariable final long id)
	{
		return EmployeeDTO.builder().id(id).name("Test").role("User").build();
	}
}
