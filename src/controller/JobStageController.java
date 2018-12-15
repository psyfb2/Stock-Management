package controller;

import GUI.AddStage;
import GUI.DataManager;
import GUI.Job;
import GUI.ShowItemStage;
import GUI.StockInStage;
import GUI.StockOutStage;
import GUI.StockStage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class JobStageController {

	@FXML Button showButton;
	@FXML Button addButton;
	@FXML TableView<Job> jobTable;
	@FXML TableColumn<Job, String> nameCol;
	@FXML TableColumn<Job, String> locationCol;
	@FXML TableColumn<Job, String> itemsCol;
	
	
	@FXML public void initialize() {				
		nameCol.setCellValueFactory(new PropertyValueFactory<Job, String>("name"));
		locationCol.setCellValueFactory(new PropertyValueFactory<Job, String>("location"));
		itemsCol.setCellValueFactory(new PropertyValueFactory<Job, String>("items"));
				
		jobTable.setRowFactory(tv->{
			TableRow<Job> row = new TableRow<Job>();
			row.setOnMouseClicked(event->{
				if(event.getClickCount() == 2 && (!row.isEmpty())) {
					Job job = row.getItem();
					double xLocation = event.getScreenX();
					double yLocation = event.getScreenY();
					new StockStage(new Stage(), xLocation, yLocation);
					//System.out.println(job.getName());
					
				}
			});
			return row;
		});
		for(String job: DataManager.jobs.keySet()) {
			DataManager.data.add(new Job(job, DataManager.jobs.get(job), null));
		}
		jobTable.setItems(DataManager.data);
		
	}
	
	@FXML private void addClicked() {
		new AddStage(new Stage());
	}
	
	@FXML private void inClicked() {
		new StockInStage(new Stage());
	}
	
	@FXML private void outClicked() {
		new StockOutStage(new Stage());
	}
	
	@FXML private void showClicked() {
		new ShowItemStage(new Stage());
	}
	
	@FXML private void tableClicked() {
		
	}
	
}