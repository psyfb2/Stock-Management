package com.g52grp.main;

import com.g52grp.database.*;
import com.g52grp.warehouse.model.HomePage;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	// only create ONE connection for the whole program (take DatabaseConnection object as a parameter for your classes)
    public static final DatabaseConnection con = new DatabaseConnection();
    public static final String LOGOPATH = "./resources/rjb.png";
    public static final String BACKIMAGEPATH = "./resources/backButton.png";
    public static final String DELETEIMAGEPATH = "./resources/deleteJobButton.png";
    public static final String JOBMENUPATH_FXML = "./com/g52grp/views/JobMenu.fxml";
    public static final String SINGLEJOBPATH_FXML = "./com/g52grp/views/SingleJob.fxml";
    public static final String ADDNEWJOBPATH_FXML = "./com/g52grp/views/AddNewJob.fxml";
    public static final String HOMEPAGE_FXML = "/com/g52grp/warehouse/view/HomePage.fxml";
	
	public static void main(String[] args) {
		if (con.openConnection()) {
			launch(args);
		} else {
			System.out.println("Connection Failed");
		}
	}
	
	@Override
	public void start(Stage theStage) throws Exception {
		new HomePage(theStage);
	}
	
	@Override
	public void stop() throws Exception{
	    con.closeConnection();
	    super.stop();
	}

}
