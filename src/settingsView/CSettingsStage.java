package settingsView;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import mainView.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CSettingsStage 
{
	
	private Stage stage;
	
	public CSettingsStage()
	{
		try 
		{
			stage = new Stage();
			
			URL location = CSettingsStage.class.getResource("Setting_Page.fxml");
			
	        AnchorPane settingsPage = (AnchorPane) FXMLLoader.load(location);
	        Scene scene = new Scene(settingsPage);
	        stage.setScene(scene);
	        stage.sizeToScene();
	        stage.setTitle("Servers Configuration");
	        stage.setResizable(false);
	        stage.setFullScreen(false);
	        stage.setIconified(true);
	        stage.getIcons().add(new Image(getClass().getResourceAsStream("iconSettings.jpg")));
	        stage.show();
	        
	        stage.requestFocus();
	        stage.toFront();
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
