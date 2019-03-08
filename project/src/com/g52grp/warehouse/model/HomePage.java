package com.g52grp.warehouse.model;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class HomePage {
	public  HomePage(Stage theStage) throws IOException {
		theStage.setTitle( "Start" );
		theStage.getIcons().add(new Image("RJB.png"));
        Parent root = FXMLLoader.load(getClass().getResource("/com/g52grp/warehouse/view/HomePage.fxml"));
       Scene Scene = new Scene( root, BasicParameter.getScrSize().getWidth(), BasicParameter.getScrSize().getHeight());
		theStage.setScene(Scene);
		theStage.show();
	}
}
