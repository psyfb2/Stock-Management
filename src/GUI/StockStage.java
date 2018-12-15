package GUI;

import java.io.IOException;
import java.lang.ModuleLayer.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StockStage {
	public String items;
	private Controller controller;
	public StockStage(Stage theStage, double x, double y) {
		try {	
			FXMLLoader loader = new FXMLLoader();
			Parent root = loader.load(getClass().getResource("/view/StockStage.fxml"));
			controller = loader.getController();
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
