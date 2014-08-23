/*
 * 
 */
package unixExceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class UnixException.
 */
public abstract class UnixException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2355038524612357614L;

	/** The _error code. */
	protected String _errorCode;

	/**
	 * Instantiates a new unix exception.
	 */
	public UnixException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new unix exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 * @param enableSuppression
	 *            the enable suppression
	 * @param writableStackTrace
	 *            the writable stack trace
	 */
	public UnixException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new unix exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public UnixException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new unix exception.
	 * 
	 * @param message
	 *            the message
	 */
	public UnixException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new unix exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public UnixException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 *            already formated message string
	 * */

}
