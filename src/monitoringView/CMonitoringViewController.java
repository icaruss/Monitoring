/*
 * 
 */

package monitoringView;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.ptg.TblPtg;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;

import viewLogic.CSharedInstance;
import viewLogic.CViewConstants.DirectoryDirection;
import viewLogic.CViewConstants.FileType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.SplitPane.Divider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


// TODO: Auto-generated Javadoc
/**
 * The Class CMonitoringViewController.
 */
public class CMonitoringViewController implements Initializable {

	/** The lbl result id. */
	@FXML
	// fx:id="lblResultID"
	private Label lblResultID; // Value injected by FXMLLoader

	/** The tb view. */
	@SuppressWarnings("rawtypes")
	@FXML
	// fx:id="tbView"
	private TableView tbView; // Value injected by FXMLLoader

	/** The btn previous result. */
	@FXML
	// fx:id="btnPreviousResult"
	private Button btnPreviousResult; // Value injected by FXMLLoader

	/** The btn next result. */
	@FXML
	// fx:id="btnNextResult"
	private Button btnNextResult; // Value injected by FXMLLoader

	/** The btn show in file. */
	@FXML
	// fx:id="btnShowInFile"
	private Button btnShowInFile; // Value injected by FXMLLoader

	/** The btn next img. */
	@FXML
	// fx:id="btnShowInFile"
	private Button btnNextImg; // Value injected by FXMLLoader

	/** The btn prev img. */
	@FXML
	// fx:id="btnShowInFile"
	private Button btnPrevImg; // Value injected by FXMLLoader

	/** The btn next table. */
	@FXML
	// fx:id="btnShowInFile"
	private Button btnNextTable; // Value injected by FXMLLoader

	/** The btn prev table. */
	@FXML
	// fx:id="btnShowInFile"
	private Button btnPrevTable; // Value injected by FXMLLoader

	@FXML
	// fx:id="imgBox"
	private ImageView ImgBox; // Value injected by FXMLLoader
	
	public SplitPane spltPane;
	

	// ////// logic Variables //////
	Vector<Image> vectorOfImages;
	private int currentIndexOfImages;

	private int currentIndexOfTables;
	Vector<File> vectorOfExcelFiles;

	// ////// end logic Variables ///

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		assert lblResultID != null : "fx:id=\"lblResultID\" was not injected: check your FXML file 'Monitoring_Page.fxml'.";
		assert tbView != null : "fx:id=\"tbView\" was not injected: check your FXML file 'Monitoring_Page.fxml'.";
		assert btnPreviousResult != null : "fx:id=\"btnPreviousResult\" was not injected: check your FXML file 'Monitoring_Page.fxml'.";
		assert btnNextResult != null : "fx:id=\"btnNextResult\" was not injected: check your FXML file 'Monitoring_Page.fxml'.";
		assert btnShowInFile != null : "fx:id=\"btnShowInFile\" was not injected: check your FXML file 'Monitoring_Page.fxml'.";
		assert btnNextImg != null : "fx:id=\"btnNextImg\" was not injected: check your FXML file 'Monitoring_Page.fxml'.";
		assert btnPrevImg != null : "fx:id=\"btnPrevImg\" was not injected: check your FXML file 'Monitoring_Page.fxml'.";
		assert btnNextTable != null : "fx:id=\"btnNextTable\" was not injected: check your FXML file 'Monitoring_Page.fxml'.";
		assert btnPrevTable != null : "fx:id=\"btnPrevTable\" was not injected: check your FXML file 'Monitoring_Page.fxml'.";
		assert ImgBox != null : "fx:id=\"imgBox\" was not injected: check your FXML file 'Monitoring_Page.fxml'.";
		// Initialize your logic here: all @FXML variables will have been
		// injected
		

		CSharedInstance sharedInstance = CSharedInstance.getInstance();
		boolean isTestsExists = true;

		currentIndexOfImages = 0;
		currentIndexOfTables = 0;

