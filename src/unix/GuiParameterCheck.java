package unix;

import java.util.logging.Level;

import log.MonLogger;

import com.jcraft.jsch.JSchException;

public class GuiParameterCheck 
{
	String instance;
	String hostName;
	String userName;
	String password;
//	UnixConnection unixConnection;
	CommandExecuter commandExecuter;
	
	public GuiParameterCheck(String instance, String hostName, String userName,
			String password) {
		super();
		this.instance = instance;
		this.hostName = hostName;
		this.userName = userName;
		this.password = password;
	//	this.unixConnection = new UnixConnection(hostName, userName, password, null, null, 0);
		this.commandExecuter = new CommandExecuter(hostName, userName, password, null, null, 0);
		
	}



	
	public boolean instaceExist() throws JSchException
	{
		Boolean TF = true;
		String msg = null;
		instance = instance.toUpperCase();
		commandExecuter =  new CommandExecuter();
		
		msg = commandExecuter.execute("cd /usr/sap/" + instance);
		if (msg.trim().length() != 0)
		{
			TF= false;
			//TODO: Redirect the output string to the GUI
		}
		
		return TF;
		
	}
	
	public boolean mdsStarted() throws JSchException
	{
		Boolean TF = true;
		String msg = null;
		instance = instance.toUpperCase();
		
		CommandExecuter command =  new CommandExecuter();
		msg = command.execute("ps -ef|grep mds-r | grep " + instance);
		if (msg.trim().length() == 0)
		{
			TF= false;
			//TODO: Redirect the output string to the GUI
			System.out.println("MDS is not started, please start MDS!");
		}
		
		return TF;
		
	}
	
	public boolean mdisStarted() throws JSchException
	{
		Boolean TF = true;
		String msg = null;
		instance = instance.toUpperCase();
		
		CommandExecuter command =  new CommandExecuter();
		msg = command.execute("ps -ef|grep mdis-r | grep " + instance);
		if (msg.trim().length() == 0)
		{
			TF= false;
			//TODO: Redirect the output string to the GUI
			System.out.println("MDIS is not started, please start MDIS!");
		}
		
		return TF;
		
	}
	
	public boolean mdssStarted() throws JSchException
	{
		Boolean TF = true;
		String msg = null;
		instance = instance.toUpperCase();
		
		CommandExecuter command =  new CommandExecuter();
		msg = command.execute("ps -ef|grep mdss-r  | grep " + instance);
		if (msg.trim().length() == 0)
		{
			TF= false;
			//TODO: Redirect the output string to the GUI
			System.out.println("MDSS is not started, please start MDSS!");
		}
		return TF;
		
	}
	
	
	// If connection successful the return string will be null
	public boolean connectionTestSuccess()
	{
		String msg = null;
		boolean TF = true;
		try
		{
			msg = UnixConnection.connectToUnix();
			
			if (msg != null)
			{
				TF= false;
				//TODO: Redirect the output string to the GUI
				System.out.println(msg);
			}
		}
		catch(JSchException e)
		{
    		MonLogger.myLogger.log(Level.WARNING, e.getMessage());
    		MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
		}
		return TF;
		
	}
	
	
	public Boolean mainGuiCheck()
	{
		
		Boolean TF = true;
		try 
		{
			if (connectionTestSuccess() == true)
			{
				MonLogger.myLogger.log(Level.INFO,  "Connection Test was successful");
			}
			else
			{
				MonLogger.myLogger.log(Level.INFO,  "Connection Test was NOT successful");
				TF = false;
			}
			
			if (instaceExist() == true)
			{
				MonLogger.myLogger.log(Level.INFO,  "Instance exist");
			}
			else
			{
				MonLogger.myLogger.log(Level.INFO,  "Instance does NOT exist!");
				TF = false;
			}
			if (mdsStarted() == true)
			{
				MonLogger.myLogger.log(Level.INFO,  "MDS is started!");
			}
			else 
			{
				MonLogger.myLogger.log(Level.INFO,  "MDS is NOT started!");
				MonLogger.myLogger.log(Level.INFO,  "Start before proceeding or uncheck the 'mds-r' radiobutton!");
				TF = false;
			}
			if (mdisStarted() == true)
			{
				MonLogger.myLogger.log(Level.INFO,  "MDIS is started!");
			}
			else 
			{
				MonLogger.myLogger.log(Level.INFO,  "MDIS is NOT started!");
				MonLogger.myLogger.log(Level.INFO, "Start before proceeding or uncheck the 'mdis-r' radiobutton!");
				TF = false;
			}
			if (mdssStarted() == true)
				MonLogger.myLogger.log(Level.INFO,  "MDSS is started!");
			else {
				MonLogger.myLogger.log(Level.INFO,"MDSS is NOT started!");
				MonLogger.myLogger.log(Level.INFO,"Start before proceeding or uncheck the 'mdss-r' radiobutton!");
				TF = false;
			}
		} 
		catch (JSchException e) 
		{
    		MonLogger.myLogger.log(Level.WARNING, e.getMessage());
    		MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
		}
		return TF;
	}
}
