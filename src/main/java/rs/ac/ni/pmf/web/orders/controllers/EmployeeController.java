package rs.ac.ni.pmf.web.orders.controllers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import rs.ac.ni.pmf.web.orders.model.dto.EmployeeDTO;

@RequestMapping("/v1")
public interface EmployeeController
{
	@GetMapping("/employees")
	@Operation(summary = "Retrieve the list of all employees")
	CollectionModel<EntityModel<EmployeeDTO>> getEmployees();

	@GetMapping("/employees/{id}")
	@Operation(summary = "Retrieve employee details")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Found the employee"),
		@ApiResponse(responseCode = "404", description = "Employee not found")
	})
	EntityModel<EmployeeDTO> getEmployee(@Parameter(description = "ID of an employee") @PathVariable long id);

	@PostMapping("/employees")
	ResponseEntity<?> addEmployee(@RequestBody EmployeeDTO employee);

	@PutMapping("/employees/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void updateEmployee(@PathVariable long id, @RequestBody EmployeeDTO employee);

	@DeleteMapping("/employees/{id}")
	ResponseEntity<?> deleteEmployee(@PathVariable long id);
}
