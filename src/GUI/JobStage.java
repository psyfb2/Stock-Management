package GUI;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JobStage {
	public JobStage(Stage theStage) {
		try {			
			Parent root = FXMLLoader.load(getClass().getResource("/view/JobStage.fxml"));
			//Dimension   screensize   =   Toolkit.getDefaultToolkit().getScreenSize();
			//int width = (int)screensize.getWidth();
			//int height = (int)screensize.getHeight();
			theStage.setTitle( "RJB" );
			Scene theScene = new Scene( root);
			theStage.setScene( theScene );
			theStage.show();
        } catch (IOException e) {
			e.printStackTrace();
		}
    }
}
