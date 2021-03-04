package rs.ac.ni.pmf.web.orders.model.entity;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "employees")
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	String name;

	String role;
}
