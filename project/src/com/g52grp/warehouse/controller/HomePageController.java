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
     */
    @FXML
    void stockManagementButtonClicked() {
    	Stage theStage = (Stage)stockManagementButton.getScene().getWindow();
    	//theStage.close();
    	new StockManagementPage(theStage);
    }


}
