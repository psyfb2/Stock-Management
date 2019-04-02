package controller;

import model.AddJobStage;
import model.BasicParameter;
import model.DataManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Item;
import model.Job;
import model.AddItemStage;
import model.StageManager;
import model.StockStage;

public class StageController {

	@FXML AnchorPane pane;
	@FXML ImageView logo;
	@FXML Button removeButton;
	@FXML Button showButton;
	@FXML Button addButton;
	@FXML Button jobListButton;
	@FXML Button stockInButton;
	@FXML Button stockOutButton;
	@FXML Button newItemButton;
	@FXML Button deleteButton;
	@FXML Button cancelButton;

	@FXML TableView<Job> jobTable;
	@FXML TableColumn<Job, String> nameCol;
	@FXML TableColumn<Job, String> locationCol;
	@FXML TableColumn<Job, String> itemsCol;
	@FXML TableColumn<Job, CheckBox> checkBoxCol;

	@FXML TableView<Item> itemTable;
	@FXML TableColumn<Item, String> itemCol;
	@FXML TableColumn<Item, String> quantityCol;

	@FXML private void initialize() {
		DataManager.itemTable = itemTable;
		itemCol.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
		quantityCol.setCellValueFactory(new PropertyValueFactory<Item, String>("quantity"));

		for(String item : DataManager.items.keySet()) {
			DataManager.itemDetails.add(new Item(item, DataManager.items.get(item)));
		}
		itemTable.setItems(DataManager.itemDetails);		

		quantityCol.setCellFactory(new Callback<TableColumn<Item, String>, TableCell<Item, String>>() {
			@Override
			public TableCell<Item, String> call(TableColumn<Item, String> param) {
				return new TableCell<Item, String>(){
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if(!isEmpty()) {
							if(Integer.parseInt(item) < 5) {
								this.setTextFill(Color.RED);							
							}else {
								this.setTextFill(Color.BLACK);
							}
							setText(item);
						}else {
							setText(null);
						}
					}
				};
			}
			
		});
		
		DataManager.jobTable = jobTable;
		nameCol.setCellValueFactory(new PropertyValueFactory<Job, String>("name"));
		locationCol.setCellValueFactory(new PropertyValueFactory<Job, String>("location"));
		itemsCol.setCellValueFactory(new PropertyValueFactory<Job, String>("items"));
		checkBoxCol.setCellValueFactory(cellData -> cellData.getValue().getCheckBox().getCheckBox());

		//nameCol.setSortType(SortType.ASCENDING);

		for(String job: DataManager.jobs.keySet()) {
			DataManager.jobDetails.add(new Job(job, DataManager.jobs.get(job), null));
		}
		jobTable.setItems(DataManager.jobDetails);
		jobTable.setRowFactory(tv->{
			TableRow<Job> row = new TableRow<Job>();
			row.setOnMouseClicked(event->{
				if((StageManager.getStockWindow() == null) && event.getClickCount() == 1 && (!row.isEmpty()) && event.getButton() == MouseButton.SECONDARY) {
					Job job = row.getItem();
					DataManager.job = job;
					double xLocation = event.getScreenX();
					double yLocation = event.getScreenY();
					new StockStage(new Stage(), xLocation, yLocation);
				}else {
					StageManager.stockWindowClose();
				}
			});
			return row;
		});

		logo.setFitHeight(BasicParameter.getJobStageButtonHeight() * 5 / 4);
		logo.setFitWidth(BasicParameter.getScrSize().getWidth());
		logo.setLayoutX(0);
		logo.setLayoutY(0);
		pane.setPrefSize(BasicParameter.getScrSize().getWidth(), BasicParameter.getScrSize().getHeight());
		jobTable.setPrefSize(BasicParameter.getTableWidth(), BasicParameter.getTableHeight());
		//jobTable.setLayoutX((BasicParameter.scrSize.getWidth() - BasicParameter.jobTableWidth)/2);
		jobTable.setLayoutX(BasicParameter.getJobStageButtonWidth());
		jobTable.setLayoutY(BasicParameter.getJobStageButtonHeight() * 5 / 4);
		nameCol.setPrefWidth(BasicParameter.getScrSize().getWidth() * 0.25);
		locationCol.setPrefWidth(BasicParameter.getScrSize().getWidth() * 0.35);
		itemsCol.setPrefWidth(BasicParameter.getScrSize().getWidth() * 0.4);
		checkBoxCol.setVisible(false);
		
