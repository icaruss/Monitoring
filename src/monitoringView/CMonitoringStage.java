/*
 * 
 */
package monitoringView;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import viewLogic.CSharedInstance;
import mainView.Main;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class CMonitoringStage.
 */
public class CMonitoringStage {

	/** The stage. */
	private Stage stage;

	/**
	 * Instantiates a new c monitoring stage.
	 */
	public CMonitoringStage() {
		try {
			stage = new Stage();

			URL location = CMonitoringStage.class
					.getResource("Monitoring_Page.fxml");

			AnchorPane settingsPage = (AnchorPane) FXMLLoader.load(location);
			Scene scene = new Scene(settingsPage);
			stage.setScene(scene);
			stage.sizeToScene();
			stage.setTitle("Monitoring Results");
			stage.setResizable(false);
			stage.setFullScreen(false);
			stage.setIconified(true);
			stage.getIcons()
					.add(new Image(getClass().getResourceAsStream(
							"monitorIcon.jpg")));
			
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() 
			{
				public void handle(WindowEvent we)
				{
					if (CSharedInstance.getInstance().monitoringOpenedInstances > 0)
							CSharedInstance.getInstance().monitoringOpenedInstances--;
				}
			});
			
			stage.show();
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Close stage.
	 */
	public void closeStage() 
	{
		stage.close();
		stage = null;
	}

}
