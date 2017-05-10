package utils.exceptions;

public class ItemException extends Exception {

	private static final long serialVersionUID = -5607947055610830218L;

	public ItemException(String message)
	{
		super(message);
	}

	public ItemException(String message, Throwable throwable)
	{
		super(message, throwable);
	}

}
