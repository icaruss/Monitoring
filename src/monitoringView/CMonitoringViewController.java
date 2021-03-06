/*
 * 
 */

package monitoringView;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import viewLogic.CSharedInstance;
import viewLogic.CViewConstants.DirectoryDirection;
import viewLogic.CViewConstants.FileType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.SplitPane.Divider;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.ResizeFeatures;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;


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
	private TableView<Map> tbView; // Value injected by FXMLLoader

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

	/** The Img box. */
	@FXML
	// fx:id="imgBox"
	private ImageView ImgBox; // Value injected by FXMLLoader
	
	/** The splt pane. */
	public SplitPane spltPane;
	
	/** The lbl tbl. */
	@FXML
	// fx:id="lblTbl"
	private Label lblTbl;
	

	// ////// logic Variables //////
	
	/** The vector of images. */
	private Vector<Image> vectorOfImages;
	
	/** The current index of images. */
	private int currentIndexOfImages;

	/** The current index of tables. */
	private int currentIndexOfTables;
	
	/** The vector of excel files. */
	private Vector<File> vectorOfExcelFiles;
	

	// ////// end logic Variables ///

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@SuppressWarnings("rawtypes")
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
		assert lblTbl != null : "fx:id=\"lblTbl\" was not injected: check your FXML file 'Monitoring_Page.fxml'.";
		
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
		
		// Table view Resize policy
		tbView.setColumnResizePolicy(new Callback<TableView.ResizeFeatures, Boolean>() 
		{
			  @Override
			  public Boolean call(ResizeFeatures p) 
			  {
			     return true;
			  }

			
			});
		

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
	 * Clearing view and
	 * Load next applicable data to view.
	 */
	private void loadDataFileToViewVariables() 
	{
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
		
		if (vectorOfImages.size() > 0)
			setImageToViewByIndex();

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

	/**
	 * Clear view and logic.
	 */
	private void ClearViewAndLogic()
	{
		// Logic
		if (vectorOfExcelFiles != null)
			vectorOfExcelFiles.clear();

		if (vectorOfImages != null)
			vectorOfImages.clear();
		
		tbView.getColumns().clear();
		tbView.getItems().clear();
		
		ImgBox.setImage(null);
		
	}

	/**
	 * Sets the image to view by index.
	 */
	private void setImageToViewByIndex() 
	{
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

	/**
	 * Sets the excel file to view by index.
	 */
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

	/**
	 * Sets the visibility on components.
	 * 
	 * @param isVisible
	 *            the new visibility on components
	 */
	private void setVisibilityOnComponents(boolean isVisible)
	{
		btnNextImg.setVisible(isVisible);
		btnNextResult.setVisible(isVisible);
		btnNextTable.setVisible(isVisible);
		btnPrevImg.setVisible(isVisible);
		btnPreviousResult.setVisible(isVisible);
		btnPrevTable.setVisible(isVisible);
		btnShowInFile.setVisible(isVisible);
	}
	
	/**
	 * Load table data from given file.
	 * suppresssing rawtype of TableView
	 * 
	 * @param file
	 *            the file
	 */
	@SuppressWarnings({ "rawtypes" })
	public void loadTableData(File file)
	{
		tbView.getItems().clear();
		
		ObservableList<Map> allData = FXCollections.observableArrayList();
		
		Vector<String> columnHeaders = new Vector<String>();
		
		final Vector<Integer> indeces = new Vector<Integer>();
		
		// retrieving all columns from current file
		try
		{
			FileInputStream fileInput = new FileInputStream(file);

			HSSFWorkbook workbook = new HSSFWorkbook(fileInput);
			HSSFSheet sheet = workbook.getSheetAt(0);
			
			for (int i = sheet.getFirstRowNum() ; i <= sheet.getLastRowNum() ; ++i)
			{
				HSSFRow row_ = sheet.getRow(i);
				
				Map<String, String> dataRow = new HashMap<String, String>();

				for (int j = row_.getFirstCellNum() ; j < row_.getLastCellNum() ; ++j)
				{
					HSSFCell cell_ = row_.getCell(j);
					
					if (i == 0) // Adding all column headers
					{
						if (cell_.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
	            		{
							columnHeaders.add(String.valueOf(cell_.getColumnIndex()));
	            		}
	            		else if (cell_.getCellType() == HSSFCell.CELL_TYPE_STRING)
	            		{
	            			columnHeaders.add(cell_.getStringCellValue());
	            		}
	            		else
	            		{
	            			columnHeaders.add("N/A");
	            		}
					}
					else // adding the rest of the data
	            	{
	            		if (cell_.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
	            		{
	            			dataRow.put(columnHeaders.elementAt(j), String.valueOf(cell_.getNumericCellValue()));
	            		}
	            		else if (cell_.getCellType() == HSSFCell.CELL_TYPE_STRING)
	            		{
	            			dataRow.put(columnHeaders.elementAt(j), String.valueOf(cell_.getStringCellValue()));
	            		}
	            		else
	            		{
	            			dataRow.put(columnHeaders.elementAt(j),"N/A");
	            		}
	            		
	            	}
					
				}
				
				int indexFirstCell = row_.getFirstCellNum();
				
				String hexOfColor = CMonitoringViewController.extractBackgroundColor(row_.getCell(indexFirstCell), workbook);
				
				if (!hexOfColor.equalsIgnoreCase("000000")) // uncolored row
				{
					int index = (i != 0) ? i - 1 : i;
					
					indeces.add(index);
				}
				
				
				if (i != 0)
					allData.add(dataRow);
			}
			
			fileInput.close();
			
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		this.tbView.getColumns().clear();
		
		this.tbView.setItems(allData);
		
		// Cell Callback
		Callback<TableColumn<Map, String>, TableCell<Map, String>>  cellFactoryForMap = new Callback<TableColumn<Map, String>, TableCell<Map, String>>() 
		{
                @Override
                public TableCell call(TableColumn param)
                {
                    return new TableCell<Map, String>()
                    {
                        
                        @Override
                        public void updateItem(String item, boolean empty)
                        {
                            super.updateItem(item, empty);
                            
                            if(!isEmpty() && !item.isEmpty() && !item.equals(""))
                            {
                                setText(item);
                                
                                TableRow currentRow = getTableRow();
                                if(currentRow != null)
                                { 
                                	int rowIndex = currentRow.getIndex();
                                	
                                	if (indeces.contains(rowIndex))
                                	{
                                    	this.setTextFill(javafx.scene.paint.Color.RED);
                                	}
                                	else
                                	{
                                    	this.setTextFill(javafx.scene.paint.Color.DARKBLUE);
                                	}
                                }
                                
                            }
                            else
                            {
                            	setText("");
                            }
                        }
                    };
                }
                
        };
		
		
		tbView.setEditable(false);
		tbView.getSelectionModel().setCellSelectionEnabled(false);
		
		for (String str : columnHeaders)
		{
			TableColumn<Map, String> column = new TableColumn<Map, String>(str);
			column.setCellValueFactory(new MapValueFactory<String>(str));
			
			column.setCellFactory(cellFactoryForMap);
			
			int length = str.length();
			
			column.setMaxWidth(Math.max(length*15, 300));
			column.setMinWidth(Math.min(length * 10, 120));
			
			this.tbView.getColumns().add(column);
		}
		
		this.tbView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		
		// data added to table - label change
		this.lblTbl.setText("Current Table : " + file.getName());
		
		columnHeaders.clear();
		
		
	}
	

	/**
	 * Extract background color.
	 * 
	 * @param p_cell
	 *            the p_cell
	 * @param Workbook
	 *            the workbook
	 * @return the string
	 */
	public static String extractBackgroundColor(HSSFCell p_cell, HSSFWorkbook Workbook) 
	{
        // get custom pallet of colors from excel file
       HSSFPalette hsfPallet = Workbook.getCustomPalette();
        // get key of that custom pallet        
        short key =  p_cell.getCellStyle().getFillForegroundColor(); 
      // creating object HSSFColor  from key
        HSSFColor color = hsfPallet.getColor(key);
      // getting rgb values
        short[] rgb = color.getTriplet();
      // creating java.awt.Color object, you need that object for method Integer.toHexString()
        Color c = new Color(rgb[0], rgb[1], rgb[2]);
        String hex = Integer.toHexString(c.getRGB());
        
        return  hex.substring(2,hex.length());
    }
	

}
