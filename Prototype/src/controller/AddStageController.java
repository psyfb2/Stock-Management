package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DataManager;
import model.Job;

public class AddStageController {

	@FXML private Button cancelButton;
	@FXML private Button okButton;
	@FXML private TextField nameField;
	@FXML private TextField locationField;
	
	@FXML private void cancelClicked() {
		Stage stage = (Stage)cancelButton.getScene().getWindow();
	    stage.close();
	}
	
	@FXML private void okClicked() {
		String name = nameField.getText();
		String location = locationField.getText();
		DataManager.jobs.put(name, location);
		DataManager.jobDetails.add(new Job(name, location, null));
		Stage stage = (Stage)cancelButton.getScene().getWindow();
	    stage.close();
	}
}
