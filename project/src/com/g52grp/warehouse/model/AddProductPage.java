package com.g52grp.warehouse.model;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import com.g52grp.main.Main;

public class AddProductPage {
	public  AddProductPage(Stage theStage) throws IOException {
		theStage.setTitle( "Start" );
		theStage.getIcons().add(new Image("RJB.png"));
        Parent root = FXMLLoader.load(getClass().getResource(Main.ADDPRODUCTPAGE_FXML));
        Scene Scene = new Scene( root);
		theStage.setScene(Scene);
		theStage.setAlwaysOnTop(true);
		theStage.show();
	}
}
