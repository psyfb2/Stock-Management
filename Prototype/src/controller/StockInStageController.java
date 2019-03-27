package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.DataManager;
import model.Item;

public class StockInStageController {
	@FXML private Button scannerButton;
	@FXML private Button confirmButton;
	@FXML private TableView<Item> inTable;
	@FXML private TableColumn<Item, String> itemCol;
	@FXML private TableColumn<Item, TextField> quantityCol;
	private int countScanner = 0;
	private static ObservableList<Item> inItems =  FXCollections.observableArrayList();
	
	@FXML public void initialize() {			
		//DataManager.jobTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		//DataManager.jobTable.getSelectionModel()
		itemCol.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
		quantityCol.setCellValueFactory(cellData -> cellData.getValue().getMyTextField().getTextField());
		inItems.removeAll(inItems);
		inTable.setItems(inItems);			
	}
	
	@FXML private void scannerClicked() {
		countScanner++;
		String item = "item" + countScanner;
		inItems.add(new Item(item, 1));			
	}
	
	@FXML private void confirmClicked() {
		String newJobItems = new String();
		
		for(Item item : inTable.getItems()) {
			String name = item.getName();
			String quantity = item.getMyTextField().getText();
			if(DataManager.items.containsKey(name)) {
				int value = Integer.parseInt(DataManager.items.get(name)) + Integer.parseInt(quantity);
				DataManager.items.put(name, String.valueOf(value));			
			}else {
				DataManager.items.put(name, quantity);
			}
		}
		
		for(String b :  DataManager.job.getItems().split(",\\s+")) {
			boolean isWritten = false;
			for(Item item : inTable.getItems()) {
				String name = item.getName();
				String quantity = item.getMyTextField().getText();
				if(b.split(":\\s+")[0].equals(name)) {
					int n = Integer.parseInt(b.split(":\\s+")[1]) - Integer.parseInt(quantity);
					if(n != 0) {
						newJobItems = newJobItems + b.split(":\\s+")[0] + ": " + String.valueOf(n) + ", ";
					}
					isWritten = true;
				}
			}
			if(!isWritten) {
				newJobItems = newJobItems + b;
			}
		}
		
		DataManager.job.setItems(newJobItems);		
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
