package com.g52grp.warehouse.model;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StockManagementPage {
	public StockManagementPage(Stage theStage) {
		try {			
			Parent root = FXMLLoader.load(getClass().getResource("../view/StockManagementPage.fxml"));
			theStage.setTitle( "RJB" );
			Scene theScene = new Scene( root );
			
			theStage.setScene( theScene );
			theStage.show();
        } catch (IOException e) {
			e.printStackTrace();
		}
    }
}
