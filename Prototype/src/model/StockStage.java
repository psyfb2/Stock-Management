package model;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StockStage {
	public StockStage(Stage theStage, double x, double y) {
		try {	
			StageManager.setStockWindow(theStage);
			Parent root = FXMLLoader.load(getClass().getResource("/view/StockStage.fxml"));
			theStage.setTitle( "RJB" );
			//theStage.initStyle(StageStyle.TRANSPARENT);
			Scene theScene = new Scene( root );
			//theScene.setFill(null);
			theStage.initStyle(StageStyle.UNDECORATED);
			theStage.setX(x);
			theStage.setY(y);
			theStage.setScene( theScene );
			theStage.show();
        } catch (IOException e) {
			e.printStackTrace();
		}
    }
}
