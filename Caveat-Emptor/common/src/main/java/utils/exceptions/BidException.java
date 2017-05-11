package utils.exceptions;

public class BidException extends Exception {

	private static final long serialVersionUID = -3433529517194145523L;

	public BidException(String message)
	{
		super(message);
	}

	public BidException(String message, Throwable throwable)
	{
		super(message, throwable);
	}

}

