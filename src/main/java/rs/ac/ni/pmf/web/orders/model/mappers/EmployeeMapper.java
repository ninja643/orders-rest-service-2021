package rs.ac.ni.pmf.web.orders.model.mappers;

import rs.ac.ni.pmf.web.orders.model.dto.EmployeeDTO;
import rs.ac.ni.pmf.web.orders.model.entity.EmployeeEntity;

public class EmployeeMapper
{
	public static final String SEPARATOR = ":";

	public static EmployeeDTO toDto(final EmployeeEntity employeeEntity)
	{
		final String[] nameParts = employeeEntity.getName().split(SEPARATOR);

		return EmployeeDTO.builder()
			.id(employeeEntity.getId())
			.name(employeeEntity.getName().replace(SEPARATOR, " "))
			.role(employeeEntity.getRole())
			.firstName(nameParts[0])
			.lastName(nameParts.length > 1 ? nameParts[1] : null)
			.build();
	}

	public static EmployeeEntity toEntity(final EmployeeDTO employeeDTO)
	{
		final String fullName = employeeDTO.getName() != null ?
			employeeDTO.getName().replace(" ", SEPARATOR) :
			employeeDTO.getFirstName() + SEPARATOR + employeeDTO.getLastName();

		return EmployeeEntity.builder()
			.id(employeeDTO.getId())
			.name(fullName)
			.role(employeeDTO.getRole())
			.build();
	}
}
