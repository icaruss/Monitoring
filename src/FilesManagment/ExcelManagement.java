/*
 * 
 */
package FilesManagment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import log.MonLogger;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

// TODO: Auto-generated Javadoc
// -------------- AIX/LINUX/SOLARIS OUTPUT -----
// column number 1 = time
// column number 4 = %CPU
// column number 5 = %MEM
// column number 6 = %VSZ
// column number 7 = %RSS
//--------------------------------------------- 
//-----------    HPUX OUTPUT  ----------------- 
//column number 1 = time
//column number 4 = %CPU
//column number 5 = VSZ
//--------------------------------------------- 
/**
 * The Class ExcelManagement.
 */
public class ExcelManagement extends FilesManagment {

	/** The file path. */
	String filePath;

	/**
	 * Instantiates a new excel management.
	 * 
	 * @param filePath
	 *            the file path
	 */
	public ExcelManagement(String filePath) {
		super(filePath);
		this.filePath = filePath;

		// TODO Auto-generated constructor stub
	}

	/**
	 * *************************************************************************
	 * ********.
	 * 
	 * @param ExcelFilePath
	 *            the excel file path
	 * @param columnNum
	 *            the column num
	 * @return the diff2 cells
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	/*
	 * AIX Output: FIRSTROW=2 , LASTROW=sheet.getLastRowNum()-4 ; VSZ COL=4 ;
	 * CPU COL=2 ; RSS COL=5 ; %MEM COL=3
	 */
	/************************************************************************************/

	public int[][] getDiff2Cells(String ExcelFilePath, int columnNum)
			throws IOException {

		FileInputStream file = new FileInputStream(new File(ExcelFilePath));

		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet sheet = workbook.getSheetAt(0);
		Row rowFirst = sheet.getRow(1);
		Row rowLast = sheet.getRow(sheet.getLastRowNum());
		if (sheet.getLastRowNum() - 1 < 0) {
			return null;
		}
		int[][] diffArr = new int[sheet.getLastRowNum() - 1][2];

		int notlast = 0;
		int last = 0;

		// HSSFCell cellLast = (HSSFCell) rowLast.getCell(columnNum);

		int j = 0;
		while (rowLast.getRowNum() > rowFirst.getRowNum()) {
			int notLastRowNum = rowLast.getRowNum() - 1;
			Row notLastRow = sheet.getRow(notLastRowNum);
			HSSFCell notLastCell = (HSSFCell) notLastRow.getCell(columnNum);
			HSSFCell cellLast = (HSSFCell) rowLast.getCell(columnNum);
			if (notLastCell.getCellType() == Cell.CELL_TYPE_STRING)
				notlast = new Integer(notLastCell.getStringCellValue()); // get
																			// the
																			// cell
																			// contents
			else if (notLastCell.getCellType() == Cell.CELL_TYPE_NUMERIC)
				notlast = (int) notLastCell.getNumericCellValue();

			if (cellLast.getCellType() == Cell.CELL_TYPE_STRING)
				last = new Integer(cellLast.getStringCellValue());
			else if (cellLast.getCellType() == Cell.CELL_TYPE_NUMERIC)
				last = (int) cellLast.getNumericCellValue();

			diffArr[j][0] = last - notlast; // save diff
			diffArr[j][1] = notLastRowNum; // save the row number of y, when x-y
											// = z
			rowLast = notLastRow; // 68

			j++;
		}

		return diffArr;

	}

	// Check if diff >= parameter

