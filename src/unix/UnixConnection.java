/*
 * 
 */
package unix;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

// TODO: Auto-generated Javadoc
/**
 * The Class UnixConnection.
 */
public class UnixConnection extends UnixContext {

	/** The instance. */
	private static UnixConnection instance = null;

	/**
	 * Instantiates a new unix connection.
	 * 
	 * @param hostName
	 *            the host name
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param jschSSHChannel
	 *            the jsch ssh channel
	 * @param sesConnection
	 *            the ses connection
	 * @param intTimeOut
	 *            the int time out
	 */
	public UnixConnection(String hostName, String userName, String password,
			JSch jschSSHChannel, Session sesConnection, int intTimeOut) {
		super(hostName, userName, password, jschSSHChannel, sesConnection,
				intTimeOut);
	}

	/**
	 * Gets the single instance of UnixConnection.
	 * 
	 * @return single instance of UnixConnection
	 */
	public static UnixConnection getInstance() {
		if (instance == null) {
			synchronized (UnixConnection.class) {
				if (instance == null) {
					instance = new UnixConnection(hostName, userName, password,
							jschSSHChannel, sesConnection, intTimeOut);
				}
			}
		}
		return instance;
	}

	/**
	 * Connect to unix.
	 * 
	 * @return the string
	 * @throws JSchException
	 *             the j sch exception
	 */
	protected static String connectToUnix() throws JSchException {
		String errorMessage = null;

		try {
			jschSSHChannel = new JSch();
			sesConnection = jschSSHChannel.getSession(userName, hostName, 22);
			sesConnection.setPassword(password);
			// UNCOMMENT THIS FOR TESTING PURPOSES, BUT DO NOT USE IN PRODUCTION
			sesConnection.setConfig("StrictHostKeyChecking", "no");
			sesConnection.connect(intTimeOut);

		} catch (JSchException jschX) {
			errorMessage = jschX.getMessage();
		}

		return errorMessage;

	}

	/**
	 * Disconnect from unix.
	 * 
	 * @throws JSchException
	 *             the j sch exception
	 */
	protected static void disconnectFromUnix() throws JSchException {

		jschSSHChannel.getSession(hostName).disconnect();
		sesConnection.disconnect();

	}

}
