package rs.ac.ni.pmf.web.orders.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.ni.pmf.web.orders.model.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>
{
}
