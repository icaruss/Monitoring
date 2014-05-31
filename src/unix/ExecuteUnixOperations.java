package unix;

import Clix.ParseClix;
import Clix.clix;

import java.util.logging.Level;
import java.util.logging.Logger;
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





import org.jfree.ui.RefineryUtilities;

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


public class ExecuteUnixOperations extends CommandExecuter
{
	public String getOS()
	{
		String tmpOS = null;
		try {
			tmpOS =  new String(this.execute("uname -s"));
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tmpOS;
	}
	
	
    public ExecuteUnixOperations(String hostName, String userName,
			String password, JSch jschSSHChannel, Session sesConnection,
			int intTimeOut) 
    {
		super(hostName, userName, password, jschSSHChannel, sesConnection, intTimeOut);


	}
    
    public ExecuteUnixOperations() 
    {
		super(hostName, userName, password, jschSSHChannel, sesConnection, intTimeOut);

	}
    
   

    // PARAMETERS TO GET FROM GUI  -  START   TODO
    /////////////////////////////////////////
    String port="59950";
    String[] paramsForSH = {"MQM"};
    Boolean clixOn = true;
    String SID = "MQM";     // 
    int interval = 5 ;
    /////////////////////////////////////////
    // PARAMETERS TO GET FROM GUI  -  END

	
    public ExecuteUnixOperations(Map<String, Object> currentConfiguration) 
    {
    	super( (String)(currentConfiguration.get("hostName")) , 
    			(String)(currentConfiguration.get("userName")) , 
    			(String)(currentConfiguration.get("password")) , 
    			(JSch)(currentConfiguration.get("jschSSHChannel")) , 
    			(Session)(currentConfiguration.get("sesConnection")) , 
    			0);
    	
    	String m_port = (String)(currentConfiguration.get("port"));
    	port = (null != m_port) ? m_port : "59950";
    	
    	String m_interval = (String)(currentConfiguration.get("interval"));
    	interval = (null != m_interval) ? Integer.parseInt(m_interval) : 5;
    	
    }




	private RunkillSH runkillSH = new RunkillSH();
 //   private MDMRelatedOperations mdmRelatedOperations = new MDMRelatedOperations();
    private FileManagmentOperations fileManagmentOperations = new FileManagmentOperations();
    private DateOperations dateOperations = new DateOperations();
    String mon_file = "AV_monitoring.sh";

    String startDate;
  
    private ExcelManagement excelManagement = null;
    clix clixCommand = null;
    private winUnixOperations winUnixOperations = new winUnixOperations();
    
	//------------------------------ Place files in Folder ------------------------
	
	/* 1. The current monitoring files will be placed under a folder named by a local time string
	 * 2. The last 10 files will be placed under the folder named "Monitoring Tests", and will contain
	 * by default the last 10 tests.
	 * 
	 */
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd HH_mm_ss");
	   //get current date time with Date()
	Date date = new Date();
	String TestFolderName = dateFormat.format(date) + getOS();
	
	Folder TestFolder;
	
	Folder mainFolder = new Folder(System.getProperty("user.dir") + "\\Monitoring Tests");
	MonLogger monLogger = MonLogger.getInstance();
	
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------
 
    
    // Will be executed when the "Start Monitoring" button will be pressed
    public void start()
    {
    	try
    	{
    		

			MonLogger.myLogger.log(Level.INFO, "Program started immediatly");
			MonLogger.myLogger.log(Level.INFO, "Gui Parameter Check started:");
    		GuiParameterCheck guiParameterCheck =  new GuiParameterCheck(SID, hostName, userName,password);
    		if(!guiParameterCheck.mainGuiCheck())
    		{
    			//TODO: Return to GUI   - ZOHAR
    			MonLogger.myLogger.log(Level.INFO, "Gui parameters check FAILED! return to GUI..");
    		}
    		
    		MonLogger.myLogger.log(Level.INFO, "Gui parameters check Success!");
    		MonLogger.myLogger.log(Level.INFO, "Kill vmstat and AV_monitoring processes if running");
    		
    		runkillSH.killProcesses(mon_file);
    
    		MonLogger.myLogger.log(Level.INFO, "Remove old monitoring files from OS");
    		
    		runkillSH.removeFilesFromUnix();
    		fileManagmentOperations.removeFirstLine(mon_file);
    		MonLogger.myLogger.log(Level.INFO, "Locate bash installation folder on OS");
    		
    		String bash = "#!" + runkillSH.locateBash(getOS());
    		fileManagmentOperations.insertTextToFile(0, bash, mon_file);  	
    		
    		MonLogger.myLogger.log(Level.INFO, "Copy AV_Monitoring script to OS");
    		winUnixOperations.copyToUnix(mon_file);
    		
    		MonLogger.myLogger.log(Level.INFO, "Execute Monitoring file on OS");
    		runkillSH.ExecuteSh(paramsForSH, mon_file);
   
    		MonLogger.myLogger.log(Level.INFO, "Check if clix monitoring enabled");
    		if (clixOn)
    		{
    			MonLogger.myLogger.log(Level.INFO, "clix monitoring enabled - Run clix!");
    			 clixCommand = new clix(String.valueOf(interval), port, hostName, password);
    			 clixCommand.runClix();
    			 

    		}
    	}
    	catch (Exception e)
    	{
    		MonLogger.myLogger.log(Level.WARNING, e.getMessage());
    		MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
    	}
    }
    
    
    public void startOnTime()
    {
    	try
    	{
    		GuiParameterCheck guiParameterCheck =  new GuiParameterCheck(SID, hostName, userName,password);
    		if(!guiParameterCheck.mainGuiCheck())
    		{
    			//TODO: Return to GUI   - ZOHAR
    			MonLogger.myLogger.log(Level.INFO, "Gui parameters check FAILED! return to GUI..");
    		}
    		MonLogger.myLogger.log(Level.INFO, "Gui parameters check Success!");
    		
    		
    		Calendar cal = Calendar.getInstance();
        	cal.getTime();
        	SimpleDateFormat currentTime = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");
        	//Object currentTime;
			String comparison = dateOperations.compareTwoDates(currentTime.toString(), startDate);
			while (comparison.equals("date1Smaller"))
			{
				Thread.sleep(DateOperations.getDateDiff(startDate, currentTime.toString()));
				comparison = dateOperations.compareTwoDates(currentTime.toString(), startDate);
			}
			if (comparison.equals("date1Bigger") || comparison.equals("date1Equals"))
			{
				MonLogger.myLogger.log(Level.INFO, "Program started on time");
				MonLogger.myLogger.log(Level.INFO, "Kill vmstat and AV_monitoring processes if running");
	    		runkillSH.killProcesses(mon_file);
	    		

	    		MonLogger.myLogger.log(Level.INFO, "Remove old monitoring files from OS");
	    		
	    		runkillSH.removeFilesFromUnix();
	    		fileManagmentOperations.removeFirstLine(mon_file);
	    		MonLogger.myLogger.log(Level.INFO, "Locate bash installation folder on OS");
	    		
	    		String bash = "#!" + runkillSH.locateBash(getOS());
	    		fileManagmentOperations.insertTextToFile(0, bash, mon_file);  	
	    		
	    		MonLogger.myLogger.log(Level.INFO, "Copy AV_Monitoring script to OS");
	    		winUnixOperations.copyToUnix(mon_file);
	    		
	    		MonLogger.myLogger.log(Level.INFO, "Execute Monitoring file on OS");
	    		runkillSH.ExecuteSh(paramsForSH, mon_file);
	   
	    		MonLogger.myLogger.log(Level.INFO, "Check if clix monitoring enabled");
	    		if (clixOn)
	    		{
	    			MonLogger.myLogger.log(Level.INFO, "clix monitoring enabled - Run clix!");
	    			 clixCommand = new clix(String.valueOf(interval), port, hostName, password);
	    			 clixCommand.runClix();
	    			 

	    		}
	    			
			}
    	}
    	catch (Exception e)
    	{
    		MonLogger.myLogger.log(Level.WARNING, e.getMessage());
    		MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
    	}
    }
    
    // Will be executed when the "Stop Monitoring" button will be pressed
    public void finish() throws IOException, ParseException
    {
    	try
    	{
    		
	    	 List<String> fileNames = new ArrayList<String>();
	    	 if( mainFolder.exists())
	    		{
	    			
	    		 	MonLogger.myLogger.log(Level.INFO, "Check tests folder's size");
	    		 	mainFolder.checkFolderSize();
	    			TestFolder = new Folder( mainFolder + "\\" + TestFolderName + getOS());
	    			
	    		}
	    		else
	    		{
	    			MonLogger.myLogger.log(Level.INFO, "Create tests folder");
	    			TestFolder = new Folder(System.getProperty("user.dir")  + "\\Monitoring Tests\\" + TestFolderName + getOS());
	    		}		
		
	    	
	    	runkillSH.killProcesses(mon_file);
	    	runkillSH.createFileNames("ls | grep _mon_");
	    	fileNames = runkillSH.getFileNames();						// Files are created
	    	
	    	runkillSH.copyFilesToWin();
	    	runkillSH.removeFilesFromUnix();
			if (clixOn)
			{
				MonLogger.myLogger.log(Level.INFO, "Stop clix monitoring");
				 clixCommand = new clix(String.valueOf(interval), port, hostName, password);
				 clixCommand.stopClix();
				 clixCommand.killProcesses();
				 ParseClix parseClix = new ParseClix();
				 TestFolder.addFileToFolder(parseClix.parseClixOut("clix_mon.out"));
				 MonLogger.myLogger.log(Level.INFO, "Add clix monitoring output to test folder" + TestFolderName + getOS());
	
			}

			
			
	    	//------------------------------ Date OPERATIONS ------------------------------
	    	
	    	Date startDate = dateOperations.getDateFromFile(fileNames.get(0), true);		//The monitoring start time  , has instance bool
	    	Date[] dates = null;
	    	String chartName = null;
			for (String fileName : fileNames) 
			{
				
				if (fileName.contains("vmstat"))
				{
					chartName = "vmstat";
					VmstatFileOperations vmstatFileOperations = new VmstatFileOperations();
					int dataLinesNum = vmstatFileOperations.getDatalinesNumber(fileName);
					//System.out.println(dataLinesNum );
					dates = dateOperations.promoteTimeWithInterval(startDate, interval, dataLinesNum);    //Get interval from GUI
					String[] datesStr = dateOperations.convertDateArrToStrinArr(dates);
					Converter converter = new Converter();
					fileName = converter.convertTextToCsv(fileName);
					fileManagmentOperations.insertStringToLine(datesStr, fileName);
					fileName = Converter.convertCSVToExcel(fileName);	
					excelManagement =  new ExcelManagement(TestFolder + "\\" + fileName);	
					TestFolder.addFileToFolder(fileName);
					MonLogger.myLogger.log(Level.INFO, "Add vmstat monitoring output to test folder" + TestFolderName);
						
				}
				else if (fileName.contains("md"))
				{
					if (fileName.contains("mdis"))		
						chartName = "MDIS";
					if (fileName.contains("mds"))	
						chartName = "MDS";
					if (fileName.contains("mdss"))		
						chartName = "MDSS";
					MDMProccessFilesOperations mDMProccessFilesOperations = new MDMProccessFilesOperations();
					int dataLinesNum = mDMProccessFilesOperations.getDatalinesNumber(fileName);
					System.out.println(dataLinesNum );
					dates = dateOperations.promoteTimeWithInterval(startDate, interval, dataLinesNum - 1);    //Get interval from GUI
					String[] datesStr = dateOperations.convertDateArrToStrinArr(dates);
					Converter converter = new Converter();
					fileName = converter.convertTextToCsv(fileName);
					fileManagmentOperations.insertStringToLine(datesStr, fileName);
					fileName = Converter.convertCSVToExcel(fileName);	
					excelManagement =  new ExcelManagement(TestFolder + "\\" + fileName);
					
				}
				
				
				int[] Seconds = dateOperations.generateSecondArray(dates);
				int[] Minutes = dateOperations.generateMinuteArray(dates);
				int[] Hours = dateOperations.generateHourArray(dates);
				int[] Days = dateOperations.generateDayArray(dates);
				int[] Months = dateOperations.generateMonthArray(dates);
				int[] Years = dateOperations.generateYearArray(dates);
				//int[] CPU = excelManagement.getCPU(fileName, OS);
				//-------------------------------------------------------------------------------------------------------------
				//------------------------------------ Chart Operations -------------------------------------------------------
				
				
				String OS = getOS();
				MonLogger.myLogger.log(Level.INFO, "Charts generation and analysis");
				if (OS.contains("HP-UX") && !fileName.startsWith("vmstat"))
				{
					// Create %CPU Chart
					Double[] CPU = excelManagement.getCPU(fileName, OS);
					ChartGeneration CPUchartGeneration = new ChartGeneration(OS + " " + chartName, Seconds, Minutes, Hours, Days, Months, Years, CPU, chartName);
					TestFolder.addFileToFolder(CPUchartGeneration.getImageName());
					MonLogger.myLogger.log(Level.INFO, "Add CPU chart image to test folder" + TestFolderName);
					CPUchartGeneration.pack();
					RefineryUtilities.centerFrameOnScreen(CPUchartGeneration);
					CPUchartGeneration.setVisible(true);
					
					// Create VSZ Chart
					Double[] VSZ = excelManagement.getMemory("VSZ", fileName, OS);
					ChartGeneration VSZchartGeneration = new ChartGeneration(OS + " " + chartName , Seconds, Minutes, Hours, Days, Months, Years, VSZ, null, chartName);
					TestFolder.addFileToFolder(VSZchartGeneration.getImageName());
					MonLogger.myLogger.log(Level.INFO, "Add memory chart image to test folder" + TestFolderName);
					VSZchartGeneration.pack();
					RefineryUtilities.centerFrameOnScreen(VSZchartGeneration);
					VSZchartGeneration.setVisible(true);
					TestFolder.addFileToFolder(fileName);
					MonLogger.myLogger.log(Level.INFO, "Add mds/mdis/mdss monitoring output to test folder" + TestFolderName);
					if (fileName.startsWith("mds_"))
					{
						excelManagement.mainExcelFlow(4, 0, interval, TestFolder);	
					}
					
				}
				
				if (!OS.contains("HP-UX") && !fileName.startsWith("vmstat"))
				{
					// Create %CPU Chart
					Double[] CPU = excelManagement.getCPU(fileName, OS);
					ChartGeneration CPUchartGeneration = new ChartGeneration(OS + " " + chartName, Seconds, Minutes, Hours, Days, Months, Years, CPU, chartName);
					MonLogger.myLogger.log(Level.INFO, "Add CPU chart image to test folder" + TestFolderName);
					TestFolder.addFileToFolder(CPUchartGeneration.getImageName());

					CPUchartGeneration.pack();
			/*		RefineryUtilities.centerFrameOnScreen(CPUchartGeneration);
					CPUchartGeneration.setVisible(true);*/
					
					// Create VSZ + RSS Chart
					Double[] VSZ = excelManagement.getMemory("VSZ", fileName, OS);
					Double[] RSS = excelManagement.getMemory("RSS", fileName, OS);
					ChartGeneration RSSVSZchartGeneration = new ChartGeneration(OS + " " + chartName , Seconds, Minutes, Hours, Days, Months, Years, VSZ, RSS, chartName);
					MonLogger.myLogger.log(Level.INFO, "Add memory chart image to test folder" + TestFolderName);
					TestFolder.addFileToFolder(RSSVSZchartGeneration.getImageName());

					RSSVSZchartGeneration.pack();	
					MonLogger.myLogger.log(Level.INFO, "Add mds/mdis/mdss monitoring output to test folder" + TestFolderName);
					TestFolder.addFileToFolder(fileName);
					if (fileName.startsWith("mds_"))
					{
						excelManagement.mainExcelFlow(6, 0, interval, TestFolder);	

					}
					
					
					//RefineryUtilities.centerFrameOnScreen(RSSVSZchartGeneration);
					//RSSVSZchartGeneration.setVisible(true);
				}
				
				
				

			}
			
	    
		}
    	catch (Exception e)
		{
    		MonLogger.myLogger.log(Level.WARNING, e.getMessage());
    		MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
		}
		    	
    }
    private boolean checkTime() throws InterruptedException, ParseException
    {
		MonLogger.myLogger.log(Level.INFO, "Check Time invoked");
    	boolean TF = false;
		Calendar cal = Calendar.getInstance();
    	cal.getTime();
    	SimpleDateFormat currentTime = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");
    	//Object currentTime;
		String comparison = dateOperations.compareTwoDates(currentTime.toString(), startDate);
		while (comparison.equals("date1Smaller"))
		{
			Thread.sleep(DateOperations.getDateDiff(startDate, currentTime.toString()));
			comparison = dateOperations.compareTwoDates(currentTime.toString(), startDate);
		}
		if (comparison.equals("date1Bigger") || comparison.equals("date1Equals"))
		{
			TF = true;;
			MonLogger.myLogger.log(Level.INFO, "Time was reached");
		}
		return TF;
    	
    }
    
    

    
    
    public void finishOnTime() throws InterruptedException, ParseException, IOException
    {
    	if (checkTime())
    	{
    		MonLogger.myLogger.log(Level.INFO, "Finished on time entered by user");
    		finish();
    	}
    }

}
