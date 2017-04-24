package utils;

public class UserException extends Exception {

	private static final long serialVersionUID = -6393854752968435672L;

	public UserException(String message)
	{
		super(message);
	}
	
	public UserException(String message, Throwable throwable)
	{
		super(message, throwable);
	}
	
}
