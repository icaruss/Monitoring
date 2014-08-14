/*
 * 
 */
package unix;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

// TODO: Auto-generated Javadoc
/**
 * The Class UnixContext.
 * 
 * @author I064852
 */
public class UnixContext 
{
	
	/** The host name. */
	protected static String hostName;
	
	/** The user name. */
	protected static String userName;
	
	/** The password. */
	protected static String password;
	
	/** The jsch ssh channel. */
	protected static JSch jschSSHChannel;
	
	/** The ses connection. */
	protected static Session sesConnection;
	
	/** The int time out. */
	protected static int intTimeOut;
	
	
	
	/**
	 * Instantiates a new unix context.
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
	public UnixContext(String hostName, String userName, String password,
			JSch jschSSHChannel, Session sesConnection, int intTimeOut) 
	{
		super();
		UnixContext.hostName = hostName;
		UnixContext.userName = userName;
		UnixContext.password = password;
		UnixContext.jschSSHChannel = jschSSHChannel;
		UnixContext.sesConnection = sesConnection;
		UnixContext.intTimeOut = intTimeOut;
	}
	
	
	/**
	 * Gets the host name.
	 * 
	 * @return the host name
	 */
	public String getHostName() 
	{
		return hostName;
	}
	
	/**
	 * Sets the host name.
	 * 
	 * @param hostName
	 *            the new host name
	 */
	public void setHostName(String hostName) 
	{
		UnixContext.hostName = hostName;
	}
	
	/**
	 * Gets the user name.
	 * 
	 * @return the user name
	 */
	public String getUserName() 
	{
		return userName;
	}
	
	/**
	 * Sets the user name.
	 * 
	 * @param userName
	 *            the new user name
	 */
	public void setUserName(String userName) 
	{
		UnixContext.userName = userName;
	}
	
	/**
	 * Gets the password.
	 * 
	 * @return the password
	 */
	public String getPassword() 
	{
		return password;
	}
	
	/**
	 * Sets the password.
	 * 
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) 
	{
		UnixContext.password = password;
	}
	
	/**
	 * Gets the jsch ssh channel.
	 * 
	 * @return the jsch ssh channel
	 */
	public JSch getJschSSHChannel() 
	{
		return jschSSHChannel;
	}
	
	/**
	 * Sets the jsch ssh channel.
	 * 
	 * @param jschSSHChannel
	 *            the new jsch ssh channel
	 */
	public void setJschSSHChannel(JSch jschSSHChannel) 
	{
		UnixContext.jschSSHChannel = jschSSHChannel;
	}
	
	/**
	 * Gets the ses connection.
	 * 
	 * @return the ses connection
	 */
	public Session getSesConnection() 
	{
		return sesConnection;
	}
	
	/**
	 * Sets the ses connection.
	 * 
	 * @param sesConnection
	 *            the new ses connection
	 */
	public void setSesConnection(Session sesConnection) 
	{
		UnixContext.sesConnection = sesConnection;
	}
	
	/**
	 * Gets the int time out.
	 * 
	 * @return the int time out
	 */
	public int getIntTimeOut() 
	{
		return intTimeOut;
	}
	
	/**
	 * Sets the int time out.
	 * 
	 * @param intTimeOut
	 *            the new int time out
	 */
	public void setIntTimeOut(int intTimeOut) 
	{
		UnixContext.intTimeOut = intTimeOut;
	}

}
