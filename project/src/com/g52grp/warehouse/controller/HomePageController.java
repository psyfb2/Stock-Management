package com.g52grp.warehouse.controller;


import java.io.IOException;

import com.g52grp.main.Main;
import com.g52grp.warehouse.model.BasicParameter;
import com.g52grp.warehouse.model.StockManagementPage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * 
 * @author psyzh1
 *
 */
public class HomePageController {

    @FXML
    private Pane homePane;

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
    	
    	//initialize homePane
    	 homePane.setPrefSize(BasicParameter.getScrSize().getWidth(), BasicParameter.getScrSize().getHeight());
    	 homePane.setLayoutX(0);
    	 homePane.setLayoutY(0);
    	 
     	//initialize homePageButton
    	 homePageButton.setPrefSize(BasicParameter.getButton2Width(), BasicParameter.getButton2Height());
    	 homePageButton.setLayoutX(0);
    	 homePageButton.setLayoutY(0);
    	 
     	//initialize stockButton
    	 stockManagementButton.setPrefSize(BasicParameter.getButton1Width(), BasicParameter.getButton1Height());
    	 stockManagementButton.setLayoutX(BasicParameter.getScrSize().getWidth()/2 - BasicParameter.getScrSize().getWidth()/4);
    	 stockManagementButton.setLayoutY( BasicParameter.getScrSize().getHeight()/4);
    	 
     	//initialize stockPicture
     	 stockPicture.setFitWidth(stockManagementButton.getPrefWidth());
     	 stockPicture.setFitHeight(stockManagementButton.getPrefHeight());
     	 
      	//initialize jobPicture
     	 jobPicture.setFitWidth(jobManagementButton.getPrefWidth());
     	 jobPicture.setFitHeight(jobManagementButton.getPrefHeight());
     	 
     	//initialize jobButton
    	 jobManagementButton.setPrefSize(BasicParameter.getButton1Width(), BasicParameter.getButton1Height());
    	 jobManagementButton.setLayoutX(BasicParameter.getScrSize().getWidth()/2 + BasicParameter.getScrSize().getWidth()/16);
    	 jobManagementButton.setLayoutY( BasicParameter.getScrSize().getHeight()/4);
    	 
       	//initialize jobPicture
      	 jobPicture.setFitWidth(jobManagementButton.getPrefWidth());
      	 jobPicture.setFitHeight(jobManagementButton.getPrefHeight());
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
     */
    @FXML
    void stockManagementButtonClicked() {
    	Stage theStage = (Stage)stockManagementButton.getScene().getWindow();
    	//theStage.close();
    	new StockManagementPage(theStage);
    }



}
