package utils.exceptions;

public class CategoryException extends Exception {

	private static final long serialVersionUID = -6393854752968435672L;

	public CategoryException(String message)
	{
		super(message);
	}
	
	public CategoryException(String message, Throwable throwable)
	{
		super(message, throwable);
	}
	
}