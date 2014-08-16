/*
 * 
 */
package viewLogic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;







import unix.ExecuteUnixOperations;
import viewLogic.CViewConstants.DirectoryDirection;
import log.MonLogger;
import FilesManagment.DateOperations;

import com.thoughtworks.xstream.XStream;


// TODO: Auto-generated Javadoc
/**
 * The Class CSharedInstance.
 */
public class CSharedInstance 
{
	
	/** The total seconds countdown. */
	public long totalSecondsCountdown;
	
	/** The seconds elapsed. */
	public int secondsElapsed;
	
	/** The current monitoring. */
	public CViewConstants.MonitorType currentMonitoring;
	
	/** The is monitoring started. */
	public boolean isMonitoringStarted;
	
	/** The is monitoring done. */
	public boolean isMonitoringDone;
	
	/** The monitoring start time. */
	public Time monitoringStartTime;
	
	/** The current configuration id. */
	private String currentConfigurationID;
	
	/** The configurations. */
	private Map<String, Map<String, Object>> configurations;
	
	/** The current data files id. */
	public String currentDataFilesID;
	
	/** The data files. */
	private Map<String, Vector<String>> dataFiles;
	
	/** The execute unix operations. */
	public ExecuteUnixOperations executeUnixOperations;
	
	
	/** The shared instance. */
	private static CSharedInstance sharedInstance = new CSharedInstance( );
		   
	/* A private Constructor prevents any other 
	 * class from instantiating.
	 */
	/**
	 * Instantiates a new c shared instance.
	 */
	private CSharedInstance()
	{ 
		configurations = new HashMap<String, Map<String, Object>>();
		currentConfigurationID = null;
		totalSecondsCountdown = -1;
		secondsElapsed = 0;
		isMonitoringStarted = false;
		isMonitoringDone = true;
		monitoringStartTime = null;
		executeUnixOperations = null;
		
		currentMonitoring = CViewConstants.MonitorType.MonitorTypeElse;
		
		dataFiles = new HashMap<String, Vector<String>>();
		
		deSeriallize();
		
		setDefaultConfiguration();
		
		loadDataFilesToLocalCache();
		
		if (dataFiles.isEmpty())
		{
			currentDataFilesID = null;
		}
		else
		{
			currentDataFilesID = (String) dataFiles.keySet().toArray()[0];
		}
		
	}


	/* Static 'instance' method */
	/**
	 * Gets the single instance of CSharedInstance.
	 * 
	 * @return single instance of CSharedInstance
	 */
	public static CSharedInstance getInstance( ) 
	{
		return sharedInstance;
	}
	

	/**
	 * Adds the new configuration.
	 * 
	 * @param key
	 *            the key
	 * @param values
	 *            the values
	 */
	public void addNewConfiguration(String key, Map<String, Object> values)
	{
		if (configurations.containsKey(key))
		{
			if (!values.isEmpty())
			{
				configurations.put(key, values);
				
				currentConfigurationID = key;
			}
		}
		else
		{
			if (configurations.size() >= 10)
			{
				Object[] oldValues = configurations.values().toArray();
				
				configurations.remove(oldValues[oldValues.length - 1]);
			}
			
			configurations.put(key, values);
			
			currentConfigurationID = key;
		}
	}
	
	
	
	/**
	 * Gets the chosen configuration.
	 * 
	 * @param key
	 *            the key
	 * @return the chosen configuration
	 */
	public Map<String, Object> getChosenConfiguration(String key)
	{
		if (configurations.containsKey(key))
		{
			return configurations.get(key);
		}
		
		return null;
	}
	
	/**
	 * Gets the all configuration keys.
	 * 
	 * @return the all configuration keys
	 */
	public Set<String> getAllConfigurationKeys()
	{
		return configurations.keySet();
	}
	
	
	/**
	 * Gets the current configuration.
	 * 
	 * @return the current configuration
	 */
	public  Map<String, Object> getCurrentConfiguration()
	{
		if (currentConfigurationID != null)
		{
			return getChosenConfiguration(currentConfigurationID);
		}
		
		return null;
	}
	
	

