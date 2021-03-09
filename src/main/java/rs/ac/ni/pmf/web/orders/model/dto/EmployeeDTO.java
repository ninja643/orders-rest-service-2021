package rs.ac.ni.pmf.web.orders.model.dto;

import org.springframework.hateoas.server.core.Relation;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Relation(collectionRelation = "employees", itemRelation = "employee")
public class EmployeeDTO
{
	Long id;
	String name;
	String role;
}
