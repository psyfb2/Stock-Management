package com.g52grp.warehouse.model;

import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import com.g52grp.main.Main;

/**
 * 
 * @author psyzh1
 * Open HomePage fxml
 *
 */
public class HomePage {
	private static boolean firstLoad = false;
	
	public  HomePage(Stage theStage) throws IOException {
		theStage.setTitle( "RJ Booth Services" );
		theStage.getIcons().add(new Image("RJB.png"));
        Parent root = Main.getFXMLFile(getClass(), Main.HOMEPAGE_FXML).load();
        
        // only create one new scene, then use getScene().setRoot(root)
        if(!firstLoad) {
        	Scene scene = new Scene(root);
    		theStage.setScene(scene);
    		Main.fullscreen(theStage);
    		firstLoad = true;
        } else {
        	theStage.getScene().setRoot(root);
        }
        
        theStage.show();
	}
}