		if (sharedInstance.currentDataFilesID != null)
		{ 
			String filename = sharedInstance.currentDataFilesID.substring(sharedInstance.currentDataFilesID.lastIndexOf("\\")+1);  

			lblResultID.setText("Current : " + filename);
		} 
		else 
		{
			Set<String> setOfKeys = sharedInstance.getAllDataFilesKeys();
			if (setOfKeys != null && !setOfKeys.isEmpty()) 
			{
				String str = (String)setOfKeys.toArray()[0];
				String filename = str.substring(str.lastIndexOf("\\")+1); 
				
				lblResultID.setText("Current : " + filename);

				sharedInstance.currentDataFilesID = (String) (setOfKeys
						.toArray()[0]);

			} else {
				lblResultID.setText("There are NO Monitoring Tests Available");

				isTestsExists = false;
			}

		}

		if (isTestsExists) {
			loadDataFileToViewVariables();
		}

		setVisibilityOnComponents(isTestsExists);
		

		// opens all files associated with that current Run
		btnShowInFile.setOnAction(new EventHandler<ActionEvent>() 
		{

			@Override
			public void handle(ActionEvent event) 
			{

				Vector<String> vecOfDataFiles = CSharedInstance.getInstance()
						.getCurrentDataFilesSet();

				if (vecOfDataFiles != null && !vecOfDataFiles.isEmpty())
				{
					for (String fileName : vecOfDataFiles) 
					{
						File f = new File(fileName);
						if (f.exists() && !f.isDirectory()) 
						{
							try 
							{
								if (Desktop.isDesktopSupported())
								{
									Desktop.getDesktop().open(f);
								}
							} 
							catch (IOException e) 
							{
								e.printStackTrace();
							}
						}
					}

				}

			}

		});

		// Show Next Result, if none exists , show the first/Last received
		btnNextResult.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				CSharedInstance sharedInstance = CSharedInstance.getInstance();
				sharedInstance
						.nextORPreviousDirectoryInDataFilesMap(DirectoryDirection.DirectoryDirectionNext);
				
				String filename = sharedInstance.currentDataFilesID.substring(sharedInstance.currentDataFilesID.lastIndexOf("\\")+1);  

				lblResultID.setText("Current : " + filename);
				
				loadDataFileToViewVariables();

