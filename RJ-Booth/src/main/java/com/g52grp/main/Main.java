package com.g52grp.main;

import com.g52grp.database.DatabaseConnection;
import com.g52grp.pageloaders.HomePage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Launches Application and has some static methods for loading different files
 * @author psyfb2
 */
public class Main extends Application {
	//only create ONE connection for the whole program (take DatabaseConnection object as a parameter for your classes)
    public static final DatabaseConnection con = new DatabaseConnection();
    
    public static final String LOGOPATH 				= "rjb.png";
    public static final String BACKIMAGEPATH 			= "backButton.png";
    public static final String DELETEIMAGEPATH			= "deleteJobButton.png";
    public static final String UNARCHIVEIMAGEPATH 		= "unarchiveJobButton.png";
    public static final String CONFIGPATH 		        = "config.properties";
    
    public static final String JOBMENUPATH_FXML 		= "fxml/JobMenu.fxml";
    public static final String SINGLEJOBPATH_FXML 		= "fxml/SingleJob.fxml";
    public static final String ADDNEWJOBPATH_FXML 		= "fxml/AddNewJob.fxml";
    public static final String HOMEPAGE_FXML 			= "fxml/HomePage.fxml";
    public static final String ADDPRODUCTPAGE_FXML 		= "fxml/AddProductPage.fxml";
    public static final String STOCKMANAGMENTPAGE_FXML 	= "fxml/StockManagementPage.fxml";
    public static final String REPORTS_FXML 			= "fxml/Reports.fxml";
    public static final String ARCHIVIEDJOB_FXML        = "fxml/ArchivedJob.fxml";
    
    /*
	 * if you get
	 * Error: JavaFX runtime components are missing, and are required to run this application
	 * try running main method in GuiStarter.java instead as the entry point
	 */
	public static void main(String[] args) {
		con.openConnection(false);
		launch(args);
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
	
	/**
	 * Use this method to load an FXML file
	 * @param clazz getClass() of the caller
	 * @param resourceName path of FXML file to load
	 * @return FXMLLoader for this FXML file, call .load() on this loader to get the root
	 */
	public static FXMLLoader getFXMLFile(Class<?> clazz, String resourceName) {
		try {
			return new FXMLLoader(Main.class.getClassLoader().getResource(resourceName));
		} catch (Exception ignored) {
			throw new RuntimeException();
		}
	}
	
	/**
	 * Use this class to load an image resource
	 * @param resourcePath Path of image
	 * @return Image object
	 */
	public static Image getImageResource(String resourcePath) {
		return new Image(Main.class.getClassLoader().getResourceAsStream(resourcePath));
	}

	public static String getCSSFile(String resourcePath) {
		return Main.class.getClassLoader().getResource(resourcePath).toExternalForm();
	}
	
	/**
	 * Make the screen fullscreen. Only need to call once for the first page which is laoded.
	 * @param theStage Stage object of the screen
	 */
	public static void fullscreen(Stage theStage) {
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		
        // set Stage boundaries to visible bounds of the main screen
        theStage.setX(primaryScreenBounds.getMinX());
        theStage.setY(primaryScreenBounds.getMinY());
        theStage.setWidth(primaryScreenBounds.getWidth());
        theStage.setHeight(primaryScreenBounds.getHeight());
        theStage.setMaximized(true);
	}

}
