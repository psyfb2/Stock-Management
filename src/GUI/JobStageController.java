package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class JobStageController {

	@FXML Button addButton;
	@FXML TableView<Job> jobTable;
	@FXML TableColumn<Job, String> nameCol;
	@FXML TableColumn<Job, String> locationCol;
	
	
	@FXML public void initialize() {				
		nameCol.setCellValueFactory(new PropertyValueFactory<Job, String>("name"));
		locationCol.setCellValueFactory(new PropertyValueFactory<Job, String>("location"));
		
		for(int i = 0; i < DataManager.names.size(); i++) {
			DataManager.data.add(new Job(DataManager.names.get(i), DataManager.locations.get(i)));
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
}
