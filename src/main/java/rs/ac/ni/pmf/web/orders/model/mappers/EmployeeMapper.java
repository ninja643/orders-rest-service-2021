package rs.ac.ni.pmf.web.orders.model.mappers;

import rs.ac.ni.pmf.web.orders.model.dto.EmployeeDTO;
import rs.ac.ni.pmf.web.orders.model.entity.EmployeeEntity;

public class EmployeeMapper
{
	public static EmployeeDTO toDto(final EmployeeEntity employeeEntity)
	{
		return EmployeeDTO.builder()
			.id(employeeEntity.getId())
			.name(employeeEntity.getName())
			.role(employeeEntity.getRole())
			.build();
	}

	public static EmployeeEntity toEntity(final EmployeeDTO employeeDTO)
	{
		return EmployeeEntity.builder()
			.id(employeeDTO.getId())
			.name(employeeDTO.getName())
			.role(employeeDTO.getRole())
			.build();
	}
}
