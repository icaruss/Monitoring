/*
 * 
 */
package unix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

// TODO: Auto-generated Javadoc
/**
 * The Class CommandExecuter.
 */
public class CommandExecuter extends UnixConnection {

	/** The instance. */
	protected static CommandExecuter instance = null;

	/**
	 * Instantiates a new command executer.
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
	protected CommandExecuter(String hostName, String userName,
			String password, JSch jschSSHChannel, Session sesConnection,
			int intTimeOut) {
		super(hostName, userName, password, jschSSHChannel, sesConnection,
				intTimeOut);
		/*
		 * try { this.connectToUnix(); } catch (JSchException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
	}

	/**
	 * Instantiates a new command executer.
	 */
	protected CommandExecuter() {
		super(hostName, userName, password, jschSSHChannel, sesConnection,
				intTimeOut);

	}

	/**
	 * Gets the single instance of CommandExecuter.
	 * 
	 * @return single instance of CommandExecuter
	 */
	public static CommandExecuter getInstance() {
		if (instance == null) {
			synchronized (CommandExecuter.class) {
				if (instance == null) {
					instance = new CommandExecuter(hostName, userName,
							password, jschSSHChannel, sesConnection, intTimeOut);
				}
			}
		}
		return instance;
	}

	/**
	 * Execute shell.
	 * 
	 * @param command
	 *            the command
	 * @throws JSchException
	 *             the j sch exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected void executeShell(String command) throws JSchException,
			IOException {
		UnixConnection.connectToUnix();
		Channel channel = sesConnection.openChannel("shell");

		OutputStream inputstream_for_the_channel = (OutputStream) channel
				.getOutputStream();
		PrintStream commander = new PrintStream(inputstream_for_the_channel,
				true);

		channel.setOutputStream(System.out, true);

		channel.connect();

		commander.println(command);

		commander.close();

		/*
		 * do { Thread.sleep(1000); } while(!channel.isEOF());
		 */
		/*
		 * sesConnection.disconnect(); UnixConnection.disconnectFromUnix();
		 */

	}

	/**
	 * Execute.
	 * 
	 * @param command
	 *            the command
	 * @return the string
	 * @throws JSchException
	 *             the j sch exception
	 */
	protected String execute(String command) throws JSchException {

		InputStream in = null;
		InputStream err = null;
		BufferedReader inReader = null;
		BufferedReader errReader = null;

		StringBuilder sb = new StringBuilder();
		try {
			UnixConnection.connectToUnix();
			Channel channel = sesConnection.openChannel("exec");
			((ChannelExec) channel).setCommand(command);
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(null);

			in = channel.getInputStream();
			err = ((ChannelExec) channel).getErrStream();

			channel.connect();

			int exitCode = 0;
			while (true) {
				if (channel.isClosed()) {
					exitCode = channel.getExitStatus();
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ie) {

				}
			}

			inReader = new BufferedReader(new InputStreamReader(in));
			errReader = new BufferedReader(new InputStreamReader(err));

			String s;
			if (exitCode != 0) {
				while ((s = errReader.readLine()) != null) {
					sb.append(s).append("\n");
					if (sb.toString() != "\n") {
						System.out.println("error: " + sb.toString());
					}
				}
				// status.setData(sb.toString());

			} else {
				while ((s = inReader.readLine()) != null) {

					sb.append(s).append("\n");
					if (s != "\n") {
						System.out.println(s);
					}

				}

				channel.disconnect();

			}
			// throw new
			// UnixCommandExecuterException("Exception on execute method");

		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSchException e) {
			e.printStackTrace();
		}
		CommandExecuter.disconnectFromUnix();

		int tmp = sb.toString().length();
		if (tmp > 0) {
			tmp = sb.toString().length() - 1;
		}

		return sb.toString().substring(0, tmp);

	}
}
