package controller;

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

public class StockOutStageController {
	@FXML private Button scannerButton;
	@FXML private Button confirmButton;
	@FXML private TableView<Item> outTable;
	@FXML private TableColumn<Item, String> itemCol;
	@FXML private TableColumn<Item, String> quantityCol;
	private int countScanner = 0;
	private static ObservableList<Item> outItems =  FXCollections.observableArrayList();
	
	@FXML public void initialize() {				
		itemCol.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
		quantityCol.setCellValueFactory(new PropertyValueFactory<Item, String>("quantity"));
		outItems.removeAll(outItems);
		outTable.setItems(outItems);		
		quantityCol.setCellFactory(TextFieldTableCell.forTableColumn());	
	}
	
	@FXML private void scannerClicked() {
		countScanner++;
		String item = "item" + countScanner;
		outItems.add(new Item(item, Integer.toString(1)));			
	}
	
	@FXML private void confirmClicked() {
		String jobItems = new String();
		for(int i = 0; i < countScanner; i++) {
			String item = itemCol.getCellObservableValue(i).getValue();
			String quantity = quantityCol.getCellObservableValue(i).getValue();
			System.out.println(quantity);
			if(DataManager.items.containsKey(item)) {
				int value = Integer.parseInt(DataManager.items.get(item)) - Integer.parseInt(quantity);
				DataManager.items.put(item, String.valueOf(value));
			}else {
				DataManager.items.put(item, quantity);
			}
			jobItems = jobItems + item + ": " + String.valueOf(quantity) + ", ";
		}
		
		if(DataManager.job.getItems() == null) {
			DataManager.job.setItems(jobItems);
		}else {
			DataManager.job.setItems(DataManager.job.getItems() + jobItems);
		}
		
		DataManager.jobTable.refresh();
		Stage stage = (Stage)confirmButton.getScene().getWindow();
	    stage.close();
	}
}