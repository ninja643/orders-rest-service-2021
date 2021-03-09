package rs.ac.ni.pmf.web.orders.exceptions;

public class OrderNotFoundException extends RuntimeException
{
	public OrderNotFoundException(final Long id)
	{
		super("Order with id '" + id + "' does not exist");
	}
}
