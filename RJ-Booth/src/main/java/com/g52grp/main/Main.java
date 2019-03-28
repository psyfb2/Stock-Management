package com.g52grp.main;

import com.g52grp.database.DatabaseConnection;
import com.g52grp.warehouse.model.HomePage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	// only create ONE connection for the whole program (take DatabaseConnection object as a parameter for your classes)
    public static final DatabaseConnection con = new DatabaseConnection();
    public static final String LOGOPATH = "rjb.png";
    public static final String BACKIMAGEPATH = "backButton.png";
    public static final String DELETEIMAGEPATH = "deleteJobButton.png";
    public static final String JOBMENUPATH_FXML = "fxml/JobMenu.fxml";
    public static final String SINGLEJOBPATH_FXML = "fxml/SingleJob.fxml";
    public static final String ADDNEWJOBPATH_FXML = "fxml/AddNewJob.fxml";
    public static final String HOMEPAGE_FXML = "fxml/HomePage.fxml";
    public static final String ADDPRODUCTPAGE_FXML = "fxml/AddProductPage.fxml";
    public static final String STOCKMANAGMENTPAGE_FXML = "fxml/StockManagementPage.fxml";
    
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
	
	public static FXMLLoader getFXMLFile(Class<?> clazz, String resourceName) {
		try {
			return new FXMLLoader(Main.class.getClassLoader().getResource(resourceName));
		} catch (Exception ignored) {
			throw new RuntimeException();
		}
	}
	
	public static Image getImageResource(String resourcePath) {
		return new Image(Main.class.getClassLoader().getResourceAsStream(resourcePath));
	}

	public static String getCSSFile(String resourcePath) {
		return Main.class.getClassLoader().getResource(resourcePath).toExternalForm();
	}
}