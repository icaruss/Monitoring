package Clix;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.OutputStream;
import java.util.logging.Level;

import log.MonLogger;

import com.jcraft.jsch.JSchException;



import unix.CommandExecuter;
import unix.winUnixOperations;

public class clix extends winUnixOperations
{
	// SP06 and above - START
	//---------------------------------------------------------------------
	// default port: "clix mdsMonitor localhost Admin: -W -C -T 1 >> clix_mon.out 2>&1"
	String interval;
	String clixCmd ;
	String port;
	final String clixFileName = "clix_mon.out";
	
	public clix()
	{

	}
	
	// not default port: "clix mdsMonitor localhost Admin: -W -C -T 1 -# <50650> >> clix_mon.out 2>&1"

	public clix(String _interval, String _port, String _hostName, String _password) {
		super();
		
		interval = _interval;
		port = _port;
		hostName = _hostName;
		password = _password;
		
		if (port.equalsIgnoreCase("59950"))
		{
			clixCmd = "clix mdsMonitor localhost Admin: " + " -W -C -T " + interval + "  >> " + clixFileName  + " 2>&1";
		}
		else
		{
			clixCmd = "clix mdsMonitor localhost Admin: " + " -W -C -T " + interval + " -# " + port + " >> " + clixFileName + " 2>&1";
		}
			
	}

	public String getClixCmd() {
		return clixCmd;
	}

	public void runClix() throws IOException, JSchException
	{
		 FileWriter writer = new FileWriter("clixMon.sh");	
		 writer.write(clixCmd);
		 writer.close();			 
		 copyToUnix("clixMon.sh");
		 this.execute("chmod 775 clixMon.sh");
		 this.executeShell("nohup ./clixMon.sh > /dev/null 2>&1 &");

		
	}
	
	public void stopClix()
	{
		try
		{
			copyFromUnix(clixFileName);
			this.execute("rm clixMon.sh");
			this.execute("rm " + clixFileName);
		}
		catch (JSchException e) {
			MonLogger.myLogger.log(Level.WARNING, e.getMessage());
    		MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
			
		}
	}
	
	
	

	 public void killProcesses()
	 {

			 
	 }
	 

	 
	//---------------------------------------------------------------------
		// SP06 and above - END


	// SP05 and below - START
	//---------------------------------------------------------------------
	// default port
	
	
	
	// not default port
	
	
	
	//---------------------------------------------------------------------
	// SP05 and below - END



}
	
	
	
	
	
	
	
	

