package controller;

import java.util.HashMap;

import GUI.DataManager;
import GUI.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

public class StockInStageController {
	@FXML private Button scannerButton;
	@FXML private Button confirmButton;
	@FXML private TableView<Item> inTable;
	@FXML private TableColumn<Item, String> itemCol;
	@FXML private TableColumn<Item, String> quantityCol;
	private int countScanner = 0;
	private static ObservableList<Item> inItems =  FXCollections.observableArrayList();
	
	@FXML public void initialize() {				
		itemCol.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
		quantityCol.setCellValueFactory(new PropertyValueFactory<Item, String>("quantity"));
		inItems.removeAll(inItems);
		inTable.setItems(inItems);		
		quantityCol.setCellFactory(TextFieldTableCell.forTableColumn());	
	}
	
	@FXML private void scannerClicked() {
		countScanner++;
		String item = "item" + countScanner;
		inItems.add(new Item(item, Integer.toString(1)));			
	}
	
	@FXML private void confirmClicked() {
		String newJobItems = new String();
		for(int i = 0; i < countScanner; i++) {
			String item = itemCol.getCellObservableValue(i).getValue();
			String quantity = quantityCol.getCellObservableValue(i).getValue();
			
			System.out.println(quantity);
			if(DataManager.items.containsKey(item)) {
				int value = Integer.parseInt(DataManager.items.get(item)) + Integer.parseInt(quantity);
				DataManager.items.put(item, String.valueOf(value));			
			}else {
				DataManager.items.put(item, quantity);
			}
		}
		
		for(String b :  DataManager.job.getItems().split(",\\s+")) {
			boolean isWritten = false;
			for(int i = 0; i < countScanner; i++) {
				String item = itemCol.getCellObservableValue(i).getValue();
				String quantity = quantityCol.getCellObservableValue(i).getValue();
				if(b.split(":\\s+")[0].equals(item)) {
					int n = Integer.parseInt(b.split(":\\s+")[1]) - Integer.parseInt(quantity);
					newJobItems = newJobItems + b.split(":\\s+")[0] + ": " + String.valueOf(n) + ", ";
					isWritten = true;
				}
				if(i == countScanner - 1 && !isWritten) {
					newJobItems = newJobItems + b;
				}
			}
		}
		
		DataManager.job.setItems(newJobItems);		
		DataManager.jobTable.refresh();
		Stage stage = (Stage)confirmButton.getScene().getWindow();
	    stage.close();
	}	
}
