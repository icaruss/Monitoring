package FilesManagment;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import log.MonLogger;

public class DateOperations extends FilesManagment
{
	public DateOperations() {
		super(fileName);
		// TODO Auto-generated constructor stub
	}


	public Date[] promoteTimeWithInterval(Date date, int interval, int fileLineNum )
	{
		//String[] DateArrayStr = new String[fileLineNum];
		Date[] dateArr = new Date[fileLineNum];
		Calendar cl = Calendar. getInstance();
	    cl.setTime(date);
	    for (int i = 0; i < dateArr.length ; i++)
	    {	
	    	if (i == 0)	    
		    {
		    	cl. add(Calendar.SECOND, 0);
			     dateArr[i] = cl.getTime();		
		    }
	    	else
	    	{
		     cl. add(Calendar.SECOND, interval);
		     dateArr[i] = cl.getTime();		
	    	}
	    }
	      
	    return dateArr;
	}

	
	public String[] convertDateArrToStrinArr(Date[] dateArr) throws ParseException
	{
		String[] dateArrStr = new String[dateArr.length];
	    for (int i = 0; i < dateArr.length ; i++)
	    {		    
			Format formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			String s = formatter.format(dateArr[i]);
			
		    dateArrStr[i] = s + "GMT";
		     
		     
	    }	      
	    return dateArrStr;	
	}
	
	public void splitDateToTimeArrays(Date[] dateArr)
	{
		int arrLenght = dateArr.length;
		int[] SecondArr =  new int[arrLenght];
		int[] MinuteArr =  new int[arrLenght];
		int[] HourArr =  new int[arrLenght];
		int[] DayArr =  new int[arrLenght];
		int[] YearArr =  new int[arrLenght];
		Calendar calendar = Calendar.getInstance();
		for (int i = 0 ; i < dateArr.length ; i++)
		{
			
			calendar.setTime(dateArr[i]);
			SecondArr[i] = calendar.get(Calendar.SECOND);
			MinuteArr[i] = calendar.get(Calendar.MINUTE);
			HourArr[i] = calendar.get(Calendar.HOUR_OF_DAY);
			DayArr[i] = calendar.get(Calendar.DAY_OF_MONTH);
			YearArr[i] = calendar.get(Calendar.YEAR);
		}
	}
	
	
	// interval is taken from the user
	public List<Integer> findAllDatesInInterval(Date date1, int interval, Date[] clixDateArr)
	{
		Date date2 = new Date();
		List <Integer> list = new ArrayList<Integer>();
		Calendar cl = Calendar. getInstance();
	    cl.setTime(date1);
	  
		cl. add(Calendar.SECOND, interval);
		date2 = cl.getTime();	
		for (int i = 0 ; i < clixDateArr.length ; i++)
		{
			if (clixDateArr[i].after(date2))
			{
				break;
			}
			if (clixDateArr[i].equals(date1) || clixDateArr[i].equals(date2) || (clixDateArr[i].after(date1) && clixDateArr[i].before(date2)))
			{
				list.add(i+2);
			}
		}
//		list.add(lastIterator);
		return list;
	   
	}
	
	
	public String compareTwoDates(String strDate1, String strDate2)
	{
		String result = null;
		try
		{
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");
	    	Date date1 = sdf.parse("strDate1");
	    	Date date2 = sdf.parse("strDate2");
	
	    	System.out.println(sdf.format(date1));
	    	System.out.println(sdf.format(date2));
	
	    	Calendar cal1 = Calendar.getInstance();
	    	Calendar cal2 = Calendar.getInstance();
	    	cal1.setTime(date1);
	    	cal2.setTime(date2);
	
	    	if(cal1.after(cal2))
	    	{
	    		//System.out.println("Date1 > Date2");
	    		result = "date1Bigger";
	    	}
	
	    	if(cal1.before(cal2)){
	    		//System.out.println("Date1 < Date2");
	    		result = "date1Smaller";
	    	}
	
	    	if(cal1.equals(cal2)){
	    		//System.out.println("Date1 == Date2");
	    		result = "date1Equals";
	    	}
    	
	}
		
	catch(ParseException e){
		MonLogger.myLogger.log(Level.WARNING, e.getMessage());
		MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
		e.printStackTrace();
	}
		return result;
	}
	
