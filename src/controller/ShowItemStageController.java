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

public class ShowItemStageController {
	@FXML private Button scannerButton;
	@FXML private Button confirmButton;
	@FXML private TableView<Item> itemTable;
	@FXML private TableColumn<Item, String> itemCol;
	@FXML private TableColumn<Item, String> quantityCol;
	private static ObservableList<Item> allItems =  FXCollections.observableArrayList();
	
	@FXML public void initialize() {				
		itemCol.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
		quantityCol.setCellValueFactory(new PropertyValueFactory<Item, String>("quantity"));
		allItems.removeAll(allItems);
		for(String item : DataManager.items.keySet()) {
			allItems.add(new Item(item, DataManager.items.get(item)));
		}
		itemTable.setItems(allItems);		
		
	}
}
