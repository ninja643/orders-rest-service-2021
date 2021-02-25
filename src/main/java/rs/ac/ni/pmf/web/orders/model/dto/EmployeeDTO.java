package rs.ac.ni.pmf.web.orders.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeDTO
{
	Long id;
	String name;
	String role;
}
