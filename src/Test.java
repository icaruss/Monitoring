/*
 * 
 */

import java.io.IOException;
import java.text.ParseException;

import org.jfree.ui.RefineryUtilities;

import unix.ExecuteUnixOperations;
import Charts.ChartGeneration;
import FilesManagment.ExcelManagement;
import FilesManagment.Folder;

import com.jcraft.jsch.JSchException;

// TODO: Auto-generated Javadoc
/**
 * The Class Test.
 */
public class Test {

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws JSchException
	 *             the j sch exception
	 * @throws ParseException
	 *             the parse exception
	 */
	public static void main(String[] args) throws IOException, JSchException,
			ParseException {
		// UnixContext unixContext = new UnixContext();
		// UnixConnection unixConnection = new
		// UnixConnection("iltlvh325","sapinst", "a2i2000!", null, null, 0);
		// CommandExecuter commandExecuter = new
		// CommandExecuter("iltlvh325","sapinst", "a2i2000!", null, null, 0);
		// unixConnection.connectToUnix();
		// commandExecuter.execute("uname -d");
		// unixConnection.disconnectFromUnix();
		// Calendar cal = Calendar.getInstance();
		// cal.getTime();
		// SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		// System.out.println( sdf.format(cal.getTime()) );
		// UnixConnection unixConnection = new UnixConnection("iltlvh325",
		// "sapinst","a2i2000!", null, null, 0);

/*		ExecuteUnixOperations executeUnixOperations = new ExecuteUnixOperations(
				"iltlvh325", "h26adm", "a2i2000!", null, null, 0);
		executeUnixOperations.finish();*/
		

/*		Folder testFolder = new Folder(System.getProperty("user.dir")
				+ "\\Monitoring Tests\\2014_09_12 22_54_06_SunOS");
		ExcelManagement excelManagement = new ExcelManagement(testFolder + "\\"
				+ "mds_mon_Q56_12-09-2014_19-56-03.xls");
		excelManagement.mainExcelFlow(6, 5, 5,
				testFolder, "VSZ_Diff" );
		excelManagement.mainExcelFlow(7, 5, 5,
				testFolder, "RSS_Diff" );*/
		
		
		ChartGeneration RSSVSZchartGeneration = new ChartGeneration(
				"temp", new int[]{1,2,3,4,5,6}, new int[]{10,20,30,40,50,60}, new int[]{1,1,1,1,1,1},
				new int[]{1,1,1,1,1,1}, new int[]{1,1,1,1,1,1}, new int[]{1900,1900,1900,1900,1900,1900}, new Double[]{200.0,300.0,150.0,200.0,300.0,150.0}, new Double[]{500.0,700.0,950.0,500.0,700.0,950.0},"chartName");
		RSSVSZchartGeneration.pack();
		RefineryUtilities.centerFrameOnScreen(RSSVSZchartGeneration);
		RSSVSZchartGeneration.setVisible(true);
		
	}
}
