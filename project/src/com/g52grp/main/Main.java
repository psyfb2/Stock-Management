package com.g52grp.main;

import com.g52grp.database.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	// only create ONE connection for the whole program (take DatabaseConnection object as a parameter for your classes)
    public static final DatabaseConnection con = new DatabaseConnection();
	
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
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("./com/g52grp/views/JobMenu.fxml"));
	    theStage.setTitle( "RJB - Current Jobs" );
	    theStage.setScene(new Scene (root, 1280, 720));

	    theStage.show();
	}
	
	@Override
	public void stop() throws Exception{
	    System.out.println("Stage is closing");
	    con.closeConnection();
	    super.stop();
	}

}
