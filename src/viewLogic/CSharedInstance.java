package viewLogic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;

import javax.management.monitor.MonitorNotification;
import javax.management.monitor.MonitorSettingException;
import javax.print.attribute.standard.DateTimeAtCompleted;

import log.MonLogger;

import com.thoughtworks.xstream.XStream;

public class CSharedInstance 
{
	
	public int totalSecondsCountdown;
	public int secondsElapsed;
	
	public CViewConstants.MonitorType currentMonitoring;
	
	private boolean isMonitoringStarted;
	private Time monitoringStartTime;
	
	private String currentConfigurationID;
	
	private Map<String, Map<String, Object>> configurations;
	
	public String currentDataFilesID;
	
	private Map<String, Vector<String>> dataFiles;
	
	
	private static CSharedInstance sharedInstance = new CSharedInstance( );
		   
	/* A private Constructor prevents any other 
	 * class from instantiating.
	 */
	private CSharedInstance()
	{ 
		configurations = new HashMap<String, Map<String, Object>>();
		currentConfigurationID = null;
		totalSecondsCountdown = -1;
		secondsElapsed = 0;
		isMonitoringStarted = false;
		monitoringStartTime = null;
		
		currentMonitoring = CViewConstants.MonitorType.MonitorTypeElse;
		
		currentDataFilesID = null;
		
		dataFiles = new HashMap<String, Vector<String>>();
		
		deSeriallize();
		
		setDefaultConfiguration();
	}


	/* Static 'instance' method */
	public static CSharedInstance getInstance( ) 
	{
		return sharedInstance;
	}
	

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
	
	
	
	public Map<String, Object> getChosenConfiguration(String key)
	{
		if (configurations.containsKey(key))
		{
			return configurations.get(key);
		}
		
		return null;
	}
	
	public Set<String> getAllConfigurationKeys()
	{
		return configurations.keySet();
	}
	
	
	public  Map<String, Object> getCurrentConfiguration()
	{
		if (currentConfigurationID != null)
		{
			return getChosenConfiguration(currentConfigurationID);
		}
		
		return null;
	}
	
	

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
	
	private String seriallizeData()
	{
		XStream xStream = new XStream();
		
		return xStream.toXML(configurations);
	}
	
	
	private static void stringToDom(String xmlSource) throws IOException 
	{
	    java.io.FileWriter fw = new java.io.FileWriter("Configurations.xml");
	    fw.write(xmlSource);
	    fw.close();
	}
	
	
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
	

	public void saveConfigurations(Map<String, Object> currentSettings)
	{
		String key = (String) currentSettings.get(CViewConstants.CONFIGURATION_ID);
		configurations.put(key, currentSettings);
		
		saveConfigurations();
		
	}
	

    
    public void updateWaitingTimeForResults(String fromTime, String toTime) 
	{
    	int seconds = 0;
    	
    	try
    	{
	    	String strFrom = fromTime.substring(6);
	    	String strTo = toTime.substring(6);
	    	
	    	int secondsFrom = Integer.parseInt(strFrom);
	    	int secondsTo = Integer.parseInt(strTo);
	    	
	    	if (secondsFrom != secondsTo)
	    		seconds += (60 - secondsFrom + secondsTo);
	    	
	    	strFrom = fromTime.substring(3, 5);
	    	strTo = toTime.substring(3, 5);
	    	
	    	int minutesFrom = Integer.parseInt(strFrom); // because of seconds addition
	    	int minutesTo = Integer.parseInt(strTo);
	    	
	    	if (minutesFrom != minutesTo)
	    		seconds += (60 - minutesFrom + minutesTo - 1) * 60;
	    	
	    	strFrom = fromTime.substring(0, 2);
	    	strTo = toTime.substring(0, 2);
	    	
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
	    	
	    	totalSecondsCountdown = seconds;
	    	
    	}
    	catch (Exception e)
    	{
    		MonLogger.myLogger.log(Level.WARNING, e.getMessage());
    	}
    	finally
    	{
    		// in case parsing was unsuccessful , setting to immediate
    		if (seconds == 0)
    		{
    			totalSecondsCountdown = 0;
    		}
    	}
	}
    
    public String timeLeft()
    {
    	int secondsLeft = totalSecondsCountdown - secondsElapsed;
    	int hours = secondsLeft / 3600;
    	secondsLeft = secondsLeft % 3600;
    	
    	int minutes = secondsLeft / 60;
    	secondsLeft = secondsLeft % 60;
    	
    	int seconds = secondsLeft;
    	
    	return String.format("Time Left : %dh-%dm-%ds", hours,minutes,seconds);
    }
	
    
    public boolean isReadyToLaunch()
    {
    	
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
	
	
	
	public void addNewDataFiles(String key, Vector<String> values)
	{
		if (dataFiles != null)
		{
			if (dataFiles.containsKey(key))
			{
				if (!values.isEmpty())
				{
					List<String> lst = dataFiles.get(key);
					
					if (lst == null)
					{
						lst = new Vector<String>();
						
						lst.addAll(values);
					}
					else
					{
						for (String str : values)
						{
							if (!lst.contains(str))
							{
								lst.add(str);
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
	
	
	
	public Vector<String> getChosenDataFiles(String key)
	{
		if (dataFiles.containsKey(key))
		{
			return dataFiles.get(key);
		}
		
		return null;
	}
	
	public Set<String> getAllDataFilesKeys()
	{
		return dataFiles.keySet();
	}
	
	
	public Vector<String> getCurrentDataFilesSet()
	{
		if (currentDataFilesID != null)
		{
			return getChosenDataFiles(currentDataFilesID);
		}
		
		return null;
	}
	
	
	public void loadDataFilesToLocalCache()
	{
		
		// To Do : Depends on Directories
		
	}
	
	public void saveDataFilesToExternalFile()
	{
		
		// To Do : Depends on Directories
		
	}
		
}
