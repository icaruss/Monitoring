package unix;

import java.io.FileNotFoundException;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class UnixConnection extends UnixContext 
{
	private static UnixConnection instance = null;

	public UnixConnection(String hostName, String userName, String password,
			JSch jschSSHChannel, Session sesConnection, int intTimeOut) 
	{
		super(hostName, userName, password, jschSSHChannel, sesConnection, intTimeOut);
	}	
	
	
	public static UnixConnection getInstance() 
	{
        if (instance == null) 
        {
                synchronized (UnixConnection .class)
                {
                        if (instance == null) 
                        {
                                instance = new UnixConnection (hostName, userName,password,
                            			jschSSHChannel,sesConnection,intTimeOut);
                        }
              }
        }
        return instance;
	}
	
	
	
	 protected static String connectToUnix() throws JSchException
	 {	
		 String errorMessage = null;

	     try
	     {
	        jschSSHChannel = new JSch(); 
	        sesConnection = jschSSHChannel.getSession(userName, 
	            hostName, 22);
	        sesConnection.setPassword(password);
	        // UNCOMMENT THIS FOR TESTING PURPOSES, BUT DO NOT USE IN PRODUCTION
	        sesConnection.setConfig("StrictHostKeyChecking", "no");
	        sesConnection.connect(intTimeOut);
	        
	     }
	     catch(JSchException jschX)
	     {
	        errorMessage = jschX.getMessage();
	     }

	     return errorMessage;

	 }
	 
	 protected static void disconnectFromUnix() throws JSchException 
	 {
		 
		 jschSSHChannel.getSession(hostName).disconnect();
		 sesConnection.disconnect();

		 

	 }
	 

}
