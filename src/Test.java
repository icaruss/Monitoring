

import java.io.IOException;
import java.text.ParseException;
import unix.ExecuteUnixOperations;
import com.jcraft.jsch.JSchException;

public class Test 
{
	public static void main(String[] args) throws IOException, JSchException, ParseException
	{
		//UnixContext unixContext = new UnixContext();
		//UnixConnection unixConnection = new UnixConnection("iltlvh325","sapinst", "a2i2000!", null, null, 0);
		//CommandExecuter commandExecuter = new CommandExecuter("iltlvh325","sapinst", "a2i2000!", null, null, 0);
		//unixConnection.connectToUnix();
		//commandExecuter.execute("uname -d");
		//unixConnection.disconnectFromUnix();
		//Calendar cal = Calendar.getInstance();
    	//cal.getTime();
    	//SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    	//System.out.println( sdf.format(cal.getTime()) );
		//UnixConnection unixConnection = new UnixConnection("iltlvh325", "sapinst","a2i2000!", null, null, 0);
		
		
		ExecuteUnixOperations executeUnixOperations =  new ExecuteUnixOperations("iltlvh325", "h26adm","a2i2000!", null, null, 0);
		executeUnixOperations.finish();		

	}
}

