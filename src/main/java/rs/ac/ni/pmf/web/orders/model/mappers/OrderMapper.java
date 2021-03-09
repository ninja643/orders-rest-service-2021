package rs.ac.ni.pmf.web.orders.model.mappers;

import rs.ac.ni.pmf.web.orders.model.dto.OrderDTO;
import rs.ac.ni.pmf.web.orders.model.entity.OrderEntity;

public class OrderMapper
{
	public static OrderDTO toDto(final OrderEntity orderEntity)
	{
		return OrderDTO.builder()
			.id(orderEntity.getId())
			.description(orderEntity.getDescription())
			.status(orderEntity.getStatus())
			.build();
	}

	public static OrderEntity toEntity(final OrderDTO orderDTO)
	{
		return OrderEntity.builder()
			.id(orderDTO.getId())
			.description(orderDTO.getDescription())
			.status(orderDTO.getStatus())
			.build();
	}
}
