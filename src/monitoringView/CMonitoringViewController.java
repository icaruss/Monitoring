

package monitoringView;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Vector;

import unix.ExecuteUnixOperations;
import viewLogic.CSharedInstance;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import viewLogic.CViewConstants;


public class CMonitoringViewController implements Initializable
{

	@FXML // fx:id="lblResultID"
    private Label lblResultID; // Value injected by FXMLLoader
	
	@FXML // fx:id="lineChart"
    private LineChart lineChart; // Value injected by FXMLLoader
    
	@FXML // fx:id="tbView"
    private TableView tbView; // Value injected by FXMLLoader
	
	@FXML // fx:id="btnPreviousResult"
    private Button btnPreviousResult; // Value injected by FXMLLoader
	
	@FXML // fx:id="btnNextResult"
    private Button btnNextResult; // Value injected by FXMLLoader
	
	@FXML // fx:id="btnShowInFile"
    private Button btnShowInFile; // Value injected by FXMLLoader
	
    
    ////////  logic Variables //////
    
    private Map<String, String[]> files;

    
    
    //////// end logic Variables ///
    
   
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



	private void loadDataFileToView()
	{
		// Load Chart/s
		
		
		
		// Load Table

	}
    
    
}
