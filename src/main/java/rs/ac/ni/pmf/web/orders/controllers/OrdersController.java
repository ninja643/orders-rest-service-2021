package rs.ac.ni.pmf.web.orders.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.hateoas.*;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import rs.ac.ni.pmf.web.orders.exceptions.ApiError;
import rs.ac.ni.pmf.web.orders.exceptions.OrderNotFoundException;
import rs.ac.ni.pmf.web.orders.model.OrderStatus;
import rs.ac.ni.pmf.web.orders.model.dto.OrderDTO;
import rs.ac.ni.pmf.web.orders.model.entity.OrderEntity;
import rs.ac.ni.pmf.web.orders.model.mappers.OrderMapper;
import rs.ac.ni.pmf.web.orders.repositories.OrderRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class OrdersController
{
	private final OrderRepository _orderRepository;

	@GetMapping("/orders/{id}")
	public EntityModel<OrderDTO> getOrder(@PathVariable final Long id)
	{
		final OrderEntity orderEntity = findOrder(id);

		return EntityModelBuilder.buildOrderEntityModel(OrderMapper.toDto(orderEntity));
	}

	@GetMapping("/orders")
	public CollectionModel<EntityModel<OrderDTO>> getAllOrders()
	{
		final List<EntityModel<OrderDTO>> orders = _orderRepository.findAll().stream()
			.map(OrderMapper::toDto)
			.map(EntityModelBuilder::buildOrderEntityModel)
			.collect(Collectors.toList());

		return CollectionModel.of(
			orders,
			linkTo(methodOn(OrdersController.class).getAllOrders()).withSelfRel());
	}

	@PostMapping("/orders")
	public ResponseEntity<EntityModel<OrderDTO>> createNewOrder(@RequestBody final OrderDTO orderDTO)
	{
		final OrderEntity orderEntity = OrderMapper.toEntity(orderDTO);
		orderEntity.setStatus(OrderStatus.IN_PROGRESS);

		final OrderEntity savedEntity = _orderRepository.save(orderEntity);

		return ResponseEntity
			.created(linkTo(methodOn(OrdersController.class).getOrder(savedEntity.getId())).toUri())
			.body(EntityModelBuilder.buildOrderEntityModel(OrderMapper.toDto(savedEntity)));
	}

	@PutMapping("/orders/{id}/complete")
	public ResponseEntity<?> complete(@PathVariable final Long id)
	{
		final OrderEntity orderEntity = findOrder(id);

		if (orderEntity.getStatus() == OrderStatus.IN_PROGRESS)
		{
			orderEntity.setStatus(OrderStatus.COMPLETED);
			final OrderEntity completedOrder = _orderRepository.save(orderEntity);
			return ResponseEntity.ok(EntityModelBuilder.buildOrderEntityModel(OrderMapper.toDto(completedOrder)));
		}

		return ResponseEntity
			.status(HttpStatus.METHOD_NOT_ALLOWED)
			.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
			.body(ApiError.builder().code(ApiError.ErrorCode.ORDER_NOT_COMPLETABLE)
				.message("Cannot complete the order with id '" + id + "' since it is in status '" + orderEntity.getStatus() +
					"'")
				.build());
	}

	@PutMapping("/orders/{id}/cancel")
	public ResponseEntity<?> cancel(@PathVariable final Long id)
	{
		final OrderEntity orderEntity = findOrder(id);

		if (orderEntity.getStatus() == OrderStatus.IN_PROGRESS)
		{
			orderEntity.setStatus(OrderStatus.CANCELED);
			final OrderEntity completedOrder = _orderRepository.save(orderEntity);
			return ResponseEntity.ok(EntityModelBuilder.buildOrderEntityModel(OrderMapper.toDto(completedOrder)));
		}

		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("ERROR_CODE", ApiError.ErrorCode.ORDER_NOT_CANCELABLE);

		return ResponseEntity
			.status(HttpStatus.METHOD_NOT_ALLOWED)
			.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
			.body(Problem.create()
				.withProperties(parameters)
				.withTitle("Order not cancelable")
				.withDetail("Cannot cancel the order with id '" + id + "' since it is in status '" + orderEntity.getStatus() +
					"'")
			);
	}

	private OrderEntity findOrder(Long id)
	{
		return _orderRepository.findById(id)
			.orElseThrow(() -> new OrderNotFoundException(id));
	}
}
