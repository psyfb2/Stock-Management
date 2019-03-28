package com.g52grp.warehouse.model;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * @author psyzh1
 * Open StockManegementPage fxml
 *
 */
public class StockManagementPage {
	public StockManagementPage(Stage theStage) {
		try {			
			Parent root = FXMLLoader.load(getClass().getResource("/com/g52grp/warehouse/view/StockManagementPage.fxml"));
			theStage.setTitle( "RJB" );
			Scene theScene = new Scene( root );
			theStage.setScene( theScene );
			theStage.show();
        } catch (IOException e) {
			e.printStackTrace();
		}
    }
}