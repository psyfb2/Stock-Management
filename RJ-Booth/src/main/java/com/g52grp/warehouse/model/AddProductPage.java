package com.g52grp.warehouse.model;

import java.io.IOException;

import com.g52grp.main.Main;
import com.g52grp.warehouse.controller.AddProductPageController;
import com.g52grp.warehouse.controller.StockManagementPageController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AddProductPage {
	public  AddProductPage(Stage theStage, StockManagementPageController smController) throws IOException {
		theStage.setTitle( "RJ Booth Services" );
		theStage.getIcons().add(new Image(Main.LOGOPATH));
		theStage.setTitle( "Add New Product" );
		FXMLLoader loader = Main.getFXMLFile(getClass(), Main.ADDPRODUCTPAGE_FXML);
		Parent root = loader.load();
		//theStage.getScene().setRoot(root);
		theStage.setScene(new Scene(root));
		theStage.setAlwaysOnTop(true);
		
		AddProductPageController controller = loader.<AddProductPageController>getController();
        controller.initData(smController);
        
		theStage.show();
	}
}
