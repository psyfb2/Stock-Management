package model;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AddItemStage {
	public AddItemStage() {
		Stage theStage = new Stage();
		StageManager.setAddJobWindow(theStage);
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/view/AddItemStage.fxml"));
			theStage.setTitle( "RJB" );
			Scene theScene = new Scene( root );
			theStage.setScene( theScene );
			theStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
