package com.g52grp.pageloaders;

import java.io.IOException;

import com.g52grp.controllers.AddProductPageController;
import com.g52grp.controllers.StockManagementPageController;
import com.g52grp.main.Main;

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
