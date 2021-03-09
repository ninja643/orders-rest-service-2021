package rs.ac.ni.pmf.web.orders.exceptions;

public class EmployeeNotFoundException extends RuntimeException
{
	public EmployeeNotFoundException(long id)
	{
		super("Employee with id " + id + " does not exist");
	}
}
