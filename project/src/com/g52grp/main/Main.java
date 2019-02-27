package com.g52grp.main;

import java.io.FileInputStream;

import com.g52grp.database.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	
	// only create ONE connection for the whole program (take DatabaseConnection object as a parameter for your classes)
    public static final DatabaseConnection con = new DatabaseConnection();
    public static final String LOGOPATH = "./resources/rjb.png";
    public static final String BACKIMAGEPATH = "./resources/backButton.png";
    public static final String JOBMENUPATH_FXML = "./com/g52grp/views/JobMenu.fxml";
    public static final String SINGLEJOBPATH_FXML = "./com/g52grp/views/SingleJob.fxml";
    public static final String ADDNEWJOBPATH_FXML = "./com/g52grp/views/AddNewJob.fxml";
	
	public static void main(String[] args) {
		
		DatabaseConnection test = con;
		// connection always fails in uni for some reason, must be the damn firewall
		if (test.openConnection()) {
			System.out.println("Successful Connection");
			launch(args);
		} else {
			System.out.println("Connection Failed");
		}
	}
	
	@Override
	public void start(Stage theStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(JOBMENUPATH_FXML));
	    theStage.setTitle( "RJB - Current Jobs" );
	    theStage.setScene(new Scene (root, 1280, 720));
	    theStage.getIcons().add(new Image(new FileInputStream(LOGOPATH)));
	    theStage.show();
	}
	
	@Override
	public void stop() throws Exception{
	    System.out.println("Stage is closing");
	    con.closeConnection();
	    super.stop();
	}

}
