package rs.ac.ni.pmf.web.orders.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import rs.ac.ni.pmf.web.orders.controllers.impl.EmployeeControllerImpl;
import rs.ac.ni.pmf.web.orders.model.OrderStatus;
import rs.ac.ni.pmf.web.orders.model.dto.EmployeeDTO;
import rs.ac.ni.pmf.web.orders.model.dto.OrderDTO;

public class EntityModelBuilder
{
	public static EntityModel<EmployeeDTO> buildEmployeeEntityModel(final EmployeeDTO dto)
	{
		return EntityModel.of(
			dto,
			linkTo(methodOn(EmployeeControllerImpl.class).getEmployee(dto.getId())).withSelfRel(),
			linkTo(methodOn(EmployeeControllerImpl.class).getEmployees()).withRel("all-employees")
		);
	}

	public static EntityModel<OrderDTO> buildOrderEntityModel(final OrderDTO orderDTO)
	{
		final Long orderId = orderDTO.getId();

		final EntityModel<OrderDTO> orderModel = EntityModel.of(
			orderDTO,
			linkTo(methodOn(OrdersController.class).getOrder(orderId)).withSelfRel(),
			linkTo(methodOn(OrdersController.class).getAllOrders()).withRel("all-orders"));

		if (orderDTO.getStatus() == OrderStatus.IN_PROGRESS)
		{
			orderModel.add(linkTo(methodOn(OrdersController.class).complete(orderId)).withRel("complete"));
			orderModel.add(linkTo(methodOn(OrdersController.class).cancel(orderId)).withRel("cancel"));
		}

		return orderModel;
	}
}
