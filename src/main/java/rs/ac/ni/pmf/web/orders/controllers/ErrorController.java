package rs.ac.ni.pmf.web.orders.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.ac.ni.pmf.web.orders.exceptions.ApiError;
import rs.ac.ni.pmf.web.orders.exceptions.EmployeeNotFoundException;

@ControllerAdvice
@ResponseBody
public class ErrorController
{
	@ExceptionHandler(EmployeeNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiError handleEmployeeNotFound(final EmployeeNotFoundException e)
	{
		return ApiError.builder()
			.code(ApiError.ErrorCode.EMPLOYEE_NOT_FOUND)
			.message(e.getMessage())
			.build();
	}
}
