package com.g52grp.views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * @author psyfb2
 * Controller for the Job Menu FXML file
 * Allows user to:
 * 		Viewing a list of jobs and select one of the jobs
 * 		Add a new job
 */
public class JobMenuController implements Initializable {
	@FXML Button addNewJob;

	
	/**
	 * Called when the addNewJob button is clicked, brings a pop up for the user to add a new job
	 * @throws IOException Failed to load AddNewJob.fxml
	 */
	@FXML public void newJobModal(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("./com/g52grp/views/AddNewJob.fxml"));
		Stage stage = new Stage();
        stage.setTitle("Add New Job");
        stage.setScene(new Scene(root, 600, 200));
        stage.show();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		return;
	}
}