	public static long getDateDiff(String strDate1, String strDate2) throws ParseException 
	{
			TimeUnit timeUnit = TimeUnit.MILLISECONDS;
			SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");
	    	Date date1 = sdf.parse("strDate1");
	    	Date date2 = sdf.parse("strDate2");
		    long diffInMillies = date2.getTime() - date1.getTime();
		    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);

	}
	
	//parses the date from the file name
	public Date getDateFromFile(String FileName, Boolean hasInstance)
	{
		//Month monthPrefix; 
		//String ExtractedfileName = MF.extractFileNameFromPath(FileName);
		
		String ExtractedfileName = FileName;
	    char[] dest = new char[3];
	    Date date = new Date();
	   // String dateStr;
	    int instance = 0;
	    if (hasInstance == true)
	    {
	    	instance = 4;
	    }
	    else
	    	instance = 0;
	    
	    try 
	    {    	
	    	// mdis_mon_06-01-2013_18-50-23.txt
	    	
	    	dest = new char[4];
	    	ExtractedfileName.getChars(0, 4, dest, 0);
	    	
            if ((new String(dest).equalsIgnoreCase("mdis"))  || (new String(dest).equalsIgnoreCase("mdss")))
            {
 
            	dest = new char[10];
            	ExtractedfileName.getChars(9 +instance, 19 + instance, dest, 0);    //get date
		    	 String strFullDate = new String(dest);
		    	 dest = new char[8];
		    	 ExtractedfileName.getChars(20 + instance, 28 + instance, dest, 0);    //get time
		    	 strFullDate = strFullDate + " " + new String(dest);
		    	 DateFormat formatter ; 
		    	 formatter = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss"); 
		    	 date = formatter.parse(strFullDate);  

            }
            
            else if (new String(dest).equalsIgnoreCase("mds_"))
            {
            	// mds_mon_06-01-2013_18-50-23.txt
            	 
            	dest = new char[10];
            	ExtractedfileName.getChars(8  + instance, 18  + instance, dest, 0);    //get date
		    	 String strFullDate = new String(dest); 
		    	 dest = new char[8];
		    	 ExtractedfileName.getChars(19 + instance, 27 + instance, dest, 0);    //get time
		    	 strFullDate = strFullDate + " " + new String(dest);
		    	 DateFormat formatter ; 
		    	 formatter = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss"); 
		    	 date = formatter.parse(strFullDate);  
		    	 //System.out.println(formatter.format(date));     //Prints the date in the format of dd-mm-yyyy HH-mm-ss
            }
            

	    }
	    catch (Exception e) 
	    {
	    	MonLogger.myLogger.log(Level.WARNING, e.getMessage());
    		MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
	    } 
	    finally 
	    {
	    }
		return date;

	}
	
	public Date convertStringToDate(String dateStr)
	{
		 Date date = new Date(); 
		try
		{
			 date = new Date(); 
			 DateFormat formatter ; 
	    	 formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss"); 
	    	 date = formatter.parse(dateStr); 
	    //	 System.out.println(date);
		}
		catch( ParseException e)
		{
			MonLogger.myLogger.log(Level.WARNING, e.getMessage());
    		MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
		}
		return date;
		
	}
	
	

	public int[] generateSecondArray(Date[] dateArr)   // dateArr is generated from promoteTimeWithInterval() method
	{
		int[] SecondArr =  new int[dateArr.length];
		Calendar calendar = Calendar.getInstance();
		for (int i = 0 ; i < dateArr.length ; i++)
		{
			
			calendar.setTime(dateArr[i]);
			SecondArr[i] = calendar.get(Calendar.SECOND);
		}
		return SecondArr;
		
	}
	
	public int[] generateMinuteArray(Date[] dateArr)
	{
		int[] MinuteArr =  new int[dateArr.length];
		Calendar calendar = Calendar.getInstance();
		for (int i = 0 ; i < dateArr.length ; i++)
		{
			
			calendar.setTime(dateArr[i]);
			MinuteArr[i] = calendar.get(Calendar.MINUTE);
		}
		return MinuteArr;
	}
	
	public int[] generateHourArray(Date[] dateArr)
	{
	
		int[] HourArr =  new int[dateArr.length];
		Calendar calendar = Calendar.getInstance();
		for (int i = 0 ; i < dateArr.length ; i++)
		{
			
			calendar.setTime(dateArr[i]);
			HourArr[i] = calendar.get(Calendar.HOUR_OF_DAY);
		}
		return HourArr;
	}
	
	public int[] generateDayArray(Date[] dateArr)
	{
		int[] DayArr =  new int[dateArr.length];
		Calendar calendar = Calendar.getInstance();
		for (int i = 0 ; i < dateArr.length ; i++)
		{
			
			calendar.setTime(dateArr[i]);
			DayArr[i] = calendar.get(Calendar.DAY_OF_MONTH);
		}
		return DayArr;
	}
	
	public int[] generateMonthArray(Date[] dateArr)
	{
		int[] MonthArr =  new int[dateArr.length];
		Calendar calendar = Calendar.getInstance();
		for (int i = 0 ; i < dateArr.length ; i++)
		{
			
			calendar.setTime(dateArr[i]);
			MonthArr[i] = calendar.get(Calendar.MONTH) + 1 ;
		}
		return MonthArr;
	}
	
	public int[] generateYearArray(Date[] dateArr)
	{
		
		int[] YearArr =  new int[dateArr.length];
		Calendar calendar = Calendar.getInstance();
		for (int i = 0 ; i < dateArr.length ; i++)
		{
			
			calendar.setTime(dateArr[i]);
			YearArr[i] = calendar.get(Calendar.YEAR);
		}
		return YearArr;
	}
	
	
	
}


