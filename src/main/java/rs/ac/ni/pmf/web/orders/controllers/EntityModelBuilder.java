package rs.ac.ni.pmf.web.orders.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import rs.ac.ni.pmf.web.orders.model.dto.EmployeeDTO;

public class EntityModelBuilder
{
	public static EntityModel<EmployeeDTO> buildEmployeeEntityModel(final EmployeeDTO dto)
	{
		return EntityModel.of(
			dto,
			linkTo(methodOn(EmployeeController.class).getEmployee(dto.getId())).withSelfRel(),
			linkTo(methodOn(EmployeeController.class).getEmployees()).withRel("all-employees")
		);
	}
}
