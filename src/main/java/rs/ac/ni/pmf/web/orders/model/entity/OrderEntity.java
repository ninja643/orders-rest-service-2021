package rs.ac.ni.pmf.web.orders.model.entity;

import javax.persistence.*;
import lombok.*;
import rs.ac.ni.pmf.web.orders.model.OrderStatus;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	String description;

	OrderStatus status;
}
