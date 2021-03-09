package rs.ac.ni.pmf.web.orders.exceptions;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ApiError
{
	String message;
	ErrorCode code;

	public enum ErrorCode
	{
		EMPLOYEE_NOT_FOUND
	}
}
