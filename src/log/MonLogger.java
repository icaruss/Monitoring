/*
 * 
 */
package log;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class MonLogger.
 */
public class MonLogger
{
	
	/** The Constant myLogger. */
	public final static Logger myLogger = Logger.getLogger("Logger:");  
	
	/** The self. */
	private static MonLogger self = null;
	
/*	//empty private constructor
	private MonLogger()
	{
	
	}*/
	
	//synchronized getInstance
	/**
 * Gets the single instance of MonLogger.
 * 
 * @return single instance of MonLogger
 */
public static synchronized MonLogger getInstance(){
		if (self == null)
		{
			self = new MonLogger();
			prepareLogger();

		}

		return self;
	}
	

	
	/**
	 * Prepare logger.
	 */
	private static void prepareLogger() 
	{  
		try 
		{
			String dir = System.getProperty("user.dir") + "\\Logs\\monLog.log";

			File saveDir = new File(dir);
			{
					//Here comes the existence check
					if(!saveDir.exists())
					  saveDir.mkdirs();
			}
			FileHandler myFileHandler = new FileHandler(dir, 102400, 10, true); 
			myLogger .addHandler(myFileHandler);  
			myLogger .setUseParentHandlers(false);  
			myLogger .setLevel(Level.FINEST);
		} catch (SecurityException e) {
			myLogger.log(Level.WARNING, e.getMessage());
			myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
		} catch (IOException e) {
			myLogger.log(Level.WARNING, e.getMessage());
			myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
		}  
	}  
	
	//prevent cloning
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException(); 
	}
	
	//synchronized logging
	/**
	 * Debug.
	 * 
	 * @param msg
	 *            the msg
	 */
	public synchronized void debug(String msg){
	
	}
	
	/**
	 * Info.
	 * 
	 * @param msg
	 *            the msg
	 */
	public synchronized void info(String msg)
	{
	
	}
	
	/**
	 * Fatal.
	 * 
	 * @param msg
	 *            the msg
	 */
	public synchronized void fatal(String msg)
	{
	
	}

}
