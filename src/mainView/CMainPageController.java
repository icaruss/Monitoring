
package mainView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import settingsView.CSettingsStage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class CMainPageController implements Initializable 
{

 
    @FXML // fx:id="btnSettings"
    private Button btnSettings; // Value injected by FXMLLoader

    @FXML // fx:id="btnVMStat"
    private Button btnVMStat; // Value injected by FXMLLoader
    
    @FXML // fx:id="btnShowVMSTATView"
    private Button btnShowVMSTATView; // Value injected by FXMLLoader
    
    @FXML // fx:id="btnShowMonitoringResults"
    private Button btnShowMonitoringResults; // Value injected by FXMLLoader
    


	public void initialize(URL fxmlFileLocation, ResourceBundle resources)
    {
        assert btnSettings != null : "fx:id=\"btnSettings\" was not injected: check your FXML file 'Main_Page.fxml'.";
        assert btnVMStat != null : "fx:id=\"btnVMStat\" was not injected: check your FXML file 'Main_Page.fxml'.";
        assert btnShowVMSTATView != null : "fx:id=\"btnShowVMSTATView\" was not injected: check your FXML file 'Main_Page.fxml'.";
        assert btnShowMonitoringResults != null : "fx:id=\"btnShowMonitoringResults\" was not injected: check your FXML file 'Main_Page.fxml'.";

        // Initialize your logic here: all @FXML variables will have been injected
        
        btnSettings.setOnAction(new EventHandler<ActionEvent>() 
        {

            @Override
            public void handle(ActionEvent event) 
            {
            	new settingsView.CSettingsStage();
            }
        });
        
        btnVMStat.setOnAction(new EventHandler<ActionEvent>() 
        {

        	@Override
        	public void handle(ActionEvent event)
        	{
        		System.out.println("btnVMStat Event On Occured");
        	}
        });
        
        btnShowVMSTATView.setOnAction(new EventHandler<ActionEvent>() 
        {

        	@Override
        	public void handle(ActionEvent event)
        	{
        		System.out.println("btnShowVMSTATView Event On Occured");
        	}
        });
        


        btnShowMonitoringResults.setOnAction(new EventHandler<ActionEvent>() 
        {

        	@Override
        	public void handle(ActionEvent event)
        	{
        		System.out.println("btnShowMonitoringResults Event On Occured");
        	}
        });
        
    }

}







