/*
 * 
 */
package unix;

import Clix.ParseClix;
import Clix.clix;

import java.util.logging.Level;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import log.MonLogger;

import viewLogic.CSharedInstance;
import Charts.ChartGeneration;
import FilesManagment.Converter;
import FilesManagment.DateOperations;
import FilesManagment.ExcelManagement;
import FilesManagment.FileManagmentOperations;
import FilesManagment.Folder;
import FilesManagment.MDMProccessFilesOperations;
import FilesManagment.VmstatFileOperations;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

// TODO: Auto-generated Javadoc
/**
 * The Class ExecuteUnixOperations.
 */
public class ExecuteUnixOperations extends CommandExecuter {

	/**
	 * Gets the os.
	 * 
	 * @return the os
	 */
	public String getOS() {
		String tmpOS = null;
		try {
			tmpOS = new String(this.execute("uname -s"));
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tmpOS;
	}

	/**
	 * Instantiates a new execute unix operations.
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
	public ExecuteUnixOperations(String hostName, String userName,
			String password, JSch jschSSHChannel, Session sesConnection,
			int intTimeOut) {
		super(hostName, userName, password, jschSSHChannel, sesConnection,
				intTimeOut);

	}

	/**
	 * Instantiates a new execute unix operations.
	 */
	public ExecuteUnixOperations() {
		super(hostName, userName, password, jschSSHChannel, sesConnection,
				intTimeOut);

	}

	// PARAMETERS TO GET FROM GUI - START TODO
	// ///////////////////////////////////////
	/** The port. */
	String port;

	/** The params for sh. */
	String[] paramsForSH;

	/** The clix on. */
	Boolean clixOn;

	/** The sid. */
	String SID;

	/** The interval. */
	int interval;

	/** The start date. */
	String startDate;

	/** The end date. */
	String endDate;
	
	int memDiff;

	// ///////////////////////////////////////
	// PARAMETERS TO GET FROM GUI - END

	/**
	 * Instantiates a new execute unix operations.
	 * 
	 * @param currentConfiguration
	 *            the current configuration
	 */
	public ExecuteUnixOperations(Map<String, Object> currentConfiguration) {
		super((String) (currentConfiguration.get("hostName")),
				(String) (currentConfiguration.get("userName")),
				(String) (currentConfiguration.get("password")),
				(JSch) (currentConfiguration.get("jschSSHChannel")),
				(Session) (currentConfiguration.get("sesConnection")), 0);

		String m_port = (String) (currentConfiguration.get("port"));
		port = (null != m_port) ? m_port : "59950";
		String m_interval = (String) (currentConfiguration.get("interval"));
		interval = (null != m_interval) ? Integer.parseInt(m_interval) : 5;
		SID = (String) (currentConfiguration.get("instance"));
		clixOn = (Boolean) (currentConfiguration.get("clix"));
		paramsForSH = createParamsForSH(currentConfiguration);
		startDate = (String) currentConfiguration.get("startFromTime");
		endDate = (String) currentConfiguration.get("startToTime");
		memDiff = Integer.parseInt((String)currentConfiguration.get("memoryPop"));
	}

	/** The runkill sh. */
	private RunkillSH runkillSH = new RunkillSH();

	/** The file managment operations. */
	private FileManagmentOperations fileManagmentOperations = new FileManagmentOperations();

	/** The date operations. */
	private DateOperations dateOperations = new DateOperations();

	/** The mon_file. */
	String mon_file = "AV_monitoring.sh";

	/** The excel management. */
	private ExcelManagement excelManagement = null;

	/** The clix command. */
	clix clixCommand = null;

	/** The win unix operations. */
	private winUnixOperations winUnixOperations = new winUnixOperations();

	// ------------------------------ Place files in Folder
	// ------------------------

	/*
	 * 1. The current monitoring files will be placed under a folder named by a
	 * local time string 2. The last 10 files will be placed under the folder
	 * named "Monitoring Tests", and will contain by default the last 10 tests.
	 */

	/** The date format. */
	DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd HH_mm_ss");
	// get current date time with Date()
	/** The date. */
	Date date = new Date();

	/** The Test folder name. */
	String TestFolderName = dateFormat.format(date) + "_" + getOS();

	/** The Test folder. */
	Folder TestFolder;

	/** The main folder. */
	Folder mainFolder = new Folder(System.getProperty("user.dir")
			+ "\\Monitoring Tests");

	/** The mon logger. */
	MonLogger monLogger = MonLogger.getInstance();

	// -----------------------------------------------------------------------------
	// -----------------------------------------------------------------------------

	/**
	 * Creates the params for sh.
	 * 
	 * @param currentConfiguration
	 *            the current configuration
	 * @return the string[]
	 */
	private String[] createParamsForSH(Map<String, Object> currentConfiguration) {
		List<String> params = new ArrayList<String>();

		params.add((String) currentConfiguration.get("instance"));
		if (currentConfiguration.get("mdsr") != null)
			params.add("mds");
		if (currentConfiguration.get("mdisr") != null)
			params.add("mdis");
		if (currentConfiguration.get("mdssr") != null)
			params.add("mdss");

		return params.toArray(new String[params.size()]);

	}

	// Will be executed when the "Start Monitoring" button will be pressed
	/**
	 * Start.
	 */
	public void start() {
		try {

			MonLogger.myLogger.log(Level.INFO, "Gui Parameter Check started:");
			/*
			 * GuiParameterCheck guiParameterCheck = new GuiParameterCheck(SID,
			 * hostName, userName,password);
			 * if(!guiParameterCheck.mainGuiCheck(endDate)) { //TODO: Return to
			 * GUI - ZOHAR MonLogger.myLogger.log(Level.INFO,
			 * "Gui parameters check FAILED! return to GUI.."); }
			 * 
			 * MonLogger.myLogger.log(Level.INFO,
			 * "Gui parameters check Success!");
			 */
			MonLogger.myLogger.log(Level.INFO,
					"Kill vmstat and AV_monitoring processes if running");

			runkillSH.killProcesses(mon_file);
			MonLogger.myLogger.log(Level.INFO,
					"Remove old monitoring files from OS");

			runkillSH.removeFilesFromUnix();
			fileManagmentOperations.removeFirstLine(mon_file);
			MonLogger.myLogger.log(Level.INFO,
					"Locate bash installation folder on OS");

			String bash = "#!" + runkillSH.locateBash(getOS());
			fileManagmentOperations.insertTextToFile(0, bash, mon_file);
			fileManagmentOperations.replaceStringInfile("secs=" + interval,
					mon_file);
			MonLogger.myLogger.log(Level.INFO,
					"Copy AV_Monitoring script to OS");
			winUnixOperations.copyToUnix(mon_file);

			MonLogger.myLogger.log(Level.INFO, "Execute Monitoring file on OS");
			runkillSH.ExecuteSh(paramsForSH, mon_file);

			MonLogger.myLogger.log(Level.INFO,
					"Check if clix monitoring enabled");
			if (clixOn) {
				MonLogger.myLogger.log(Level.INFO,
						"clix monitoring enabled - Run clix!");
				clixCommand = new clix(String.valueOf(interval), port,
						hostName, password);
				clixCommand.runClix();

			}
		} catch (Exception e) {
			MonLogger.myLogger.log(Level.WARNING, e.getMessage());
			MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
		}
	}

	/**
	 * Start on time.
	 */
	public void startOnTime() {
		try {
			/*
			 * GuiParameterCheck guiParameterCheck = new GuiParameterCheck(SID,
			 * hostName, userName,password);
			 * if(!guiParameterCheck.mainGuiCheck(endDate)) { //TODO: Return to
			 * GUI - ZOHAR MonLogger.myLogger.log(Level.INFO,
			 * "Gui parameters check FAILED! return to GUI.."); }
			 * MonLogger.myLogger.log(Level.INFO,
			 * "Gui parameters check Success!");
			 */

			Calendar cal = Calendar.getInstance();
			Date date = cal.getTime();
			SimpleDateFormat currentTime = new SimpleDateFormat(
					"dd-mm-yyyy HH:mm:ss");

			// Object currentTime;
			String comparison = dateOperations.compareTwoDates(
					currentTime.format(date), startDate);

			// The start time monitoring time is smaller then current time
			while (comparison.equals("date1Smaller")) {
				cal = Calendar.getInstance();
				date = cal.getTime();
				Thread.sleep(DateOperations.getDateDiff(
						currentTime.format(date), startDate));
				comparison = dateOperations.compareTwoDates(
						currentTime.format(date), startDate);
			}

			// Start Monitoring
			if (comparison.equals("date1Bigger")
					|| comparison.equals("date1Equals")) {
				MonLogger.myLogger.log(Level.INFO, "Program started on time");
				start();
			}

			comparison = dateOperations.compareTwoDates(
					currentTime.format(date), endDate);
			while (comparison.equals("date1Smaller")) {
				cal = Calendar.getInstance();
				date = cal.getTime();
				Thread.sleep(DateOperations.getDateDiff(endDate,
						currentTime.format(date)));
				comparison = dateOperations.compareTwoDates(
						currentTime.format(date), endDate);
			}

			if (comparison.equals("date1Bigger")
					|| comparison.equals("date1Equals")) {
				MonLogger.myLogger.log(Level.INFO, "Program finished on time");
				finishOnTime();
			}
		} catch (Exception e) {
			MonLogger.myLogger.log(Level.WARNING, e.getMessage());
			MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
		}
	}

	// Will be executed when the "Stop Monitoring" button will be pressed
	/**
	 * Finish.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ParseException
	 *             the parse exception
	 */
	public void finish() throws IOException, ParseException {
		try {

			List<String> fileNames = new ArrayList<String>();
			if (mainFolder.exists()) {

				MonLogger.myLogger.log(Level.INFO, "Check tests folder's size");
				mainFolder.checkFolderSize();
				TestFolder = new Folder(mainFolder + "\\" + TestFolderName);

			} else {
				MonLogger.myLogger.log(Level.INFO, "Create tests folder");
				TestFolder = new Folder(System.getProperty("user.dir")
						+ "\\Monitoring Tests\\" + TestFolderName);
			}

			runkillSH.killProcesses(mon_file);
			runkillSH.createFileNames("ls | grep _mon_");
			fileNames = runkillSH.getFileNames(); // Files are created

			runkillSH.copyFilesToWin();
			runkillSH.removeFilesFromUnix();
			
			Boolean clixFileNotExist = false;
			if (clixOn) {
				MonLogger.myLogger.log(Level.INFO, "Stop clix monitoring");
				clixCommand = new clix(String.valueOf(interval), port,
						hostName, password);
				clixCommand.stopClix();
				clixCommand.killProcesses();
				ParseClix parseClix = new ParseClix();
				String parseClixOut = parseClix.parseClixOut("clix_mon.out");
				if (parseClixOut == null)
				{
					clixFileNotExist = true;
				}
				else
				{
					TestFolder.addFileToFolder(parseClixOut);
					MonLogger.myLogger.log(Level.INFO,
							"Add clix monitoring output to test folder"
									+ TestFolderName + getOS());
				}

			}

			// ------------------------------ Date OPERATIONS
			// ------------------------------

			Date startDate = dateOperations.getDateFromFile(fileNames.get(0),
					true); // The monitoring start time , has instance bool
			Date[] dates = null;
			String chartName = null;
			for (String fileName : fileNames) {

				if (fileName.contains("vmstat")) {
					chartName = "vmstat";
					VmstatFileOperations vmstatFileOperations = new VmstatFileOperations();
					int dataLinesNum = vmstatFileOperations
							.getDatalinesNumber(fileName);
					// System.out.println(dataLinesNum );
					dates = dateOperations.promoteTimeWithInterval(startDate,
							interval, dataLinesNum); // Get interval from GUI
					String[] datesStr = dateOperations
							.convertDateArrToStrinArr(dates);
					Converter converter = new Converter();
					fileName = converter.convertTextToCsv(fileName);
					fileManagmentOperations.insertStringToLine(datesStr,
							fileName);
					fileName = Converter.convertCSVToExcel(fileName);
					excelManagement = new ExcelManagement(TestFolder + "\\"
							+ fileName);
					TestFolder.addFileToFolder(fileName);
					MonLogger.myLogger.log(Level.INFO,
							"Add vmstat monitoring output to test folder"
									+ TestFolderName);

				} else if (fileName.contains("md")) {
					if (fileName.contains("mdis"))
						chartName = "MDIS";
					if (fileName.contains("mds"))
						chartName = "MDS";
					if (fileName.contains("mdss"))
						chartName = "MDSS";
					MDMProccessFilesOperations mDMProccessFilesOperations = new MDMProccessFilesOperations();
					int dataLinesNum = mDMProccessFilesOperations
							.getDatalinesNumber(fileName);
					System.out.println(dataLinesNum);
					dates = dateOperations.promoteTimeWithInterval(startDate,
							interval, dataLinesNum - 1); // Get interval from
															// GUI
					String[] datesStr = dateOperations
							.convertDateArrToStrinArr(dates);
					Converter converter = new Converter();
					fileName = converter.convertTextToCsv(fileName);
					fileManagmentOperations.insertStringToLine(datesStr,
							fileName);
					fileName = Converter.convertCSVToExcel(fileName);
					excelManagement = new ExcelManagement(TestFolder + "\\"
							+ fileName);

				}

				int[] Seconds = dateOperations.generateSecondArray(dates);
				int[] Minutes = dateOperations.generateMinuteArray(dates);
				int[] Hours = dateOperations.generateHourArray(dates);
				int[] Days = dateOperations.generateDayArray(dates);
				int[] Months = dateOperations.generateMonthArray(dates);
				int[] Years = dateOperations.generateYearArray(dates);
				// int[] CPU = excelManagement.getCPU(fileName, OS);
				// -------------------------------------------------------------------------------------------------------------
				// ------------------------------------ Chart Operations
				// -------------------------------------------------------

				String OS = getOS();
				MonLogger.myLogger.log(Level.INFO,
						"Charts generation and analysis");
				if (OS.contains("HP-UX") && !fileName.startsWith("vmstat")) {
					// Create %CPU Chart
					Double[] CPU = excelManagement.getCPU(fileName, OS);
					ChartGeneration CPUchartGeneration = new ChartGeneration(OS
							+ " " + chartName, Seconds, Minutes, Hours, Days,
							Months, Years, CPU, chartName);
					TestFolder.addFileToFolder(CPUchartGeneration
							.getImageName());
					MonLogger.myLogger.log(Level.INFO,
							"Add CPU chart image to test folder"
									+ TestFolderName);
					CPUchartGeneration.pack();
					//RefineryUtilities.centerFrameOnScreen(CPUchartGeneration);
					//CPUchartGeneration.setVisible(true);

					// Create VSZ Chart
					Double[] VSZ = excelManagement.getMemory("VSZ", fileName,
							OS);
					ChartGeneration VSZchartGeneration = new ChartGeneration(OS
							+ " " + chartName, Seconds, Minutes, Hours, Days,
							Months, Years, VSZ, null, chartName);
					TestFolder.addFileToFolder(VSZchartGeneration
							.getImageName());
					MonLogger.myLogger.log(Level.INFO,
							"Add memory chart image to test folder"
									+ TestFolderName);
					VSZchartGeneration.pack();
					//RefineryUtilities.centerFrameOnScreen(VSZchartGeneration);
					//VSZchartGeneration.setVisible(true);
					TestFolder.addFileToFolder(fileName);
					MonLogger.myLogger.log(Level.INFO,
							"Add mds/mdis/mdss monitoring output to test folder"
									+ TestFolderName);
					if (fileName.startsWith("mds_") && clixFileNotExist == false ) {
						excelManagement.mainExcelFlow(4, memDiff, interval,
								TestFolder, "VSZ_Diff");
					}

				}

				if (!OS.contains("HP-UX") && !fileName.startsWith("vmstat")) {
					// Create %CPU Chart
					Double[] CPU = excelManagement.getCPU(fileName, OS);
					ChartGeneration CPUchartGeneration = new ChartGeneration(OS
							+ " " + chartName, Seconds, Minutes, Hours, Days,
							Months, Years, CPU, chartName);
					MonLogger.myLogger.log(Level.INFO,
							"Add CPU chart image to test folder"
									+ TestFolderName);
					TestFolder.addFileToFolder(CPUchartGeneration
							.getImageName());

					CPUchartGeneration.pack();
					/*
					 * RefineryUtilities.centerFrameOnScreen(CPUchartGeneration);
					 * CPUchartGeneration.setVisible(true);
					 */

					// Create VSZ + RSS Chart
					Double[] VSZ = excelManagement.getMemory("VSZ", fileName,
							OS);
					Double[] RSS = excelManagement.getMemory("RSS", fileName,
							OS);
					ChartGeneration RSSVSZchartGeneration = new ChartGeneration(
							OS + " " + chartName, Seconds, Minutes, Hours,
							Days, Months, Years, VSZ, RSS, chartName);
					MonLogger.myLogger.log(Level.INFO,
							"Add memory chart image to test folder"
									+ TestFolderName);
					TestFolder.addFileToFolder(RSSVSZchartGeneration
							.getImageName());

					RSSVSZchartGeneration.pack();
					MonLogger.myLogger.log(Level.INFO,
							"Add mds/mdis/mdss monitoring output to test folder"
									+ TestFolderName);
					TestFolder.addFileToFolder(fileName);
					if (fileName.startsWith("mds_") && TestFolder.getFileNames().contains("clix_mon.xls") ) 
					{
						excelManagement.mainExcelFlow(5, memDiff, interval,
								TestFolder, "VSZ_Diff" );
						excelManagement.mainExcelFlow(6, memDiff, interval,
								TestFolder, "RSS_Diff" );

					}

					// RefineryUtilities.centerFrameOnScreen(RSSVSZchartGeneration);
					// RSSVSZchartGeneration.setVisible(true);
				}
			}
		} catch (Exception e) {
			MonLogger.myLogger.log(Level.WARNING, e.getMessage());
			MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
		} finally // Adding Created test folder with its files to current
		{
			if (!TestFolderName.isEmpty()) {
				CSharedInstance sharedInstance = CSharedInstance.getInstance();

				sharedInstance.currentDataFilesID = TestFolder
						.getAbsolutePath();
				;

				sharedInstance
						.addNewDataFiles(
								sharedInstance.currentDataFilesID,
								sharedInstance
										.getAllFilesInDirectory(sharedInstance.currentDataFilesID));
			}
		}

	}

	/**
	 * Check time.
	 * 
	 * @return true, if successful
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws ParseException
	 *             the parse exception
	 */
	private boolean checkTime() throws InterruptedException, ParseException {
		MonLogger.myLogger.log(Level.INFO, "Check Time invoked");
		boolean TF = false;
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		SimpleDateFormat currentTime = new SimpleDateFormat(
				"dd-mm-yyyy HH:mm:ss");
		// Object currentTime;
		String comparison = dateOperations.compareTwoDates(
				currentTime.format(date), startDate);
		while (comparison.equals("date1Smaller")) {
			cal = Calendar.getInstance();
			date = cal.getTime();
			Thread.sleep(DateOperations.getDateDiff(startDate,
					currentTime.format(date)));
			comparison = dateOperations.compareTwoDates(
					currentTime.format(date), startDate);
		}
		if (comparison.equals("date1Bigger")
				|| comparison.equals("date1Equals")) {
			TF = true;
			;
			MonLogger.myLogger.log(Level.INFO, "Time was reached");
		}
		return TF;

	}

	/**
	 * Finish on time.
	 * 
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws ParseException
	 *             the parse exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void finishOnTime() throws InterruptedException, ParseException,
			IOException {
		if (checkTime()) {
			MonLogger.myLogger.log(Level.INFO,
					"Finished on time entered by user");
			finish();
		}
	}

}
