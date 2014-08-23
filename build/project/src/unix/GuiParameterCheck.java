/*
 * 
 */
package unix;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;

import log.MonLogger;
import FilesManagment.DateOperations;

import com.jcraft.jsch.JSchException;

// TODO: Auto-generated Javadoc
/**
 * The Class GuiParameterCheck.
 */
public class GuiParameterCheck {

	/** The instance. */
	String instance;

	/** The host name. */
	String hostName;

	/** The user name. */
	String userName;

	/** The password. */
	String password;
	// UnixConnection unixConnection;
	/** The command executer. */
	CommandExecuter commandExecuter;

	/**
	 * Instantiates a new gui parameter check.
	 * 
	 * @param instance
	 *            the instance
	 * @param hostName
	 *            the host name
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 */
	public GuiParameterCheck(String instance, String hostName, String userName,
			String password) {
		super();
		this.instance = instance;
		this.hostName = hostName;
		this.userName = userName;
		this.password = password;
		// this.unixConnection = new UnixConnection(hostName, userName,
		// password, null, null, 0);
		this.commandExecuter = new CommandExecuter(hostName, userName,
				password, null, null, 0);

	}

	/**
	 * Instace exist.
	 * 
	 * @return true, if successful
	 * @throws JSchException
	 *             the j sch exception
	 */
	public boolean instaceExist() throws JSchException {
		Boolean TF = true;
		String msg = null;
		instance = instance.toUpperCase();
		commandExecuter = new CommandExecuter();

		msg = commandExecuter.execute("cd /usr/sap/" + instance);
		if (msg.trim().length() != 0) {
			TF = false;
			// TODO: Redirect the output string to the GUI
		}

		return TF;

	}

	/**
	 * Mds started.
	 * 
	 * @return true, if successful
	 * @throws JSchException
	 *             the j sch exception
	 */
	public boolean mdsStarted() throws JSchException {
		Boolean TF = true;
		String msg = null;
		instance = instance.toUpperCase();

		CommandExecuter command = new CommandExecuter();
		msg = command.execute("ps -ef|grep mds-r | grep " + instance);
		if (!msg.contains("exe/mds-r")) {
			TF = false;
			// TODO: Redirect the output string to the GUI
			System.out.println("MDS is not started, please start MDS!");
		}

		return TF;

	}

	/**
	 * Mdis started.
	 * 
	 * @return true, if successful
	 * @throws JSchException
	 *             the j sch exception
	 */
	public boolean mdisStarted() throws JSchException {
		Boolean TF = true;
		String msg = null;
		instance = instance.toUpperCase();

		CommandExecuter command = new CommandExecuter();
		msg = command.execute("ps -ef|grep mdis-r | grep " + instance);
		if (!msg.contains("exe/mdis-r")) {
			TF = false;
			// TODO: Redirect the output string to the GUI
			System.out.println("MDIS is not started, please start MDIS!");
		}

		return TF;

	}

	/**
	 * Mdss started.
	 * 
	 * @return true, if successful
	 * @throws JSchException
	 *             the j sch exception
	 */
	public boolean mdssStarted() throws JSchException {
		Boolean TF = true;
		String msg = null;
		instance = instance.toUpperCase();

		CommandExecuter command = new CommandExecuter();
		msg = command.execute("ps -ef|grep mdss-r  | grep " + instance);
		if (!msg.contains("exe/mdss-r")) {
			TF = false;
			// TODO: Redirect the output string to the GUI
			System.out.println("MDSS is not started, please start MDSS!");
		}
		return TF;

	}

	// If connection successful the return string will be null
	/**
	 * Connection test success.
	 * 
	 * @return true, if successful
	 */
	public boolean connectionTestSuccess() {
		String msg = null;
		boolean TF = true;
		try {
			msg = UnixConnection.connectToUnix();

			if (msg != null) {
				TF = false;
				// TODO: Redirect the output string to the GUI
				System.out.println(msg);
			}
		} catch (JSchException e) {
			MonLogger.myLogger.log(Level.WARNING, e.getMessage());
			MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
		}
		return TF;

	}

	/**
	 * Main gui check.
	 * 
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @return the boolean
	 */
	@SuppressWarnings("finally")
	public Boolean mainGuiCheck(String startDate, String endDate) {

		Boolean TF = true;
		try {
			if (connectionTestSuccess() == true) {
				MonLogger.myLogger.log(Level.INFO,
						"Connection Test was successful");

			} else {
				MonLogger.myLogger.log(Level.INFO,
						"Connection Test was NOT successful");
				TF = false;
				return TF;
			}

			if (instaceExist() == true) {
				MonLogger.myLogger.log(Level.INFO, "Instance exist");
			} else {
				MonLogger.myLogger.log(Level.INFO, "Instance does NOT exist!");
				TF = false;
				return TF;
			}
			if (mdsStarted() == true) {
				MonLogger.myLogger.log(Level.INFO, "MDS is started!");

			} else {
				MonLogger.myLogger.log(Level.INFO, "MDS is NOT started!");
				MonLogger.myLogger
						.log(Level.INFO,
								"Start before proceeding or uncheck the 'mds-r' radiobutton!");
				TF = false;
				return TF;
			}
			if (mdisStarted() == true) {
				MonLogger.myLogger.log(Level.INFO, "MDIS is started!");
			} else {
				MonLogger.myLogger.log(Level.INFO, "MDIS is NOT started!");
				MonLogger.myLogger
						.log(Level.INFO,
								"Start before proceeding or uncheck the 'mdis-r' radiobutton!");
				TF = false;
				return TF;
			}
			if (mdssStarted() == true)
				MonLogger.myLogger.log(Level.INFO, "MDSS is started!");
			else {
				MonLogger.myLogger.log(Level.INFO, "MDSS is NOT started!");
				MonLogger.myLogger
						.log(Level.INFO,
								"Start before proceeding or uncheck the 'mdss-r' radiobutton!");
				TF = false;
				return TF;
			}

			// Times check
			if (startDate.equals(null) && endDate.equals(null))
			{
				return TF;
			}
			DateOperations dateOperations = new DateOperations();
			Calendar cal = Calendar.getInstance();
			Date date = cal.getTime();
			SimpleDateFormat currentTime = new SimpleDateFormat(
					"dd-mm-yyyy HH:mm:ss");

			// Object currentTime;
			String comparison = dateOperations.compareTwoDates(
					currentTime.format(date), endDate);
			String comparison2 = dateOperations.compareTwoDates(startDate,
					endDate);

			// end Date-time is smaller than current date-time
			if (comparison.equals("date1Bigger")
					|| comparison.equals("date1Equals")) {
				// TODO: Print output to GUI
				MonLogger.myLogger
						.log(Level.WARNING,
								"End date is smaller than current date, please enter valid end date");
				TF = false;
				return TF;
			}

			// start date-time is bigger than end date-time
			if (comparison2.equals("date1Bigger")
					|| comparison2.equals("date1Equals")) {
				// TODO: Print output to GUI
				MonLogger.myLogger
						.log(Level.WARNING,
								"Start date is bigger than end date, please enter valid dates");
				TF = false;
				return TF;
			}

		} catch (JSchException e) {
			MonLogger.myLogger.log(Level.WARNING, e.getMessage());
			MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
		} finally {
			return TF;
		}
	}
}
