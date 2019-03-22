package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.DataManager;
import model.Item;

public class AddItemStageController {

	@FXML CheckBox successButton;
	@FXML TextField nameTextField;
	@FXML TextField quantityTextField;
	@FXML Button confirmButton;

	@FXML private void initialize() {
		nameTextField.setDisable(true);
		quantityTextField.setDisable(true);
	}

	@FXML private void successfullyAction() {
		nameTextField.setDisable(false);
		quantityTextField.setDisable(false);
	}
	
	@FXML private void onConfirmButtonclicked() {

		String name = nameTextField.getText();
		String value = quantityTextField.getText();
		//String name = item.getName();
		if(DataManager.items.containsKey(name)) {
			value = String.valueOf(Integer.parseInt(DataManager.items.get(name)) + Integer.parseInt(value));
		}
		
		DataManager.items.put(name, value);
		//jobItems = jobItems + name + ": " + String.valueOf(quantity) + ", ";
		
		DataManager.itemDetails.clear();
		for(String item : DataManager.items.keySet()) {
			DataManager.itemDetails.add(new Item(item, DataManager.items.get(item)));
		}
		DataManager.itemTable.setItems(DataManager.itemDetails);
		
		Stage stage = (Stage)confirmButton.getScene().getWindow();
	    stage.close();	
	}
	

}
