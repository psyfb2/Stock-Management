package com.g52grp.warehouse.controller;


import java.awt.Toolkit;
import java.io.IOException;

import com.g52grp.main.Main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
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
    
    /**
     * Change to Job Menu
     * @throws IOException
     */
    @FXML
    void jobManagementButtonClicked(MouseEvent e) throws IOException {
		FXMLLoader loader = Main.getFXMLFile(getClass(), Main.JOBMENUPATH_FXML);
		Parent root = loader.load();
        Scene jobMenu = new Scene(root);
  
        Stage theStage = (Stage) (((Node) e.getSource()).getScene().getWindow());
        theStage.setTitle("Job Menu");
        theStage.setScene( jobMenu );
        theStage.show();
    }

    /**
     * Change to stockManagementPage.
     * @throws IOException 
     */
    @FXML
    void stockManagementButtonClicked(MouseEvent e) throws IOException {
    	double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    	double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    	Parent root = Main.getFXMLFile(getClass(), Main.STOCKMANAGMENTPAGE_FXML).load();
        Stage theStage = (Stage) (((Node) e.getSource()).getScene().getWindow());
		theStage.setTitle( "RJB" );
		Scene theScene = new Scene( root, screenWidth, screenHeight );
		theStage.setScene( theScene );
		
		theStage.show();
		
    }


}