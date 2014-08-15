/*
 * 
 */
package unix;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

// TODO: Auto-generated Javadoc
/**
 * The Class MDMRelatedOperations.
 */
public class MDMRelatedOperations extends CommandExecuter
{
	
	/** The instance. */
	private String instance;
	
	/** The MDM instances final. */
	private String[] MDMInstancesFinal;
	
	/**
	 * Instantiates a new MDM related operations.
	 * 
	 * @param hostName
	 *            the host name
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param jschSSHChannel
	 *            the jsch ssh channel
	 * @param sesConnection
	 *            the ses connection
	 * @param intTimeOut
	 *            the int time out
	 */
	public MDMRelatedOperations(String hostName, String userName,
			String password, JSch jschSSHChannel, Session sesConnection,
			int intTimeOut) 
	{
		super(hostName, userName, password, jschSSHChannel, sesConnection, intTimeOut);
		// TODO Auto-generated constructor stub
/*		try 
		{
			this.connectToUnix();
		} 
		catch (JSchException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	/**
	 * Instantiates a new MDM related operations.
	 */
	public MDMRelatedOperations() {
		super(hostName, userName, password, jschSSHChannel, sesConnection, intTimeOut);
		// TODO Auto-generated constructor stub
/*		try 
		{
			this.connectToUnix();
		} 
		catch (JSchException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}


	/**
	 * Find mds version.
	 * 
	 * @return the string
	 * @throws JSchException
	 *             the j sch exception
	 */
	public String findMDSVersion() throws JSchException
	{
		String MDSVesrion = this.execute("cat /usr/sap/" + instance + "/MDS*/exe/MDS_VERSION");
		return MDSVesrion;
		
	}
	
	/**
	 * Find mdis version.
	 * 
	 * @return the string
	 * @throws JSchException
	 *             the j sch exception
	 */
	public String findMDISVersion() throws JSchException
	{
		String MDISVesrion = this.execute("cat /usr/sap/" + instance + "/MDIS*/exe/MDIS_VERSION");
		return MDISVesrion;
		
	}
	
	/**
	 * Find mdss version.
	 * 
	 * @return the string
	 * @throws JSchException
	 *             the j sch exception
	 */
	public String findMDSSVersion() throws JSchException
	{
		String MDSSVesrion = this.execute("cat /usr/sap/" + instance + "/MDSS*/exe/MDSS_VERSION");
		return MDSSVesrion;
		
	}
	
	/**
	 * Find mdm instances.
	 * 
	 * @return the string[]
	 * @throws JSchException
	 *             the j sch exception
	 */
	public String[] findMDMInstances() throws JSchException 
	{
			String MDMInstances = new String(this.execute("ls /usr/sap | grep '[A-Z]'"));
		/*	String MDMInstancesNumStr = new String(this.execute("ls /usr/sap | grep -c '[A-Z]'"));
			int MDMInstancesNum = Integer.parseInt( MDMInstancesNumStr.substring(0, 1));*/
			MDMInstancesFinal = MDMInstances.split("\\n");
			//System.out.println(java.util.Arrays.toString(MDMInstancesFinal));
			return MDMInstancesFinal;
	}

	/**
	 * Gets the MDS running processes.
	 * 
	 * @return the MDS running processes
	 * @throws JSchException
	 *             the j sch exception
	 */
	public int getMDSRunningProcesses() throws JSchException 
	{
			String mdsNum = this.execute("ps -ef|grep mds-r");
			String[] lines = mdsNum.split("\r\n|\r|\n");
			return  lines.length;
			
			
	}
	
	/**
	 * Gets the MDIS running processes.
	 * 
	 * @return the MDIS running processes
	 * @throws JSchException
	 *             the j sch exception
	 */
	public int getMDISRunningProcesses() throws JSchException 
	{
		String mdisNum = this.execute("ps -ef|grep mdis-r");
		String[] lines = mdisNum.split("\r\n|\r|\n");
		return  lines.length;
			
			
	}
	
	/**
	 * Gets the MDSS running processes.
	 * 
	 * @return the MDSS running processes
	 * @throws JSchException
	 *             the j sch exception
	 */
	public int getMDSSRunningProcesses() throws JSchException 
	{
		String mdssNum = this.execute("ps -ef|grep mdss-r");
		String[] lines = mdssNum.split("\r\n|\r|\n");
		return  lines.length;
			
			
	}

}
