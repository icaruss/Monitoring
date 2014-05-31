package viewLogic;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.thoughtworks.xstream.XStream;

public class CSharedInstance 
{
	
	private String currentConfigurationID;
	
	private Map<String, Map<String, Object>> configurations;
	
	private static CSharedInstance sharedInstance = new CSharedInstance( );
		   
	/* A private Constructor prevents any other 
	 * class from instantiating.
	 */
	private CSharedInstance()
	{ 
		configurations = new HashMap<String, Map<String, Object>>();
		currentConfigurationID = null;
		
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
	
		
}