	/**
	 * Find diff greater.
	 * 
	 * @param diffArr
	 *            the diff arr
	 * @param diff
	 *            the diff
	 * @return the int[][]
	 */
	public int[][] findDiffGreater(int[][] diffArr, int diff) {
		int count = 0;
		int[][] newDiffArr = null;
		try {
			for (int i = 0; i < diffArr.length; i++) {
				if (diffArr[i][0] >= diff) {
					count++;
				}
			}
			int j = 0;
			if (count > 0) {
				newDiffArr = new int[count][2];
				for (int i = 0; i < diffArr.length; i++) {
					if (diffArr[i][0] >= diff) {
						newDiffArr[j][0] = diffArr[i][0];
						newDiffArr[j][1] = diffArr[i][1];
						j++;

					}
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			MonLogger.myLogger.log(Level.WARNING, e.getMessage());
			MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
		} catch (NullPointerException e) {
			MonLogger.myLogger.log(Level.WARNING, e.getMessage());
			MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
		}

		return newDiffArr;

	}

	/**
	 * Gets the date string from row.
	 * 
	 * @param rowNum
	 *            the row num
	 * @param fileName
	 *            the file name
	 * @return the date string from row
	 */
	public String getDateStringFromRow(int rowNum, String fileName) {
		HSSFCell cell = null;
		try {
			FileInputStream file = new FileInputStream(new File(fileName));

			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheetAt(0);

			Row row = sheet.getRow(rowNum);
			cell = (HSSFCell) row.getCell(0);
		} catch (FileNotFoundException e) {
			MonLogger.myLogger.log(Level.WARNING, e.getMessage());
			MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
		} catch (IOException e) {
			MonLogger.myLogger.log(Level.WARNING, e.getMessage());
			MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
		}
		return cell.getStringCellValue();
	}

	/**
	 * Gets the last col.
	 * 
	 * @param rowNum
	 *            the row num
	 * @param ExcelFilePath
	 *            the excel file path
	 * @return the last col
	 */
	public int getLastCol(int rowNum, String ExcelFilePath) {
		int i = 0;
		try {
			FileInputStream file = new FileInputStream(new File(ExcelFilePath));

			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheetAt(0);

			Row row = sheet.getRow(rowNum);
			while (row.getCell(i) != null) {
				i++;
			}
		} catch (FileNotFoundException e) {
			MonLogger.myLogger.log(Level.WARNING, e.getMessage());
			MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
		} catch (IOException e) {
			MonLogger.myLogger.log(Level.WARNING, e.getMessage());
			MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
		}

		return i;
	}

	// The parameter "diffArr" should be taken from method "findDiffGreater"
	/**
	 * Sets the diff to file.
	 * 
	 * @param diffArr
	 *            the diff arr
	 * @param ExcelFilePath
	 *            the excel file path
	 */
	public void setDiffToFile(int[][] diffArr, String ExcelFilePath) {
		int lastCol = getLastCol(1, filePath); // the row num could be any row
		for (int i = 0; i < diffArr.length; i++) {
			setDiffToRow(diffArr[i][1], lastCol, diffArr[i][0], ExcelFilePath);

		}
	}

	// maxDiff parameter should be defined by the user
	/*
	 * This method will add anew column with the memory diff values and will
	 * paint in yellow the relevant
	 */
	/**
	 * Main excel flow.
	 * 
	 * @param RSSVSZColNum
	 *            the RSSVSZ col num
	 * @param maxDiff
	 *            the max diff
	 * @param interval
	 *            the interval
	 * @param testFolder
	 *            the test folder
	 */
	public void mainExcelFlow(int RSSVSZColNum, int maxDiff, int interval,
			Folder testFolder)  {
		try {
			if (getDiff2Cells(filePath, RSSVSZColNum) == null) {
				return;
			}
			int[][] fullDiffArr = getDiff2Cells(filePath, RSSVSZColNum);
			int[][] diffArr = findDiffGreater(fullDiffArr, maxDiff);
			String[] dateStrArr = new String[diffArr.length];
			Hashtable<String, List<Integer>> rowNumsforDate = new Hashtable<String, List<Integer>>(); // dateString
																										// +
																										// list
																										// of
																										// row
																										// numbers
																										// in
																										// the
																										// date
																										// interavl,
																										// in
																										// each
																										// cell

			setDiffToFile(diffArr, filePath);

			// dateStrArr is the array of dates with difference above the
			// threshold
			for (int i = 0; i < diffArr.length; i++) {
				setColorToRow(diffArr[i][1], filePath, HSSFColor.YELLOW.index);
				dateStrArr[i] = getDateStringFromRow(diffArr[i][1], filePath);
			}

			// Get clix date array
			FileInputStream file = new FileInputStream(new File(
					testFolder.getFolderPath() + "\\" + "clix_mon.xls"));

			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheetAt(0);
			DateOperations dop = new DateOperations();
			String[] clixDateStrArr = new String[sheet.getLastRowNum()
					- sheet.getFirstRowNum()];
			Date[] clixDateArr = new Date[clixDateStrArr.length];
			for (int i = 0; i < clixDateStrArr.length; i++) {
				clixDateStrArr[i] = getDateStringFromRow(i + 1,
						testFolder.getFolderPath() + "\\" + "clix_mon.xls");
				clixDateArr[i] = dop.convertStringToDate(clixDateStrArr[i]);
			}

			/*
			 * for each date string in the monitoring file, find the row numbers
			 * of clix output in the interval between date <= clixOperatons <=
			 * date + interval, put in map the intreval and the date string as a
			 * key
			 */
			for (int i = 0; i < dateStrArr.length; i++) {
				List<Integer> intList = null; // list of line numbers in the
												// clix file for a specific date
				Date date = dop.convertStringToDate(dateStrArr[i]);
				intList = dop.findAllDatesInInterval(date, interval,
						clixDateArr);
				rowNumsforDate.put(dateStrArr[i], intList);
			}

			Iterator<Map.Entry<String, List<Integer>>> it = rowNumsforDate
					.entrySet().iterator();

			while (it.hasNext()) {
				Entry<String, List<Integer>> entry = it.next();
				List<Integer> intListtmp = entry.getValue();
				for (int i = 0; i < intListtmp.size(); i++) {
					setColorToRow(intListtmp.get(i), testFolder.getFolderPath()
							+ "\\" + "clix_mon.xls",
							HSSFColor.LIGHT_ORANGE.index);
				}

			}

		} 
		catch (FileNotFoundException e) {
			MonLogger.myLogger.log(Level.WARNING, e.getMessage());
			MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
		} 
		catch (IOException e) {
			MonLogger.myLogger.log(Level.WARNING, e.getMessage());
			MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
		}

	}

	// Set cell value
	/**
	 * Sets the diff to row.
	 * 
	 * @param rowNum
	 *            the row num
	 * @param lastCol
	 *            the last col
	 * @param diff
	 *            the diff
	 * @param ExcelFilePath
	 *            the excel file path
	 */
	public void setDiffToRow(int rowNum, int lastCol, int diff,
			String ExcelFilePath) {
		try {
			FileInputStream file = new FileInputStream(new File(ExcelFilePath));

			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheetAt(0);

			Row row = sheet.getRow(rowNum);
			row.createCell(lastCol).setCellValue(diff);
			FileOutputStream out = new FileOutputStream(ExcelFilePath);
			workbook.write(out);
			out.close();

		} catch (FileNotFoundException e) {
			MonLogger.myLogger.log(Level.WARNING, e.getMessage());
			MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
		} catch (IOException e) {
			MonLogger.myLogger.log(Level.WARNING, e.getMessage());
			MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
		}
	}

	// HSSFColor.YELLOW.index = short object
	/**
	 * Sets the color to row.
	 * 
	 * @param rowNum
	 *            the row num
	 * @param ExcelFilePath
	 *            the excel file path
	 * @param color
	 *            the color
	 */
	public void setColorToRow(int rowNum, String ExcelFilePath, short color) {
		try {

			FileInputStream file = new FileInputStream(new File(ExcelFilePath));

			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheetAt(0);

			Row row = sheet.getRow(rowNum);

			HSSFCellStyle style = workbook.createCellStyle();
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(color);
			// style.setWrapText(true);

			int i = 0;
			HSSFCell cell;
			while (row.getCell(i) != null) {
				cell = (HSSFCell) row.getCell(i);
				cell.setCellStyle(style);
				i++;
			}

			FileOutputStream out = new FileOutputStream(ExcelFilePath);
			workbook.write(out);
			out.close();

		} catch (FileNotFoundException e) {
			MonLogger.myLogger.log(Level.WARNING, e.getMessage());
			MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
		} catch (IOException e) {
			MonLogger.myLogger.log(Level.WARNING, e.getMessage());
			MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
		}
	}

	// Returns array of VSZ and/or RSS
	/**
	 * Gets the memory.
	 * 
	 * @param VSZ_OR_RSS
	 *            the vsz or rss
	 * @param ExcelFilePath
	 *            the excel file path
	 * @param OS
	 *            the os
	 * @return the memory
	 */
	public Double[] getMemory(String VSZ_OR_RSS, String ExcelFilePath, String OS) {
		FileInputStream file = null;
		try {
			file = new FileInputStream(new File(ExcelFilePath));
		} catch (FileNotFoundException e) {
			MonLogger.myLogger.log(Level.WARNING, e.getMessage());
			MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
		}
		HSSFWorkbook workbook = null;
		try {
			workbook = new HSSFWorkbook(file);
		} catch (IOException e) {
			MonLogger.myLogger.log(Level.WARNING, e.getMessage());
			MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
		}
		HSSFSheet sheet = workbook.getSheetAt(0);
		int firstIndex = 1;
		int lastIndex = sheet.getLastRowNum();
		Row rowIndex;
		Double[] doubleArr = new Double[lastIndex - firstIndex + 1];

		if (OS.contains("HP-UX")) // Only VSZ in Column number 4
		{
			for (int i = 0; i < doubleArr.length; i++) {
				rowIndex = sheet.getRow(firstIndex);
				HSSFCell cell = (HSSFCell) rowIndex.getCell(4);
				doubleArr[i] = Double.parseDouble(cell.toString());
			}

		}

		// NOT HPUX
		else
		// column number 6 = %VSZ
		// column number 7 = %RSS
		{
			if (VSZ_OR_RSS == "VSZ") {
				for (int i = 0; i < doubleArr.length; i++) {
					rowIndex = sheet.getRow(firstIndex);
					HSSFCell cell = (HSSFCell) rowIndex.getCell(5);
					doubleArr[i] = Double.parseDouble(cell.toString());
				}

			} else // (VSZ_OR_RSS equals to "RSS")
			{
				for (int i = 0; i < doubleArr.length; i++) {
					rowIndex = sheet.getRow(firstIndex);
					HSSFCell cell = (HSSFCell) rowIndex.getCell(5);
					doubleArr[i] = Double.parseDouble(cell.toString());
				}
			}

		}

		return doubleArr;
	}

	// Get maximal memory pick
	/**
	 * Max value.
	 * 
	 * @param array
	 *            the array
	 * @return the double
	 */
	public Double MaxValue(Double[] array) {
		return Collections.max(Arrays.asList(array));

	}

	// Get minimal memory pick
	/**
	 * Min value.
	 * 
	 * @param array
	 *            the array
	 * @return the double
	 */
	public Double MinValue(Double[] array) {
		return Collections.min(Arrays.asList(array));

	}

	/**
	 * Gets the time of max value.
	 * 
	 * @return the time of max value
	 */
	public String getTimeOfMaxValue() {

		return null;

	}

	/**
	 * Gets the cpu.
	 * 
	 * @param ExcelFilePath
	 *            the excel file path
	 * @param OS
	 *            the os
	 * @return the cpu
	 */
	public Double[] getCPU(String ExcelFilePath, String OS) {

		FileInputStream file = null;
		try {
			file = new FileInputStream(new File(ExcelFilePath));
		} catch (FileNotFoundException e) {
			MonLogger.myLogger.log(Level.WARNING, e.getMessage());
			MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
		}
		HSSFWorkbook workbook = null;
		try {
			workbook = new HSSFWorkbook(file);
		} catch (IOException e) {
			MonLogger.myLogger.log(Level.WARNING, e.getMessage());
			MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
		}
		HSSFSheet sheet = workbook.getSheetAt(0);
		int firstIndex = 1;
		int lastIndex = sheet.getLastRowNum();
		// Row rowFirst = sheet.getRow(2);
		// Row rowLast = sheet.getRow(lastIndex);

		Row rowIndex;
		Double[] doubleArr = new Double[lastIndex - firstIndex + 1];

		for (int i = 0; i < doubleArr.length; i++) {
			rowIndex = sheet.getRow(i + 1);
			HSSFCell cell = (HSSFCell) rowIndex.getCell(3);
			doubleArr[i] = Double.parseDouble(cell.toString());
		}

		return doubleArr;
	}

	// Set cell value
	/**
	 * Adds the header.
	 * 
	 * @param lastCol
	 *            the last col
	 * @param VSZRSS
	 *            the vszrss
	 * @param ExcelFilePath
	 *            the excel file path
	 */
	public void addHeader(int lastCol, String VSZRSS, String ExcelFilePath) {
		try {
			FileInputStream file = new FileInputStream(new File(ExcelFilePath));

			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheetAt(0);

			Row row = sheet.getRow(0);
			row.createCell(lastCol).setCellValue(VSZRSS);
			FileOutputStream out = new FileOutputStream(ExcelFilePath);
			workbook.write(out);
			out.close();

		} catch (FileNotFoundException e) {
			MonLogger.myLogger.log(Level.WARNING, e.getMessage());
			MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
		} catch (IOException e) {
			MonLogger.myLogger.log(Level.WARNING, e.getMessage());
			MonLogger.myLogger.log(Level.WARNING, e.getStackTrace().toString());
			e.printStackTrace();
		}
	}
}
