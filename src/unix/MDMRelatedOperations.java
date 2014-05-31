package unix;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class MDMRelatedOperations extends CommandExecuter
{
	private String instance;
	private String[] MDMInstancesFinal;
	
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


	public String findMDSVersion() throws JSchException
	{
		String MDSVesrion = this.execute("cat /usr/sap/" + instance + "/MDS*/exe/MDS_VERSION");
		return MDSVesrion;
		
	}
	
	public String findMDISVersion() throws JSchException
	{
		String MDISVesrion = this.execute("cat /usr/sap/" + instance + "/MDIS*/exe/MDIS_VERSION");
		return MDISVesrion;
		
	}
	public String findMDSSVersion() throws JSchException
	{
		String MDSSVesrion = this.execute("cat /usr/sap/" + instance + "/MDSS*/exe/MDSS_VERSION");
		return MDSSVesrion;
		
	}
	
	public String[] findMDMInstances() throws JSchException 
	{
			String MDMInstances = new String(this.execute("ls /usr/sap | grep '[A-Z]'"));
			String MDMInstancesNumStr = new String(this.execute("ls /usr/sap | grep -c '[A-Z]'"));
			int MDMInstancesNum = Integer.parseInt( MDMInstancesNumStr.substring(0, 1));
			MDMInstancesFinal = MDMInstances.split("\\n");
			//System.out.println(java.util.Arrays.toString(MDMInstancesFinal));
			return MDMInstancesFinal;
	}

	public int getMDSRunningProcesses() throws JSchException 
	{
			String mdsNum = this.execute("ps -ef|grep mds-r");
			String[] lines = mdsNum.split("\r\n|\r|\n");
			return  lines.length;
			
			
	}
	
	public int getMDISRunningProcesses() throws JSchException 
	{
		String mdisNum = this.execute("ps -ef|grep mdis-r");
		String[] lines = mdisNum.split("\r\n|\r|\n");
		return  lines.length;
			
			
	}
	
	public int getMDSSRunningProcesses() throws JSchException 
	{
		String mdssNum = this.execute("ps -ef|grep mdss-r");
		String[] lines = mdssNum.split("\r\n|\r|\n");
		return  lines.length;
			
			
	}

}
