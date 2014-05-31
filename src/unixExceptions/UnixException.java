package unixExceptions;

public abstract class UnixException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2355038524612357614L;
	
	
	protected String _errorCode;


	public UnixException() {
		super();
		// TODO Auto-generated constructor stub
	}


	public UnixException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}


	public UnixException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}


	public UnixException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}


	public UnixException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	

	/**
	 * @param message already formated message string
	 * */

}
