package com.g52grp.warehouse.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import com.g52grp.main.Main;
import com.g52grp.warehouse.controller.AddProductPageController;
import com.g52grp.warehouse.controller.StockManagementPageController;

public class AddProductPage {
	public  AddProductPage(Stage theStage, StockManagementPageController smController) throws IOException {
		theStage.setTitle( "Add New Product" );
		theStage.getIcons().add(new Image(new FileInputStream(Main.LOGOPATH)));
		URL location = getClass().getResource(Main.ADDPRODUCTPAGE_FXML);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(location);
		Parent root = loader.load();
        Scene Scene = new Scene( root);
		theStage.setScene(Scene);
		theStage.setAlwaysOnTop(true);
		
		AddProductPageController controller = loader.<AddProductPageController>getController();
        controller.initData(smController);
        
		theStage.show();
	}
}
