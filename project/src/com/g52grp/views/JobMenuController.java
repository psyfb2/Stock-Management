package com.g52grp.views;

import java.io.FileInputStream;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @author psyfb2
 * Controller for the Job Menu FXML file
 * Allows user to:
 * 		Viewing a list of jobs and select one of the jobs
 * 		Add a new job
 * 		
 */
public class JobMenuController implements Initializable, TableViewUpdate {
	private JobManager jm;
	@FXML Button addNewJob;
	@FXML TableView<Job> jobTable;
	@FXML TableColumn<Job, String> siteName;
	@FXML TableColumn<Job, Integer> plotNumber;
	@FXML TableColumn<Job, String> date;
	@FXML TableColumn<Job, Integer> jobId; // this column is not visible to the user
	
	public JobMenuController() {
		jm = new ConcreteJobManager(Main.con);
	}
	
	/**
	 * Called when the addNewJob button is clicked, brings a pop up for the user to add a new job
	 * @throws IOException Failed to load AddNewJob.fxml
	 */
	@FXML public void newJobModal(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(Main.ADDNEWJOBPATH_FXML));
		Parent root = loader.load();
		Stage stage = new Stage();
        stage.setTitle("Add New Job");
        stage.setScene(new Scene(root, 600, 200));
        
        // after the AddNewJob successfully adds a new job, TableView needs to be updated
        // so pass a reference to TableUpdate method to the controller which it will call
        AddNewJobController controller = loader.<AddNewJobController>getController();
        controller.initData(this);
        stage.getIcons().add(new Image(new FileInputStream(Main.LOGOPATH)));
        stage.show();
	}
	
	/**
	 * Load jobs from the database 
	 * @return List of Job objects to be added to the tableview, empty List if could not access database
	 */
	public ObservableList<Job> getJobs() {
		Job[] jobsArr = jm.getAllJobs();
		ObservableList<Job> jobsList = FXCollections.observableArrayList();
		if(jobsArr == null) {
			return jobsList;
		}
		jobsList.addAll(jobsArr);
		return jobsList;
	}
	
	/**
	 * Fill the table to contains jobs currently stored on the database 
	 * can call this method from other controllers, for example after updating Jobs table
	 */
	@Override
	public void updateTableView() {
		ObservableList<Job> jobsList = jobTable.getItems();
		if(jobsList != null) {
			jobsList.removeAll();
		}
		jobTable.setItems(getJobs());
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// initialize columns
		siteName.setCellValueFactory(new PropertyValueFactory<>("siteName"));
		plotNumber.setCellValueFactory(new PropertyValueFactory<>("plotNumber"));
		date.setCellValueFactory(new PropertyValueFactory<>("date"));
		jobId.setCellValueFactory(new PropertyValueFactory<>("jobId"));
		
		// add double click mouse listener to detect when a row is double clicked
		jobTable.setRowFactory(new Callback<TableView<Job>, TableRow<Job>>() {
			@Override
			public TableRow<Job> call(TableView<Job> param) {
				TableRow<Job> row = new TableRow<>();
				row.setOnMouseClicked(e -> {
					if(e.getClickCount() == 2 && (!row.isEmpty()) ) {
						// row was double clicked, so move to SingleJob.fxml to view this job without creating a new window
						try {
							FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(Main.SINGLEJOBPATH_FXML));
							Parent root = loader.load();
							
							// pass jobId to SingleJobController
							SingleJobController c = loader.<SingleJobController>getController();
							Job selectedJob = row.getItem();
							c.initData(selectedJob.getJobId(), "" + selectedJob.getSiteName() + " " + selectedJob.getPlotNumber());

					        Scene singleJobView = new Scene( root );
					
					        Stage theStage = (Stage) (((Node) e.getSource()).getScene().getWindow());

					        theStage.setScene( singleJobView );
					        theStage.show();
						} catch(Exception ex) {
							ex.printStackTrace();
						}
					}
				});
				return row;
			}
		});
		
		// populate the table
		updateTableView();
	}
}
