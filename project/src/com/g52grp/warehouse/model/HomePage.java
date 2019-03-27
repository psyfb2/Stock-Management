package com.g52grp.warehouse.model;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import com.g52grp.main.Main;

/**
 * 
 * @author psyzh1
 * Open HomePage fxml
 *
 */
public class HomePage {
	// initially set to be full screen
	public static double screenWidth;
	public static double screenHeight;
	private static boolean fullscreen = true;
	
	public  HomePage(Stage theStage) throws IOException {

		theStage.setTitle( "Home" );
		theStage.getIcons().add(new Image("RJB.png"));
        Parent root = FXMLLoader.load(getClass().getResource(Main.HOMEPAGE_FXML));
        Scene Scene = new Scene( root,screenWidth, screenHeight);

		theStage.setScene(Scene);
		if(fullscreen) {
			fullscreen(theStage);
		}
		fullscreen = false;
		theStage.show();
	}
	
	private void fullscreen(Stage theStage) {
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		
        //set Stage boundaries to visible bounds of the main screen
        theStage.setX(primaryScreenBounds.getMinX());
        theStage.setY(primaryScreenBounds.getMinY());
        theStage.setWidth(primaryScreenBounds.getWidth());
        theStage.setHeight(primaryScreenBounds.getHeight());
        theStage.setMaximized(true);
	}
}
