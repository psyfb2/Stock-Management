package com.g52grp.warehouse.model;

import java.awt.Toolkit;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import com.g52grp.main.Main;

/**
 * 
 * @author psyzh1
 * Open HomePage fxml
 *
 */
public class HomePage {
	public  HomePage(Stage theStage) throws IOException {
    	String resource;
    	double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    	double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    	resource = Main.HOMEPAGE_FXML;
		theStage.setTitle( "home" );
		theStage.getIcons().add(new Image("RJB.png"));
        Parent root = FXMLLoader.load(getClass().getResource(resource));
        Scene Scene = new Scene( root,screenWidth, screenHeight);
		theStage.setScene(Scene);
		theStage.show();
	}
}
