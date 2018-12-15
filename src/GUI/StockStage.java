package GUI;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StockStage {
	
	public StockStage(Stage theStage, double x, double y) {
		try {	
			Parent root = FXMLLoader.load(getClass().getResource("StockStage.fxml"));
			theStage.setTitle( "RJB" );
			Scene theScene = new Scene( root );
			theStage.setX(x);
			theStage.setY(y);
			theStage.initStyle(StageStyle.UNDECORATED);
			theStage.setScene( theScene );
			theStage.show();
        } catch (IOException e) {
			e.printStackTrace();
		}
    }
	
}