		itemTable.setVisible(false);
		itemTable.setPrefSize(BasicParameter.getTableWidth(), BasicParameter.getTableHeight());
		//jobTable.setLayoutX((BasicParameter.scrSize.getWidth() - BasicParameter.jobTableWidth)/2);
		itemTable.setLayoutX(BasicParameter.getJobStageButtonWidth());
		itemTable.setLayoutY(BasicParameter.getJobStageButtonHeight() * 5 / 4);
		itemCol.setPrefWidth(BasicParameter.getScrSize().getWidth()/2);
		quantityCol.setPrefWidth(BasicParameter.getScrSize().getWidth()/2);
		
		jobListButton.setPrefSize(BasicParameter.getJobStageButtonWidth(), BasicParameter.getJobStageButtonHeight());		
		jobListButton.setLayoutX(0);
		jobListButton.setLayoutY(BasicParameter.getJobStageButtonHeight() * 5 / 4);
		
		addButton.setPrefSize(BasicParameter.getJobStageButtonWidth(), BasicParameter.getJobStageButtonHeight());
		addButton.setLayoutX(0);
		addButton.setLayoutY(BasicParameter.getJobStageButtonHeight() * 5 / 4 + BasicParameter.getJobStageButtonHeight());
		
		removeButton.setPrefSize(BasicParameter.getJobStageButtonWidth(), BasicParameter.getJobStageButtonHeight());
		removeButton.setLayoutX(0);
		removeButton.setLayoutY(BasicParameter.getJobStageButtonHeight() * 5 / 4 + BasicParameter.getJobStageButtonHeight() * 2);
		
		deleteButton.setPrefSize(BasicParameter.getJobStageButtonWidth()/2, BasicParameter.getJobStageButtonHeight());
		deleteButton.setLayoutX(0);
		deleteButton.setLayoutY(BasicParameter.getJobStageButtonHeight() * 5 / 4 + BasicParameter.getJobStageButtonHeight() * 2);
		
		cancelButton.setPrefSize(BasicParameter.getJobStageButtonWidth()/2, BasicParameter.getJobStageButtonHeight());
		cancelButton.setLayoutX(BasicParameter.getJobStageButtonWidth()/2);
		cancelButton.setLayoutY(BasicParameter.getJobStageButtonHeight() * 5 / 4 + BasicParameter.getJobStageButtonHeight() * 2);
		
		showButton.setPrefSize(BasicParameter.getJobStageButtonWidth(), BasicParameter.getJobStageButtonHeight());			
		showButton.setLayoutX(0);
		showButton.setLayoutY(BasicParameter.getJobStageButtonHeight() * 5 / 4 + BasicParameter.getJobStageButtonHeight() * 3);
				
		newItemButton.setPrefSize(BasicParameter.getJobStageButtonWidth(), BasicParameter.getJobStageButtonHeight());			
		newItemButton.setLayoutX(0);
		newItemButton.setLayoutY(BasicParameter.getJobStageButtonHeight() * 5 / 4 + BasicParameter.getJobStageButtonHeight() * 4);
				
