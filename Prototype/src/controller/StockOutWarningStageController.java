package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.DataManager;

public class StockOutWarningStageController {

	@FXML Button okButton;
	@FXML Text text;
	
	@FXML public void initialize() {				
		text.setText(DataManager.warning + " in stock.");
	}
	
	@FXML private void okButtonClicked() {
		Stage stage = (Stage)okButton.getScene().getWindow();
	    stage.close();
	}
}
