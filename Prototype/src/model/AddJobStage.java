package model;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AddJobStage {
	public AddJobStage(Stage theStage) {
		try {
			StageManager.setAddJobWindow(theStage);
			Parent root = FXMLLoader.load(getClass().getResource("/view/AddStage.fxml"));
			theStage.setTitle( "RJB" );
			Scene theScene = new Scene( root );
			theStage.setScene( theScene );
			theStage.show();
        } catch (IOException e) {
			e.printStackTrace();
		}
    }
}
