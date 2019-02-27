package com.g52grp.views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.g52grp.database.Job;
import com.g52grp.main.Main;
import com.g52grp.stockout.ConcreteJobManager;
import com.g52grp.stockout.JobManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * @author psyfb2
 * Controller for the Job Menu FXML file
 * Allows user to:
 * 		Viewing a list of jobs and select one of the jobs
 * 		Add a new job
 * 		
 */
public class JobMenuController implements Initializable {
	@FXML Button addNewJob;
	@FXML TableView<Job> jobTable;
	@FXML TableColumn<Job, String> siteName;
	@FXML TableColumn<Job, Integer> plotNumber;
	@FXML TableColumn<Job, String> date;
	
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
	
	/**
	 * Load jobs from the database 
	 * @return List of Job objects to be added to the tableview, null if could not access database
	 */
	public ObservableList<Job> getJobs() {
		JobManager jm = new ConcreteJobManager(Main.con);
		Job[] jobsArr = jm.getAllJobs();
		if(jobsArr == null) {
			return null;
		}
		ObservableList<Job> jobsList = FXCollections.observableArrayList();
		for(Job j : jobsArr) {
			jobsList.add(j);
		}
		return jobsList;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		return;
	}
}
