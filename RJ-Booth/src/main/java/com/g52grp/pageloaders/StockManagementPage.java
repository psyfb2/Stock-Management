package com.g52grp.pageloaders;

import java.io.IOException;

import com.g52grp.main.Main;

import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * Load StockManegementPage.fxml
 * @author psyzh1
 */
public class StockManagementPage {
	public StockManagementPage(Stage theStage) {
		try {			
			Parent root = Main.getFXMLFile(getClass(), Main.STOCKMANAGMENTPAGE_FXML).load();
			theStage.setTitle( "Warehouse" );
			theStage.getScene().setRoot(root);
			theStage.show();
        } catch (IOException e) {
		}
    }
}
