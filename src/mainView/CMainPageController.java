/*
 * 
 */

package mainView;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import unix.ExecuteUnixOperations;
import viewLogic.CSharedInstance;
import viewLogic.CViewConstants.MonitorType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tooltip;


// TODO: Auto-generated Javadoc
/**
 * The Class CMainPageController.
 */
public class CMainPageController implements Initializable 
{

 
    /** The btn settings. */
    @FXML // fx:id="btnSettings"
    private Button btnSettings; // Value injected by FXMLLoader

    /** The btn stop monitoring. */
    @FXML // fx:id="btnVMStat"
    private Button btnStopMonitoring; // Value injected by FXMLLoader
    
    /** The btn show vmstat view. */
    @FXML // fx:id="btnShowVMSTATView"
    private Button btnShowVMSTATView; // Value injected by FXMLLoader
    
    /** The btn show monitoring results. */
    @FXML // fx:id="btnShowMonitoringResults"
    private Button btnShowMonitoringResults; // Value injected by FXMLLoader
    
    /** The p bar. */
    public ProgressBar pBar;
    
    /** The p bar percentage. */
    public ProgressIndicator pBarPercentage;
    
    /** The tool tip pbar. */
    public Tooltip toolTipPbar;
    


	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	public void initialize(URL fxmlFileLocation, ResourceBundle resources)
    {
        assert btnSettings != null : "fx:id=\"btnSettings\" was not injected: check your FXML file 'Main_Page.fxml'.";
        assert btnStopMonitoring != null : "fx:id=\"btnStopMonitoring\" was not injected: check your FXML file 'Main_Page.fxml'.";
        assert btnShowVMSTATView != null : "fx:id=\"btnShowVMSTATView\" was not injected: check your FXML file 'Main_Page.fxml'.";
        assert btnShowMonitoringResults != null : "fx:id=\"btnShowMonitoringResults\" was not injected: check your FXML file 'Main_Page.fxml'.";
        assert pBar != null : "fx:id=\"pBar\" was not injected: check your FXML file 'Main_Page.fxml'.";
        assert pBarPercentage != null : "fx:id=\"pBarPercentage\" was not injected: check your FXML file 'Main_Page.fxml'.";
        assert toolTipPbar != null : "fx:id=\"toolTipPbar\" was not injected: check your FXML file 'Main_Page.fxml'.";

        // Initialize your logic here: all @FXML variables will have been injected
        
        btnSettings.setOnAction(new EventHandler<ActionEvent>() 
        {

            @Override
            public void handle(ActionEvent event) 
            {
            	new settingsView.CSettingsStage();
            }
        });
        
        btnStopMonitoring.setOnAction(new EventHandler<ActionEvent>() 
        {

        	@Override
        	public void handle(ActionEvent event)
        	{
        		System.out.println("btnStopMonitoring Event On Occured");
        		
        		StopMonitoring(CSharedInstance.getInstance().currentMonitoring);
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
        
        
        
        final Timer timer = new Timer();

        timer.schedule(new TimerTask() 
        {
            public void run()
            {
                 Platform.runLater(new Runnable() 
                 {
                    public void run() 
                    {
                    	if (CSharedInstance.getInstance().isReadyToLaunch())
                    	{
                    		setButtonsDisableState(true);
                    		 
                    		int cnt = 0;
                    		
	                    	if (CSharedInstance.getInstance().totalSecondsCountdown > 0)
	                    	{
	                    		double progress = (double)(1.0 / (double)CSharedInstance.getInstance().totalSecondsCountdown);
	                    		
	                    		pBar.setProgress(progress + pBar.getProgress());
	                    		pBarPercentage.setProgress(pBar.getProgress());
	                    		
	                    		CSharedInstance.getInstance().secondsElapsed++;
	                    		
	                    		toolTipPbar.setText(CSharedInstance.getInstance().timeLeft());
	                    	}
	                    	
	                    	if (CSharedInstance.getInstance().secondsElapsed == 0 && CSharedInstance.getInstance().totalSecondsCountdown != -1)
	                    	{
	                    		timer.cancel();
	                    		
	                    		StopMonitoring(CSharedInstance.getInstance().currentMonitoring);
	                    	}
	                    	
	                    	else if (CSharedInstance.getInstance().totalSecondsCountdown == -1)
	                    	{
	                    		cnt++;
	                    		cnt %= 3;
	                    		
	                    		switch (cnt)
	                    		{
	                    			case 0 :
	                    			{
	                    				toolTipPbar.setText("Running Without Time Frame.");
	                    				break;
	                    			}
	                    			case 1 :
	                    			{
	                    				toolTipPbar.setText("Running Without Time Frame..");
	                    				break;
	                    			}
	                    			case 2 :
	                    			{
	                    				toolTipPbar.setText("Running Without Time Frame...");
	                    				break;
	                    			}
	                    		}
	                    	}
	                    	
                    	}
                    }
                });
            }
        }, 0, 1000);
        
    }
	
	
	/**
	 * Stop monitoring.
	 * 
	 * @param monitorType
	 *            the monitor type
	 */
	public void StopMonitoring(MonitorType monitorType)
	{

		if (CSharedInstance.getInstance().executeUnixOperations != null)
		{
			toolTipPbar.setText("Monitoring Finished");
			
			btnSettings.setDisable(false);
			
			btnStopMonitoring.setDisable(true);
			
			CSharedInstance.getInstance().isMonitoringStarted = false;
			CSharedInstance.getInstance().isMonitoringDone = true;
			
			ExecuteUnixOperations exUnixOp = CSharedInstance.getInstance().executeUnixOperations;
			
			try 
			{
				exUnixOp.finish();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			} 
			catch (ParseException e)
			{
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Sets the buttons disable state.
	 * 
	 * @param isRunning
	 *            the new buttons disable state
	 */
	public void setButtonsDisableState(Boolean isRunning)
	{
		if (isRunning)
		{
			btnSettings.setDisable(true);
			btnStopMonitoring.setDisable(false);
			//btnShowMonitoringResults.setDisable(true);
			//btnShowVMSTATView.setDisable(true);
		}
		else
		{
			btnSettings.setDisable(false);
			btnStopMonitoring.setDisable(true);
			
			// Even if instance wasn't running before, leaving possibility to show previous runs
			/*if (CSharedInstance.getInstance().currentMonitoring == MonitorType.MonitorTypeVMSTAT)
			{
				btnShowMonitoringResults.setDisable(true);
				btnShowVMSTATView.setDisable(false);
			}
			else
			{
				btnShowMonitoringResults.setDisable(false);
				btnShowVMSTATView.setDisable(true);
			}*/
		}
	}

}







