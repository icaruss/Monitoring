/*
 * 
 */


package VMStatView;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;
import viewLogic.CSharedInstance;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;


// TODO: Auto-generated Javadoc
/**
 * The Class CVMSTATViewController.
 */
public class CVMSTATViewController implements Initializable
{

	/** The lbl result id. */
	@FXML // fx:id="lblResultID"
    private Label lblResultID; // Value injected by FXMLLoader
	
	/** The line chart. */
	@FXML // fx:id="lineChart"
    private LineChart lineChart; // Value injected by FXMLLoader
    
	/** The tb view. */
	@FXML // fx:id="tbView"
    private TableView tbView; // Value injected by FXMLLoader
	
	/** The btn previous result. */
	@FXML // fx:id="btnPreviousResult"
    private Button btnPreviousResult; // Value injected by FXMLLoader
	
	/** The btn next result. */
	@FXML // fx:id="btnNextResult"
    private Button btnNextResult; // Value injected by FXMLLoader
	
	/** The btn show in file. */
	@FXML // fx:id="btnShowInFile"
    private Button btnShowInFile; // Value injected by FXMLLoader
	
    
    ////////  logic Variables //////
    
    /** The files. */
    private Map<String, String[]> files;

    
    
    //////// end logic Variables ///
    
   
    /* (non-Javadoc)
     * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
     */
    public void initialize(URL fxmlFileLocation, ResourceBundle resources)
    {
    	assert lblResultID != null : "fx:id=\"lblResultID\" was not injected: check your FXML file 'Monitoring_Page.fxml'.";
        assert lineChart != null : "fx:id=\"lineChart\" was not injected: check your FXML file 'Monitoring_Page.fxml'.";
        assert tbView != null : "fx:id=\"tbView\" was not injected: check your FXML file 'Monitoring_Page.fxml'.";
        assert btnPreviousResult != null : "fx:id=\"btnPreviousResult\" was not injected: check your FXML file 'Monitoring_Page.fxml'.";
        assert btnNextResult != null : "fx:id=\"btnNextResult\" was not injected: check your FXML file 'Monitoring_Page.fxml'.";
        assert btnShowInFile != null : "fx:id=\"btnShowInFile\" was not injected: check your FXML file 'Monitoring_Page.fxml'.";
        
        
        // Initialize your logic here: all @FXML variables will have been injected       
        if (CSharedInstance.getInstance().currentDataFilesID != null)
        {
        	lblResultID.setText("Current : " + CSharedInstance.getInstance().currentDataFilesID);
        }
        
        loadDataFileToView();
        
        
        // opens all files associated with that current Run
        btnShowInFile.setOnAction(new EventHandler<ActionEvent>() 
        {

        	@Override
        	public void handle(ActionEvent event)
        	{
        		
        		Vector<String> vecOfDataFiles = CSharedInstance.getInstance().getCurrentDataFilesSet();
        		
        		if (vecOfDataFiles != null && !vecOfDataFiles.isEmpty())
        		{
        			for (String fileName : vecOfDataFiles)
        			{
	        			File f = new File(fileName);
	        			if(f.exists() && !f.isDirectory())
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
        btnNextResult.setOnAction(new EventHandler<ActionEvent>() 
        {

        	@Override
        	public void handle(ActionEvent event)
        	{
        		
        	}

        });
        
        // Show Previous Result, if none exists , show the first/Last received
        btnPreviousResult.setOnAction(new EventHandler<ActionEvent>() 
        {

        	@Override
        	public void handle(ActionEvent event)
        	{
        		
        	}

        });
        
    }



	/**
	 * Load data file to view.
	 */
	private void loadDataFileToView()
	{
		// Load Chart/s
		
		
		
		// Load Table

	}
    
    
}