	/**
	 * Sets the default configuration.
	 */
	private void setDefaultConfiguration() 
	{
		if (!configurations.isEmpty())
		{
			return;
		}
		
		String configurationID = "Default";
		String port="59950";
	    Boolean clixOn = true;
	    String hostName = "ilsun45";
	    String userName = "mqmadm";
	    String password = "a2i2000!"; // Alina - ! is legal character
	    String instance = "default";
	    String interval = "5";
	    
	    Map<String, Object> defaultConfiguration = new HashMap<String, Object>();
	    defaultConfiguration.put(CViewConstants.CONFIGURATION_ID, configurationID);
	    defaultConfiguration.put(CViewConstants.HOSTNAME, hostName);
	    defaultConfiguration.put(CViewConstants.USERNAME, userName);
	    defaultConfiguration.put(CViewConstants.PASSWORD, password);
	    defaultConfiguration.put(CViewConstants.INSTANCE, instance);
	    defaultConfiguration.put(CViewConstants.INTERVAL, interval);
	    defaultConfiguration.put(CViewConstants.CLIX, clixOn);
	    defaultConfiguration.put(CViewConstants.PORT, port);
	    defaultConfiguration.put(CViewConstants.START, CViewConstants.START_IMMEDIATELY);
	    
	    configurations.put(configurationID, defaultConfiguration);
	    
	    currentConfigurationID = configurationID;
	}
	
	
	/**
	 * Save configurations.
	 */
	public void saveConfigurations()
	{
		
		if (!configurations.isEmpty())
		{
			try
			{
				String xmlString = seriallizeData();
				
				stringToDom(xmlString);
				
			}
			catch(Exception e)
			{
				
			}
			
			
		}
		
	}
	
