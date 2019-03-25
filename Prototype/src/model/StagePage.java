package model;

import java.io.IOException;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class StagePage {
	public StagePage(Stage theStage) throws IOException {
		theStage.setTitle( "Start" );
        Parent root = FXMLLoader.load(getClass().getResource("/view/stage.fxml"));
        Scene Scene = new Scene( root, BasicParameter.getScrSize().getWidth(), BasicParameter.getScrSize().getHeight());
		theStage.setScene(Scene);
		theStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				StageManager.stockWindowClose();
				StageManager.addJobWindowClose();
			}
		});
		theStage.show();		
	}
}
