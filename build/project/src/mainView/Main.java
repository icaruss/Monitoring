/*
 * 
 */
package mainView;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import viewLogic.CSharedInstance;

/**
 * The Class Main.
 */
public class Main extends Application {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) {

		try {
			AnchorPane page = (AnchorPane) FXMLLoader.load(Main.class
					.getResource("Main_Page.fxml"));
			Scene scene = new Scene(page);
			primaryStage.setScene(scene);
			primaryStage.setTitle("MDM Unix monitoring Tool");
			primaryStage.setResizable(false);
			primaryStage.setFullScreen(false);
			primaryStage.setIconified(true);
			primaryStage.getIcons().add(
					new Image(getClass().getResourceAsStream("iconMain.jpg")));
			

			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
					System.out.println("MDM Monitoring is closing");

					if (CSharedInstance.getInstance().executeUnixOperations != null) {
						try {
							CSharedInstance.getInstance().executeUnixOperations
									.finish();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ParseException e) {
							e.printStackTrace();
						} finally {
							Platform.exit();
						}
					} else {
						Platform.exit();
					}
				}
			});

			primaryStage.show();

		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	/**
	 * The main Project method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
