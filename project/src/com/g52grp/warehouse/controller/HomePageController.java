package com.g52grp.warehouse.controller;


import java.awt.Toolkit;
import java.io.IOException;

import com.g52grp.main.Main;
import com.g52grp.warehouse.model.StockManagementPage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * 
 * @author psyzh1
 *
 */
public class HomePageController {
    @FXML
    private GridPane homePane;

    @FXML
    private Button homePageButton;

    @FXML
    private Button stockManagementButton;

    @FXML
    private ImageView stockPicture;

    @FXML
    private Button jobManagementButton;

    @FXML
    private ImageView jobPicture;
    @FXML
    private void initialize() {
    	
    }
    
    @FXML
    void jobManagementButtonClicked(MouseEvent e) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(Main.JOBMENUPATH_FXML));
		Parent root = loader.load();
        Scene jobMenu = new Scene(root, 1280, 720);
  
        Stage theStage = (Stage) (((Node) e.getSource()).getScene().getWindow());

        theStage.setScene( jobMenu );
        theStage.show();
    }

    /**
     * Change to stockManagementPage.
     * @throws IOException 
     */
    @FXML
    void stockManagementButtonClicked(MouseEvent e) throws IOException {
    	String resource;
    	double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    	double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    	if(screenWidth> 1800) {
    		resource = Main.STOCKMANAGMENTPAGE_FXML;
    	}
    	else {
    	resource = Main.STOCKMANAGMENTPAGEFORMALLSIZE_FXML;
    	}
    	
    	Parent root = FXMLLoader.load(getClass().getResource(resource));
        Stage theStage = (Stage) (((Node) e.getSource()).getScene().getWindow());
		theStage.setTitle( "RJB" );
		Scene theScene = new Scene( root, screenWidth, screenHeight );
		theStage.setScene( theScene );
		theStage.show();
		
    }


}
