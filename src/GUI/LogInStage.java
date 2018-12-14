package GUI;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

public class LogInStage extends Application{
	@Override
    public void start(Stage theStage) {
		try {			
			Parent root = FXMLLoader.load(getClass().getResource("LogInStage.fxml"));
			theStage.setTitle( "RJB" );
			Scene theScene = new Scene( root, 400, 300);
			theScene.setFill(new ImagePattern(new Image("RJB.png")));
			theStage.setScene( theScene );
			theStage.show();
        } catch (IOException e) {
			e.printStackTrace();
		}
    }
	
    public static void main(String[] args){
        launch(args);
    }
	
}