	/**
	 * Seriallize data.
	 * 
	 * @return the string
	 */
	private String seriallizeData()
	{
		XStream xStream = new XStream();
		
		return xStream.toXML(configurations);
	}
	
	
	/**
	 * String to dom.
	 * 
	 * @param xmlSource
	 *            the xml source
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void stringToDom(String xmlSource) throws IOException 
	{
	    java.io.FileWriter fw = new java.io.FileWriter("Configurations.xml");
	    fw.write(xmlSource);
	    fw.close();
	}
	
	
	/**
	 * De seriallize.
	 */
	@SuppressWarnings("unchecked")
	private void deSeriallize()
	{
		XStream xStream = new XStream();
		
		try
		{
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader("Configurations.xml"));
			StringBuffer buff = new StringBuffer();
			String line;
			while((line = br.readLine()) != null)
			{
			   buff.append(line);
			}
			
			configurations = new HashMap<String, Map<String,Object>>((Map<String, Map<String,Object>>)xStream.fromXML(buff.toString()));
		}
		catch (Exception e)
		{
			@SuppressWarnings("unused")
			String str = e.getMessage();
		}
		
	}
	

	/**
	 * Save configurations.
	 * 
	 * @param currentSettings
	 *            the current settings
	 */
	public void saveConfigurations(Map<String, Object> currentSettings)
	{
		String key = (String) currentSettings.get(CViewConstants.CONFIGURATION_ID);
		configurations.put(key, currentSettings);
		
		currentConfigurationID = key;
		
		saveConfigurations();
		
	}
	

    
    /**
	 * Update waiting time for results.
	 * 
	 * @param fromTime
	 *            the from time
	 * @param toTime
	 *            the to time
	 */
    public void updateWaitingTimeForResults(String fromTime, String toTime) 
	{
    	//int seconds = 0;
    	
    	try
    	{
	    	/*String strFrom = fromTime.substring(17);
	    	String strTo = toTime.substring(17);
	    	
	    	int secondsFrom = Integer.parseInt(strFrom);
	    	int secondsTo = Integer.parseInt(strTo);
	    	
	    	if (secondsFrom != secondsTo)
	    		seconds += (60 - secondsFrom + secondsTo);
	    	
	    	strFrom = fromTime.substring(14, 16);
	    	strTo = toTime.substring(14, 16);
	    	
	    	int minutesFrom = Integer.parseInt(strFrom); // because of seconds addition
	    	int minutesTo = Integer.parseInt(strTo);
	    	
	    	if (minutesFrom != minutesTo)
	    		seconds += (60 - minutesFrom + minutesTo - 1) * 60;
	    	
	    	strFrom = fromTime.substring(11, 132);
	    	strTo = toTime.substring(11, 13);
	    	
	    	int hoursFrom = Integer.parseInt(strFrom); // because of minutes addition
	    	int hoursTo = Integer.parseInt(strTo);
	    	
	    	if (hoursFrom > hoursTo)
	    	{
	    		seconds += (24 - hoursFrom + hoursTo - 1) * 60 * 60;
	    	}
	    	else if (hoursFrom < hoursTo)
	    	{
	    		seconds += (hoursTo - hoursFrom - 1) * 60 * 60;
	    	}
	    	
	    	totalSecondsCountdown = seconds;*/
    		
    		totalSecondsCountdown = DateOperations.getDateDiff(fromTime, toTime) / 1000;	    	
    	}
    	catch (Exception e)
    	{
    		MonLogger.myLogger.log(Level.WARNING, e.getMessage());
    		
    		totalSecondsCountdown = 0;
    	}
	}
    
    /**
	 * Time left.
	 * 
	 * @return the string
	 */
    public String timeLeft()
    {
    	long secs = totalSecondsCountdown - secondsElapsed;
    	long days = secs / 86400;
    	
    	if (days > 0)
    	{
    		secs = secs % 86400;
    	}
    	
    	int hours = (int)(secs / 3600);
    	
    	if (hours > 0)
    	{
    		secs = secs % 3600;
    	}
    	
    	int  minutes = (int)(secs / 60);
    	
    	if (minutes > 0)
    	{
    		secs = secs % 60;
    	}
    	
    	return "Time Left : " + days + " Days - " + hours + "h:" + minutes + "m:" + secs + "s";
    	
    	
    }
	
    
    /**
	 * Checks if is ready to launch.
	 * 
	 * @return true, if is ready to launch
	 */
    public boolean isReadyToLaunch()
    {
    	if (isMonitoringDone)
    	{
    		return false;
    	}
    	
    	if (isMonitoringStarted)
    		return true;
    	
    	Calendar calendar = Calendar.getInstance();
    	
    	try
    	{
	    	monitoringStart();
	    	
	    	if (monitoringStartTime != null)
	    	{
	    		if (monitoringStartTime.compareTo(calendar.getTime()) <= 0)
	    		{
	    			isMonitoringStarted = true;
	    			
	    			isMonitoringDone = false;
	    			
	    			return true;
	    		}
	    		
	    		return false;
	    	}
	    	
    	}
    	catch (Exception e)
    	{
    		MonLogger.myLogger.log(Level.WARNING, e.getMessage());
    	}
    	
    	return false;
    	
    }
    
	/**
	 * Monitoring start.
	 */
	public void monitoringStart()
    {
    	if (monitoringStartTime == null)
    	{
    		Map<String, Object> map = getCurrentConfiguration();
    		
    		if (map != null)
    		{
    			String fromTime = (String) map.get(CViewConstants.START_FROM_TIME);
        		
        		if (fromTime != null)
        		{
        			//int hours = Integer.parseInt(fromTime.substring(0, 2));
        			//int minutes = Integer.parseInt(fromTime.substring(3, 5));
        			//int seconds = Integer.parseInt(fromTime.substring(6));
        			
        			monitoringStartTime = Time.valueOf(fromTime.replace('-', ':'));
        		}
        		else // start immediately
        		{
        			monitoringStartTime = Time.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":" 
        									+ Calendar.getInstance().get(Calendar.MINUTE) + ":00");
        		}
    		}
    		
    	}
    }
	
	
	
	/**
	 * Adds the new data files.
	 * 
	 * @param key
	 *            the key
	 * @param values
	 *            the values
	 */
	public void addNewDataFiles(String key, Vector<String> values)
	{
		if (dataFiles != null)
		{
			if (dataFiles.containsKey(key))
			{
				if (!values.isEmpty())
				{
					Vector<String> vec = dataFiles.get(key);
					
					if (vec == null)
					{
						vec = new Vector<String>();
						
						vec.addAll(values);
					}
					else
					{
						for (String str : values)
						{
							if (!vec.contains(str))
							{
								vec.add(str);
							}
						}
						
					}
					
				}
			}
			else if (key != null && !values.isEmpty())
			{
				dataFiles.put(key, values);
			}
		}
	}
	
	
	
	/**
	 * Gets the chosen data files.
	 * 
	 * @param key
	 *            the key
	 * @return the chosen data files
	 */
	public Vector<String> getChosenDataFiles(String key)
	{
		if (dataFiles.containsKey(key))
		{
			return dataFiles.get(key);
		}
		
		return null;
	}
	
	/**
	 * Gets the all data files keys.
	 * 
	 * @return the all data files keys
	 */
	public Set<String> getAllDataFilesKeys()
	{
		if (dataFiles == null)
			return null;
		
		return dataFiles.keySet();
	}
	
	
	/**
	 * Gets the current data files set.
	 * 
	 * @return the current data files set
	 */
	public Vector<String> getCurrentDataFilesSet()
	{
		if (currentDataFilesID != null)
		{
			return getChosenDataFiles(currentDataFilesID);
		}
		
		return null;
	}
	
	
	/**
	 * Load data files to local cache.
	 */
	public void loadDataFilesToLocalCache()
	{
		String dirName = System.getProperty("user.dir") + "//" + CViewConstants.MONITORING_TESTS;
		
		File directory = new File(dirName);
		
	    File[] fList = directory.listFiles();
	    
	    for (File file : fList)
	    {
	        if (file.isDirectory())
	        {
	        	String directoryNameAsKey = file.getAbsolutePath();
	        	
	        	dataFiles.put(directoryNameAsKey, getAllFilesInDirectory(directoryNameAsKey));
	        	
	        }
	    }

		
		
	}
	
	/**
	 * Gets the all files in directory.
	 * 
	 * @param directoryName
	 *            the directory name
	 * @return the all files in directory
	 */
	public Vector<String> getAllFilesInDirectory(String directoryName)
	{
    	Vector<String> vecOfFilesInDirectory = new Vector<String>();
		
    	File directory = new File(directoryName);

	    // get all the files from a directory
	    File[] fList = directory.listFiles();
	    for (File file : fList) 
	    {
	        if (file.isFile()) 
	        {
	        	vecOfFilesInDirectory.add(file.getAbsolutePath());
	        }
	    }
	    
	    return vecOfFilesInDirectory;
	}
	
	/**
	 * Gets the all files in map with current file type ending in directory.
	 * 
	 * @param fileType
	 *            the file type
	 * @param directoryName
	 *            the directory name
	 * @return the all files in map with current file type ending in directory
	 */
	public Vector<String> getAllFilesInMapWithCurrentFileTypeEndingInDirectory(CViewConstants.FileType fileType , String directoryName)
	{
		
		Vector<String> vec = dataFiles.get(directoryName);
		
		if (vec == null || vec.isEmpty())
		{
			return null;
		}
		
		Vector<String> vecOfDataFilesByEnding = new Vector<String>();
		
		if (CViewConstants.FileType.FileTypeExcel == fileType)
		{
			for (String str : vec)
			{
				if (str.endsWith(".csv") || str.endsWith(".xls"))
				{
					vecOfDataFilesByEnding.add(str);
				}
			}
		}
		else if (CViewConstants.FileType.FileTypeImg == fileType)
		{
			for (String str : vec)
			{
				if (str.endsWith(".png") || str.endsWith(".jpg") || str.endsWith(".jpeg"))
				{
					vecOfDataFilesByEnding.add(str);
				}
			}
		}
		
		return vecOfDataFilesByEnding;
	}
	
	public void nextORPreviousDirectoryInDataFilesMap(DirectoryDirection direction)
	{
		Set<String> setOfKeys = dataFiles.keySet();
		boolean isNextKeyReady = false;
		
		if (direction == DirectoryDirection.DirectoryDirectionNext)
		{
			for (String str : setOfKeys)
			{
				if (isNextKeyReady)
				{
					currentDataFilesID = str;
					
					return;
				}
				
				if (currentDataFilesID.equalsIgnoreCase(str))
				{
					isNextKeyReady = true;
				}
			}
			
			if (isNextKeyReady)
			{
				currentDataFilesID = (String) setOfKeys.toArray()[0];
			}
		}
		else if (direction == DirectoryDirection.DirectoryDirectionPrevious)
		{
			String key = null;
			
			for (String str : setOfKeys)
			{
				if (currentDataFilesID.equalsIgnoreCase(str))
				{
					isNextKeyReady = true;
					
					break;
				}
				else
				{
					key = str;
				}
			}
			
			if (isNextKeyReady)
			{
				if (key == null)
				{
					currentDataFilesID = (String) setOfKeys.toArray()[0];
				}
				else
				{
					currentConfigurationID = key;
				}
			}
		}
		
	}
	
	
	
	/**
	 * Sets the new execute unix op.
	 * 
	 * @param currentSettings
	 *            the current settings
	 */
	public void setNewExecuteUnixOp(Map<String, Object> currentSettings)
	{
		if (executeUnixOperations != null)
		{
			this.executeUnixOperations = null; // new Instance To be Run
		}
		
		this.executeUnixOperations = new ExecuteUnixOperations(currentSettings);
	}
		
}