				setImageToViewByIndex();
				setExcelFileToViewByIndex();
			}

		});

		// Show Previous Result, if none exists , show the first/Last received
		btnPreviousResult.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				CSharedInstance sharedInstance = CSharedInstance.getInstance();
				sharedInstance
						.nextORPreviousDirectoryInDataFilesMap(DirectoryDirection.DirectoryDirectionPrevious);
				
				String filename = sharedInstance.currentDataFilesID.substring(sharedInstance.currentDataFilesID.lastIndexOf("\\")+1);  

				lblResultID.setText("Current : " + filename);
				
				loadDataFileToViewVariables();

				setImageToViewByIndex();
				setExcelFileToViewByIndex();
			}

		});

		// Show Next Image in current test directory, if none exists -shows
		// nothing
		btnNextImg.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				currentIndexOfImages++;
				setImageToViewByIndex();
			}

		});

		// Show Previous Image in current test directory, if none exists -shows
		// nothing
		btnPrevImg.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				currentIndexOfImages--;
				setImageToViewByIndex();
			}

		});

		// Show Next Table in current test directory, if none exists -shows
		// nothing
		btnNextTable.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				currentIndexOfTables++;
				setExcelFileToViewByIndex();
			}

		});

		// Show Previous Table in current test directory, if none exists -shows
		// nothing
		btnPrevTable.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				currentIndexOfTables--;
				setExcelFileToViewByIndex();
			}

		});

	}

	/**
	 * Load data file to view.
	 */
	private void loadDataFileToViewVariables() {
		ClearViewAndLogic();

		CSharedInstance sharedInstance = CSharedInstance.getInstance();

		// Load images To vector
		Vector<String> vecOfFileNamesImages = sharedInstance
				.getAllFilesInMapWithCurrentFileTypeEndingInDirectory(
						FileType.FileTypeImg, sharedInstance.currentDataFilesID);

		if (vecOfFileNamesImages != null) {
			vectorOfImages = new Vector<Image>();
		}
		for (String str : vecOfFileNamesImages) 
		{
			File file = new File(str);
			Image img = new Image(file.toURI().toString());
			vectorOfImages.add(img);
		}

		if (vectorOfImages.size() > 0) {
			setImageToViewByIndex();
		}

		// Load Files to vector
		Vector<String> vecOfExcelFileNames = sharedInstance
				.getAllFilesInMapWithCurrentFileTypeEndingInDirectory(
						FileType.FileTypeExcel,
						sharedInstance.currentDataFilesID);

		if (vecOfExcelFileNames != null) {
			vectorOfExcelFiles = new Vector<File>();
		}
		for (String str : vecOfExcelFileNames) {
			File file = new File(str);
			vectorOfExcelFiles.add(file);
		}

		if (vectorOfExcelFiles.size() > 0) {
			setExcelFileToViewByIndex();
		}

	}

	private void ClearViewAndLogic() {
		// Logic
		if (vectorOfExcelFiles != null)
			vectorOfExcelFiles.clear();

		if (vectorOfImages != null)
			vectorOfImages.clear();
	}

	private void setImageToViewByIndex() {
		if (vectorOfImages.size() == 0)
		{
			currentIndexOfImages = 0;
			return;
		}

		if (currentIndexOfImages >= vectorOfImages.size()) {
			currentIndexOfImages %= vectorOfImages.size();
		} else if (currentIndexOfImages < 0) {
			currentIndexOfImages += vectorOfImages.size();
		}

		this.ImgBox.setImage(vectorOfImages.elementAt(currentIndexOfImages));

	}

	private void setExcelFileToViewByIndex() 
	{
		if (vectorOfExcelFiles.size() == 0)
			return;

		if (currentIndexOfTables >= vectorOfExcelFiles.size()) {
			currentIndexOfTables %= vectorOfExcelFiles.size();
		} else if (currentIndexOfTables < 0) {
			currentIndexOfTables += vectorOfExcelFiles.size();
		}
		
		loadTableData(vectorOfExcelFiles.elementAt(currentIndexOfTables));

	}

	private void setVisibilityOnComponents(boolean isVisible) {
		btnNextImg.setVisible(isVisible);
		btnNextResult.setVisible(isVisible);
		btnNextTable.setVisible(isVisible);
		btnPrevImg.setVisible(isVisible);
		btnPreviousResult.setVisible(isVisible);
		btnPrevTable.setVisible(isVisible);
		btnShowInFile.setVisible(isVisible);
	}
	
	public void splitPaneOnMouse()
	{
		Divider divider = spltPane.getDividers().get(0);
		
		System.out.println(divider.getPosition());
	}
	
	
	@SuppressWarnings("unchecked")
	public void loadTableData(File file)
	{
		
		Map <Integer,Vector<String>> map = new HashMap<Integer, Vector<String>>();
		
		
		// retrieving all columns from current file
		try
		{
			FileInputStream fileInput = new FileInputStream(file);

			HSSFWorkbook workbook = new HSSFWorkbook(fileInput);
			HSSFSheet sheet = workbook.getSheetAt(0);
			
			for (int i = sheet.getFirstRowNum() ; i <= sheet.getLastRowNum() ; ++i)
			{
				HSSFRow row_ = sheet.getRow(i);

				for (int j = row_.getFirstCellNum() ; j < row_.getLastCellNum() ; ++j)
				{
					HSSFCell cell_ = row_.getCell(j);
					
					if (i == 0)
					{
						Vector<String> vec = new Vector<String>();
	            		
	            		vec.add(cell_.getStringCellValue());
	            		
	            		map.put(j, vec);
					}
					else
	            	{
	            		Vector<String> vec = map.get(j);
	            		vec.add(cell_.getStringCellValue());
	            	}
					
				}
			}
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	
		
		// STILL IN PROGRESS
		
		/*// Updating table view with columns
		for (Integer i : map.keySet())
		{
			Vector<String> vec = map.get(i);
			
			TableColumn tblColumn = new TableColumn();
			tblColumn.
			
			//tbView.getColumns().add(new Table);
		}*/
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
