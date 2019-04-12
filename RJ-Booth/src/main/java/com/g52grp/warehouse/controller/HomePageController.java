package com.g52grp.warehouse.controller;

import java.io.IOException;

import com.g52grp.main.Main;
import com.g52grp.warehouse.model.StockManagementPage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Label errorMessage;
    
    @FXML
    private void initialize() {
    	if (!Main.con.isConnected()) {
    		// there was an error connecting to the database
    		errorMessage.setText("Failed to connect to the database: Check your internet connection");
    		stockManagementButton.setDisable(true);
    		jobManagementButton.setDisable(true);
    	}
    }
    
    /**
     * Change to Job Menu
     * @throws IOException
     */
    @FXML
    void jobManagementButtonClicked(MouseEvent e) throws IOException {
		FXMLLoader loader = Main.getFXMLFile(getClass(), Main.JOBMENUPATH_FXML);
		Parent root = loader.load();
        
        Stage theStage = (Stage) (((Node) e.getSource()).getScene().getWindow());
        theStage.getScene().setRoot(root);
        theStage.setTitle("Job Menu");
        theStage.show();
    }

    /**
     * Change to stockManagementPage.
     * @throws IOException 
     */
    @FXML
    void stockManagementButtonClicked(MouseEvent e) throws IOException {
        Stage theStage = (Stage) (((Node) e.getSource()).getScene().getWindow());
		new StockManagementPage(theStage);
    }


}
