package rs.ac.ni.pmf.web.orders.model.dto;

import org.springframework.hateoas.server.core.Relation;
import lombok.Builder;
import lombok.Value;
import rs.ac.ni.pmf.web.orders.model.OrderStatus;

@Value
@Builder
@Relation(collectionRelation = "orders", itemRelation = "order")
public class OrderDTO
{
	Long id;

	String description;

	OrderStatus status;

	Integer quantity;
}
