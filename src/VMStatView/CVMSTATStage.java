package VMStatView;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import mainView.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CVMSTATStage 
{
	
	private Stage stage;
	
	public CVMSTATStage()
	{
		try 
		{
			stage = new Stage();
			
			URL location = CVMSTATStage.class.getResource("VMSTAT_Page.fxml");
			
	        AnchorPane settingsPage = (AnchorPane) FXMLLoader.load(location);
	        Scene scene = new Scene(settingsPage);
	        stage.setScene(scene);
	        stage.sizeToScene();
	        stage.setTitle("VMSTAT Results");
	        stage.setResizable(false);
	        stage.setFullScreen(false);
	        stage.setIconified(true);
	        stage.getIcons().add(new Image(getClass().getResourceAsStream("monitorIcon.jpeg")));
	        stage.show();
	    } 
		catch (Exception ex) 
	    {
	        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	
	
	public void closeStage()
	{
		stage.close();
		stage = null;
	}
	
}
