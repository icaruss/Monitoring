/*
 * 
 */
package unix;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

// TODO: Auto-generated Javadoc
/**
 * The Class RunkillSH.
 */
public class RunkillSH extends CommandExecuter
{

	/** The file names. */
	private List<String> fileNames = new ArrayList<String>();
	
	/**
	 * Instantiates a new runkill sh.
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
	public RunkillSH(String hostName, String userName, String password,
			JSch jschSSHChannel, Session sesConnection, int intTimeOut) {
		super(hostName, userName, password, jschSSHChannel, sesConnection, intTimeOut);
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
	 * Instantiates a new runkill sh.
	 */
	public RunkillSH() {
		super(hostName, userName, password, jschSSHChannel, sesConnection, intTimeOut);
/*		try 
		{
			this.connectToUnix();
		} 
		catch (JSchException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		// TODO Auto-generated constructor stub
	}
	

		 /////
		 /**
		 * Execute sh.
		 * 
		 * @param paramsForSH
		 *            the params for sh
		 * @param SHFileName
		 *            the SH file name
		 * @throws InterruptedException
		 *             the interrupted exception
		 * @throws JSchException
		 *             the j sch exception
		 */
 		public void ExecuteSh(String[] paramsForSH, String SHFileName) throws InterruptedException, JSchException
		 {
			 //"HP1", "mdss", "mds", "mdis" - the params order
			 //The parameters for SH has to be entered in the correct order
			 String allParams = new String();
			 this.execute("chmod 755 " + SHFileName);

			if (paramsForSH == null)
			 {
				 this.execute("nohup ./" + SHFileName + " > /dev/null 2>&1 &");				 
			 }

			 else 
				
				 for (int i = 0 ; i < paramsForSH.length ; i++)
				 {
					allParams += paramsForSH[i] + " ";					
				}
				 
				 this.execute("nohup ./" + SHFileName + " " + allParams + " > /dev/null 2>&1 &");

		 }
		 
		 
		 
		 ///////
		 // get file names if sh script has generated files
		 /**
		 * Creates the file names.
		 * 
		 * @param lsCommandToFindFilesNames
		 *            the ls command to find files names
		 * @throws JSchException
		 *             the j sch exception
		 */
 		public void createFileNames(String lsCommandToFindFilesNames) throws JSchException
		 {
			 
			 //the command should be ls | grep _mon_  --> lsCommandToFindFilesNames = ls | grep _mon_
			 /* Example for the file Names are:
			  * mdis_mon_19-06-2013_13-35-10.txt
			  * mds_mon_19-06-2013_13-35-10.txt
			  * mdss_mon_19-06-2013_13-35-10.txt
			  * vmstat_mon_19-06-2013_13-35-10.txt
			  */
			 String fileNamesString = this.execute(lsCommandToFindFilesNames);

			 //System.out.println("fileNamesString: " + fileNamesString);
			 String delim = "\n";						 //insert here all delimitators
			 StringTokenizer st = new StringTokenizer(fileNamesString,delim);
			 while(st.hasMoreTokens()) 
			 {
				 fileNames.add(st.nextToken());		     
			 }
		 }

		 
		 /**
		 * Gets the file names.
		 * 
		 * @return the file names
		 */
 		public List<String> getFileNames() {
			return fileNames;
		}

		///  TODO: CHECK AND OPTIMIZE
		 /**
		 * Kill processes.
		 * 
		 * @param SHFileName
		 *            the SH file name
		 * @throws JSchException
		 *             the j sch exception
		 */
		public void killProcesses(String SHFileName) throws JSchException
		 {
			 if (this.execute("ls | grep " + SHFileName) == SHFileName + "\n");
			 {
				 this.execute("./" + SHFileName + " kill");
				 this.execute("pgrep -l vmstat");
				 this.execute("pkill vmstat");
				 
			 }

/*			 for (int i = 0; i < fileNames.size() ; i++)
			 {
				 this.copyFromUnix(fileNames.get(i));
			 }*/
			 
		 }
		 
		 
		 /**
		 * Copy files to win.
		 * 
		 * @throws JSchException
		 *             the j sch exception
		 */
 		public void copyFilesToWin() throws JSchException
		 {
			 winUnixOperations winUnixOperations =  new winUnixOperations();
			 for (int i = 0; i < fileNames.size() ; i++)
			 {
				 winUnixOperations.copyFromUnix(fileNames.get(i));
			 }
			 this.execute("rm *_mon_*.txt");
		 }
		 
		 
		 /**
		 * Removes the files from unix.
		 * 
		 * @throws JSchException
		 *             the j sch exception
		 */
 		public void removeFilesFromUnix() throws JSchException
		 {

			 this.execute("rm *_mon_*.txt");
		 }
		 
		 
		 ////

			
			/**
		 * Locate bash.
		 * 
		 * @param OS
		 *            the os
		 * @return the string
		 * @throws JSchException
		 *             the j sch exception
		 */
 		public String locateBash(String OS) throws JSchException
			{
				String bash = null;
				if(OS.startsWith("Linux"))
				{
					bash = new String("/bin/bash");
				}
				if(OS.startsWith("SunOS"))
				{
					bash = new String("/usr/bin/bash");
				}
				else			
				{
					this.execute("rm pathfile.sh");
					//this.executeCommand("echo umask 000 > pathfile.sh\echo export PATH=/usr/sbin:/usr/local/bin:/usr/bin:/usr/ccs/bin:/usr/contrib/bin:/usr/contrib/Q4/bin:/opt/perl/bin:/opt/gvsd/bin:/opt/ipf/bin:/opt/nettladm/bin:/opt/fcms/bin:/opt/wbem/bin:/opt/wbem/sbin:/opt/sas/bin:/opt/graphics/common/bin:/opt/atok/bin:/usr/bin/X11:/usr/contrib/bin/X11:/opt/sec_mgmt/bastille/bin:/opt/caliper/bin:/opt/drd/bin:/opt/dsau/bin:/opt/dsau/sbin:/opt/resmon/bin:/opt/firefox:/opt/gnome/bin:/opt/propplus/bin:/usr/contrib/kwdb/bin:/opt/perl_32/bin:/opt/perl_64/bin:/opt/prm/bin:/opt/sfm/bin:/opt/swm/bin:/opt/sec_mgmt/spc/bin:/opt/ssh/bin:/opt/swa/bin:/opt/hpsmh/bin:/opt/thunderbird:/opt/sentinel/bin:/opt/langtools/bin:/opt/gwlm/bin >> pathfile.sh" );
					this.execute("export TEMPPATH=$PATH");
					this.execute("echo export PATH=/usr/sbin:/usr/local/bin:/usr/bin:/usr/ccs/bin:/usr/contrib/bin:/usr/contrib/Q4/bin:/opt/perl/bin:/opt/gvsd/bin:/opt/ipf/bin:/opt/nettladm/bin:/opt/fcms/bin:/opt/wbem/bin:/opt/wbem/sbin:/opt/sas/bin:/opt/graphics/common/bin:/opt/atok/bin:/usr/bin/X11:/usr/contrib/bin/X11:/opt/sec_mgmt/bastille/bin:/opt/caliper/bin:/opt/drd/bin:/opt/dsau/bin:/opt/dsau/sbin:/opt/resmon/bin:/opt/firefox:/opt/gnome/bin:/opt/propplus/bin:/usr/contrib/kwdb/bin:/opt/perl_32/bin:/opt/perl_64/bin:/opt/prm/bin:/opt/sfm/bin:/opt/swm/bin:/opt/sec_mgmt/spc/bin:/opt/ssh/bin:/opt/swa/bin:/opt/hpsmh/bin:/opt/thunderbird:/opt/sentinel/bin:/opt/langtools/bin:/opt/gwlm/bin >> pathfile.sh");
					this.execute("echo which bash >> pathfile.sh");
					this.execute("chmod 744 pathfile.sh");
					bash = this.execute("./pathfile.sh");
				}

				return bash;
			}
			
	 }


