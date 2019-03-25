package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.DataManager;
import model.Item;
import model.StockOutWarningStage;

public class StockOutStageController {
	@FXML private Button scannerButton;
	@FXML private Button confirmButton;
	@FXML private TableView<Item> outTable;
	@FXML private TableColumn<Item, String> itemCol;
	@FXML private TableColumn<Item, TextField> quantityCol;
	private int countScanner = 0;
	private static ObservableList<Item> outItems =  FXCollections.observableArrayList();
	
	@FXML public void initialize() {				
		itemCol.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
		quantityCol.setCellValueFactory(cellData -> cellData.getValue().getMyTextField().getTextField());
		outItems.removeAll(outItems);
		outTable.setItems(outItems);
		
	}
	
	@FXML private void scannerClicked() {
		countScanner++;
		String item = "item" + countScanner;
		outItems.add(new Item(item, 1));			
	}
	
	@FXML private void confirmClicked() {
		String jobItems = new String();
		boolean canConfirm = true;
		DataManager.warning = "There are not enough";
		
		for(Item item : outTable.getItems()) {
			String name = item.getName();
			String quantity = item.getMyTextField().getText();
			int value = Integer.parseInt(DataManager.items.get(name)) - Integer.parseInt(quantity);
			if(value >= 0) {
				DataManager.items.put(name, String.valueOf(value));
			}else {
				canConfirm = false;
				DataManager.warning = DataManager.warning + " "+ name;
			}			
		}
				
		if(!canConfirm) {
			//System.out.println("qnmd");
			new StockOutWarningStage(new Stage());
		}else {
			for(Item item :  outTable.getItems()) {
				boolean isWritten = false;
				
				if(DataManager.job.getItems() != null) {
					for(String b :  DataManager.job.getItems().split(",\\s+")) {
					String name = b.split(":\\s+")[0];
					String quantity = b.split(":\\s+")[1];
					if(item.getName().equals(name)) {
						int n = Integer.parseInt(item.getMyTextField().getText()) + Integer.parseInt(quantity);
						jobItems = jobItems + item.getName() + ": " + n + ", ";
						isWritten = true;
						}
					}
				}
				
				if(!isWritten) {
					jobItems = jobItems + item.getName() + ": " + item.getMyTextField().getText() + ", ";
				}
			}
			
			DataManager.job.setItems(jobItems);
			DataManager.jobTable.refresh();
			
			DataManager.itemDetails.clear();
			for(String item : DataManager.items.keySet()) {
				DataManager.itemDetails.add(new Item(item, DataManager.items.get(item)));
			}
			DataManager.itemTable.setItems(DataManager.itemDetails);
			
			Stage stage = (Stage)confirmButton.getScene().getWindow();
			stage.close();
		}
		
	}
}
