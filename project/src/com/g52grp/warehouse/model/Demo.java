package com.g52grp.warehouse.model;

import java.io.FileInputStream;
import java.io.IOException;

import com.g52grp.database.DatabaseConnection;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Demo extends Application{
	
	// only create ONE connection for the whole program (take DatabaseConnection object as a parameter for your classes)
    public static final DatabaseConnection con = new DatabaseConnection();
    /*public static final String LOGOPATH = "./resources/rjb.png";
    public static final String BACKIMAGEPATH = "./resources/backButton.png";
    public static final String DELETEIMAGEPATH = "./resources/deleteButton.png";
    public static final String JOBMENUPATH_FXML = "./com/g52grp/views/JobMenu.fxml";
    public static final String SINGLEJOBPATH_FXML = "./com/g52grp/views/SingleJob.fxml";
    public static final String ADDNEWJOBPATH_FXML = "./com/g52grp/views/AddNewJob.fxml";*/
	
	public static void main(String[] args) {
		
		DatabaseConnection test = con;
		if (test.openConnection()) {
			System.out.println("Successful Connection");
			launch(args);
		} else {
			System.out.println("Connection Failed");
		}
	}
		
	public void start(Stage theStage) throws IOException{
	   	 new StockManagementPage(theStage);
	}
	
	@Override
	public void stop() throws Exception{
	    System.out.println("Stage is closing");
	    con.closeConnection();
	    super.stop();
	}

}
