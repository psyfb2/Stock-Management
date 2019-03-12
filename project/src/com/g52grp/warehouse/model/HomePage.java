package com.g52grp.warehouse.model;

import java.io.FileInputStream;
import java.io.IOException;

import com.g52grp.main.Main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * 
 * @author psyzh1
 * Open HomePage fxml
 *
 */
public class HomePage {
	public  HomePage(Stage theStage) throws IOException {
		theStage.setTitle( "Start" );
		theStage.getIcons().add(new Image(new FileInputStream(Main.LOGOPATH)));
        Parent root = FXMLLoader.load(getClass().getResource(Main.HOMEPAGE_FXML));
        Scene Scene = new Scene( root, BasicParameter.getScrSize().getWidth(), BasicParameter.getScrSize().getHeight());
		theStage.setScene(Scene);
		theStage.show();
	}
}
