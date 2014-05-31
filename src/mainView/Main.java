package mainView;
	

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class Main extends Application 
{
	@Override
	public void start(Stage primaryStage) 
	{
		
		try 
		{
            AnchorPane page = (AnchorPane) FXMLLoader.load(Main.class.getResource("Main_Page.fxml"));
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("MDM Unix onitoring Tool");
            primaryStage.setResizable(false);
            primaryStage.setFullScreen(false);
            primaryStage.show();
            
            
        } 
		catch (Exception ex) 
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
		
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
