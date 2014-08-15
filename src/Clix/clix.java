/*
 * 
 */
package Clix;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import log.MonLogger;
import com.jcraft.jsch.JSchException;
import unix.winUnixOperations;

// TODO: Auto-generated Javadoc
/**
 * The Class clix.
 */
public class clix extends winUnixOperations
{
	// SP06 and above - START
	//---------------------------------------------------------------------
	// default port: "clix mdsMonitor localhost Admin: -W -C -T 1 >> clix_mon.out 2>&1"
	/** The interval. */
	String interval;
	
	/** The clix cmd. */
	String clixCmd ;
	
	/** The port. */
	String port;
	
	/** The clix file name. */
	final String clixFileName = "clix_mon.out";
	
	/**
	 * Instantiates a new clix.
	 */
	public clix()
	{

	}
	
	// not default port: "clix mdsMonitor localhost Admin: -W -C -T 1 -# <50650> >> clix_mon.out 2>&1"

	/**
	 * Instantiates a new clix.
	 * 
	 * @param _interval
	 *            the _interval
	 * @param _port
	 *            the _port
	 * @param _hostName
	 *            the _host name
	 * @param _password
	 *            the _password
	 */
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

	/**
	 * Gets the clix cmd.
	 * 
	 * @return the clix cmd
	 */
	public String getClixCmd() {
		return clixCmd;
	}

	/**
	 * Run clix.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws JSchException
	 *             the j sch exception
	 */
	public void runClix() throws IOException, JSchException
	{
		 FileWriter writer = new FileWriter("clixMon.sh");	
		 writer.write(clixCmd);
		 writer.close();			 
		 copyToUnix("clixMon.sh");
		 this.execute("chmod 775 clixMon.sh");
		 this.executeShell("nohup ./clixMon.sh > /dev/null 2>&1 &");

		
	}
	
	/**
	 * Stop clix.
	 */
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
	
	
	

	 /**
	 * Kill processes.
	 */
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
	
	
	
	
	
	
	
	

