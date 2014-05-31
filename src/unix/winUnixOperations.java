package unix;

import java.io.FileNotFoundException;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

public class winUnixOperations extends CommandExecuter
{
	/////
	public void copyToUnix(String mon_file) throws FileNotFoundException
	{

		try 
		{
			UnixConnection.connectToUnix();
		    Channel channel = sesConnection.openChannel("sftp");
		    channel.connect();
		    ChannelSftp sftpChannel = (ChannelSftp) channel;
		  //  ChannelExec channelExe = (ChannelExec)sesConnection.openChannel("exec");
	
			sftpChannel.put(mon_file,mon_file);
			

			//works!!
			this.execute("tr -d '\015' <" + mon_file + ">temp.sh");
			this.execute("rm " + mon_file);
			this.execute("mv temp.sh " + mon_file);
			this.execute("chmod 0775 " + mon_file);


			sftpChannel.exit();
			sftpChannel.disconnect();
			channel.disconnect();
			UnixConnection.disconnectFromUnix();
		} 
		catch (JSchException e) 
		{
			e.printStackTrace();
		} 
		catch (SftpException e)
		{
			e.printStackTrace();
		}
	}
	
	public void copyFromUnix(String mon_file)
	{	
		
	    try
	    {
	    	UnixConnection.connectToUnix();
	        Channel channel = sesConnection.openChannel("sftp");
	        channel.connect();
	        ChannelSftp sftpChannel = (ChannelSftp) channel;
	        ChannelExec channelExe = (ChannelExec)sesConnection.openChannel("exec");
	        

	        sftpChannel.get(mon_file, mon_file);
	        sftpChannel.exit();
	        //sesConnection.disconnect();
	        channelExe.disconnect();
	        channel.disconnect();
	        UnixConnection.disconnectFromUnix();
	        
	    } 
	    catch (JSchException e) 
	    {
	        e.printStackTrace(); 
	    } 
	    catch (SftpException e) {
	        e.printStackTrace();
	    }
		
	}



}
