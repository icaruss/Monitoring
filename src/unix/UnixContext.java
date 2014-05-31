package unix;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/**
 * @author I064852
 *
 */
public class UnixContext 
{
	protected static String hostName;
	protected static String userName;
	protected static String password;
	protected static JSch jschSSHChannel;
	protected static Session sesConnection;
	protected static int intTimeOut;
	
	
	
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
	
	
	public String getHostName() 
	{
		return hostName;
	}
	public void setHostName(String hostName) 
	{
		UnixContext.hostName = hostName;
	}
	public String getUserName() 
	{
		return userName;
	}
	public void setUserName(String userName) 
	{
		UnixContext.userName = userName;
	}
	public String getPassword() 
	{
		return password;
	}
	public void setPassword(String password) 
	{
		UnixContext.password = password;
	}
	public JSch getJschSSHChannel() 
	{
		return jschSSHChannel;
	}
	public void setJschSSHChannel(JSch jschSSHChannel) 
	{
		UnixContext.jschSSHChannel = jschSSHChannel;
	}
	public Session getSesConnection() 
	{
		return sesConnection;
	}
	public void setSesConnection(Session sesConnection) 
	{
		UnixContext.sesConnection = sesConnection;
	}
	public int getIntTimeOut() 
	{
		return intTimeOut;
	}
	public void setIntTimeOut(int intTimeOut) 
	{
		UnixContext.intTimeOut = intTimeOut;
	}

}
