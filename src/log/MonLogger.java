package log;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MonLogger
{
	public final static Logger myLogger = Logger.getLogger("Logger:");  
	private static MonLogger self = null;
	
/*	//empty private constructor
	private MonLogger()
	{
	
	}*/
	
	//synchronized getInstance
	public static synchronized MonLogger getInstance(){
		if (self == null)
		{
			self = new MonLogger();
			prepareLogger();

		}

		return self;
	}
	

	
	private static void prepareLogger() 
	{  
		try 
		{
			String dir = System.getProperty("user.dir") + "\\Logs\\";

			File saveDir = new File(dir);
			{
					//Here comes the existence check
					if(!saveDir.exists())
					  saveDir.mkdirs();
			}
			FileHandler myFileHandler = new FileHandler(dir, 102400, 10, true);  
			myFileHandler.setFormatter(new SimpleFormatter()); 
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
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException(); 
	}
	
	//synchronized logging
	public synchronized void debug(String msg){
	
	}
	
	public synchronized void info(String msg)
	{
	
	}
	
	public synchronized void fatal(String msg)
	{
	
	}

}