		jobTable.setStyle("-fx-font-size: 13pt");
		itemTable.setStyle("-fx-font-size: 13pt");		
	}

	@FXML private void addClicked() {
		removeButton.setStyle("-fx-background-color: #d1eefd");
		addButton.setStyle("-fx-background-color:  #e2f4ff");
		jobListButton.setStyle("-fx-background-color: #d1eefd");
		showButton.setStyle("-fx-background-color:  #d1eefd");
		newItemButton.setStyle("-fx-background-color: #d1eefd");
		new AddJobStage(new Stage());
		
		StageManager.stockWindowClose();
	}
		
	@FXML private void showClicked() {
		removeButton.setStyle("-fx-background-color: #d1eefd");
		showButton.setStyle("-fx-background-color:  #e2f4ff");
		jobListButton.setStyle("-fx-background-color:  #d1eefd");
		addButton.setStyle("-fx-background-color:  #d1eefd");
		newItemButton.setStyle("-fx-background-color: #d1eefd");
		jobTable.setVisible(false);
		itemTable.setVisible(true);
		
		StageManager.stockWindowClose();
		
		
	}
		
	@FXML private void jobListClicked() {
		removeButton.setStyle("-fx-background-color: #d1eefd");
		jobListButton.setStyle("-fx-background-color: #e2f4ff");
		showButton.setStyle("-fx-background-color:   #d1eefd");
		addButton.setStyle("-fx-background-color:  #d1eefd");
		newItemButton.setStyle("-fx-background-color: #d1eefd");
		jobTable.setVisible(true);
		itemTable.setVisible(false);

		StageManager.stockWindowClose();
	}
	
	@FXML private void newItemClicked() {
		removeButton.setStyle("-fx-background-color: #d1eefd");
		jobListButton.setStyle("-fx-background-color: #d1eefd");
		showButton.setStyle("-fx-background-color:   #d1eefd");
		addButton.setStyle("-fx-background-color:  #d1eefd");
		newItemButton.setStyle("-fx-background-color: #e2f4ff");
		new AddItemStage();
	}
	
	@FXML private void removeClicked() {
		removeButton.setStyle("-fx-background-color: #e2f4ff");
		jobListButton.setStyle("-fx-background-color: #d1eefd");
		showButton.setStyle("-fx-background-color:   #d1eefd");
		addButton.setStyle("-fx-background-color:  #d1eefd");
		newItemButton.setStyle("-fx-background-color: #d1eefd");
		nameCol.setPrefWidth(BasicParameter.getScrSize().getWidth() * 0.24);
		locationCol.setPrefWidth(BasicParameter.getScrSize().getWidth() * 0.31);
		itemsCol.setPrefWidth(BasicParameter.getScrSize().getWidth() * 0.36);
		checkBoxCol.setPrefWidth(BasicParameter.getScrSize().getWidth() * 0.09);
		checkBoxCol.setVisible(true);
		deleteButton.setVisible(true);
		cancelButton.setVisible(true);
		removeButton.setVisible(false);
		
		addButton.setDisable(true);
		showButton.setDisable(true);
		jobListButton.setDisable(true);
		newItemButton.setDisable(true);
	}
	
	@FXML private void deleteButtonClicked() {
		ObservableList<Job> newJobDetails =  FXCollections.observableArrayList();
		for(Job job : jobTable.getItems()) {
			if(job.getCheckBox().isSelected()) {
				DataManager.jobs.remove(job.getName());
			}else {
				newJobDetails.add(new Job(job.getName(), job.getLocation(), job.getItems()));
			}
		}
		DataManager.jobDetails = newJobDetails;
		jobTable.setItems(newJobDetails);
		deleteButton.setVisible(false);
		cancelButton.setVisible(false);
		removeButton.setVisible(true);
		nameCol.setPrefWidth(BasicParameter.getScrSize().getWidth() * 0.25);
		locationCol.setPrefWidth(BasicParameter.getScrSize().getWidth() * 0.35);
		itemsCol.setPrefWidth(BasicParameter.getScrSize().getWidth() * 0.4);
		checkBoxCol.setVisible(false);
		
	}
	
	@FXML private void cancelButtonClicked() {
		deleteButton.setVisible(false);
		cancelButton.setVisible(false);
		removeButton.setVisible(true);
		nameCol.setPrefWidth(BasicParameter.getScrSize().getWidth() * 0.25);
		locationCol.setPrefWidth(BasicParameter.getScrSize().getWidth() * 0.35);
		itemsCol.setPrefWidth(BasicParameter.getScrSize().getWidth() * 0.4);
		checkBoxCol.setVisible(false);
		
		addButton.setDisable(false);
		showButton.setDisable(false);
		jobListButton.setDisable(false);
		newItemButton.setDisable(false);
	}

	@FXML private void paneClicked() {
		StageManager.stockWindowClose();
	}

	@FXML private void logoClicked() {
		StageManager.stockWindowClose();
	}

}