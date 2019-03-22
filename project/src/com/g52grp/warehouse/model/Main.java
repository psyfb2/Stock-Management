package com.g52grp.warehouse.model;

import java.io.IOException;

import com.g52grp.database.DatabaseConnection;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
    public static final DatabaseConnection con = new DatabaseConnection();
	
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
	   	 new HomePage(theStage);
	}
	
	@Override
	public void stop() throws Exception{
	    System.out.println("Stage is closing");
	    con.closeConnection();
	    super.stop();
	}
}