package FilesManagment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

public class Converter extends FilesManagment
{
	

	public Converter() {
		super(fileName);
		// TODO Auto-generated constructor stub
	}


	public String convertTextToCsv(String inputFileName) {
		String line = null;
		File inputFile = new File(inputFileName);
		String[] FileNameAndSuffix= inputFileName.split("\\.");
		String newFileName = FileNameAndSuffix[0] + ".csv";
		
		
		try {
			BufferedReader reader = new BufferedReader(
					new FileReader(inputFile));
			FileWriter fstream = new FileWriter(newFileName, true);
			BufferedWriter out = new BufferedWriter(fstream);
			int count = 0;

			// repeat until all lines is read
			while ((line = reader.readLine()) != null) 
			{
/*					if (count == 0) 
					{

						line = line.replaceAll("\\s+", ",");
						out.write(line);
						out.newLine();
						out.flush();
						// System.out.println(line.length());
					}*/
					if (count >= 0 && count < (getTotalLinesNum(inputFileName) - 2) &&  inputFileName.contains("md")) {

						// System.out.println(line.length());
/*						String first = line.substring(2, startIndex);
						// System.out.println(first);
						String Second = line
								.substring(endIndex, line.length());
						// System.out.println(Second);
						line = first + " " + Second;*/	//Not needed, since the ps command output was changed
						out.write(line.replaceAll("\\s+", ","));
						out.newLine();
						out.flush();

					}
					if (count > 1 && count < (getTotalLinesNum(inputFileName) - 3) &&  inputFileName.contains("vmstat"))
					{
						line = line.replaceAll("\\s+", ",");
						out.write(line);
						out.newLine();
						out.flush();
					}
					count++;
			}
			out.close();
			reader.close();
			inputFile.delete();
		}

		catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return newFileName;
	}
	
    public static String convertCSVToExcel(String fname) throws IOException
    { 
    
	    String[] FileNameAndSuffix= fname.split("\\.");
	    String fileName = FileNameAndSuffix[0];
	    ArrayList arList=null;
	    ArrayList al=null;
	    String inputName = fileName + ".csv";
	    String thisLine; 
	    int count=0; 
	     FileInputStream fis = new FileInputStream(inputName);
	     DataInputStream myInput = new DataInputStream(fis);
	    int i=0;
	    arList = new ArrayList();
	    while ((thisLine = myInput.readLine()) != null)
	    {
		     al = new ArrayList();
		     String strar[] = thisLine.split(",");
		     for(int j=0;j<strar.length;j++)
		     {
		    	 al.add(strar[j]);
		     }
		     arList.add(al);
	     //System.out.println();
	     i++;
    } 

    try
    {
	     HSSFWorkbook hwb = new HSSFWorkbook();
	     HSSFSheet sheet = hwb.createSheet("new sheet");
	     HSSFCellStyle style = hwb.createCellStyle();
	     style.setWrapText(true);
	     
	      for(int k=0;k<arList.size();k++)
	      {
	       ArrayList ardata = (ArrayList)arList.get(k);
	       HSSFRow row = sheet.createRow((short) 0+k);
	       
	       for(int p=0;p<ardata.size();p++)
	       {
	    	   sheet.autoSizeColumn(p);
	        HSSFCell cell = row.createCell((short) p);
	        cell.setCellStyle(style);
	        
	        String data = ardata.get(p).toString();
	        if(data.startsWith("=")){
	         cell.setCellType(Cell.CELL_TYPE_STRING);
	         data=data.replaceAll("\"", "");
	         data=data.replaceAll("=", "");
	         cell.setCellValue(data);
	        }else if(data.startsWith("\"")){
	            data=data.replaceAll("\"", "");
	            cell.setCellType(Cell.CELL_TYPE_STRING);
	            cell.setCellValue(data);
	        }else{
	            data=data.replaceAll("\"", "");
	            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
	            cell.setCellValue(data);
	        }
	        //*/
	     //   cell.setCellValue(ardata.get(p).toString());
	       }
	     //  System.out.println();
	      } 
	    
	     FileOutputStream fileOut = new FileOutputStream(fileName + ".xls");
	     hwb.write(fileOut);
	     fileOut.close();
	     fis.close();
	     System.out.println("Your excel file has been generated: " + fileName + ".xls");
	    } catch ( Exception ex ) 
	    {
	         ex.printStackTrace();
	    } //main method ends
	    File file = new File(fname);
	    file.delete();
		return fileName + ".xls";
    } 
}
