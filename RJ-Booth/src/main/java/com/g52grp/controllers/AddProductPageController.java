package com.g52grp.controllers;

import com.g52grp.backend.ConcreteProductManager;
import com.g52grp.main.Main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for add product modal
 * @author psyys4
 */
public class AddProductPageController {
	
	@FXML
	private TextField productCode;
	
	@FXML
	private TextField description;
	
	@FXML
	private TextField barcode;
	
	@FXML
	private Button cancelButton;
	
	@FXML
	private Button confrimButton;
	
	@FXML
	private Label errorMessage;
	
	@FXML
	private TextField price;
	
	private ConcreteProductManager pm = new ConcreteProductManager(Main.con);
	TableViewUpdate tb; // used to update the table after a product is added
	
	/**
	 * Pass data to this controller using this method
	 * @param tb object with an updateTableView method to update the table view
	 */
	public void initData(TableViewUpdate tb) {
		this.tb = tb;
	}
	
	/**
	 * Adds a new product to the database, called when the confirm button is clicked
	 */
	@FXML void confirmButtonClicked() {
		errorMessage.setText("");
		String code = productCode.getText();
		String des = description.getText();
		String bar = barcode.getText();
		String pri = price.getText();
		
		if(code.length() > 255) {
			errorMessage.setText("Error: Product Code too long");
			return;
		}
		
		if(des.length() > 255) {
			errorMessage.setText("Error: Description too long");
			return;
		}

		if(bar.length() > 128) {
			errorMessage.setText("Error: Barcode too long");
			return;
		}
		
		//check if the user enter all the required fields
		if(code.length() == 0 || des.length() == 0 || pri.length() == 0) {
			errorMessage.setText("Please enter product code, description and price.");
			return;
		}
		
		//check if the product already exits in the database
		if(pm.getProductFromProductcodeAndDescription(code, des) != null) {			
			errorMessage.setText(des + " (" + code + ") already exits in the warehouse.");
			return;
		}
		
		//check if the barcode is illegal
		if(bar.length() != 0) {
			if(!bar.matches("[0-9]{1,}")) {			
				errorMessage.setText("Please only enter digits for barcode");
				return;
			}else if(bar.length() < 4){
				errorMessage.setText("Barcode must have at least 4 digits");
				return;
			}
		}else {
			bar = null;			
		}
		
		//check if the price is illegal
		float price;
		try {
			price = Float.parseFloat(pri);
			if(price < 0) {
				errorMessage.setText("Please enter a positive number for price");
				return;
			}
		} catch(NumberFormatException ex) {
			errorMessage.setText("Please enter a positive number for price");
			return;
		}
		
		if(pm.addNewProduct(code, des, bar, price)) {					
			Stage stage = (Stage)cancelButton.getScene().getWindow();
			stage.close();
		}else {
			errorMessage.setText("Failed to add the product: error accessing database");
		}
				
		if(tb != null) {
			tb.updateTableView();
		}
		
	}
	
	/**
	 * Close this window
	 */
	@FXML void cancelButtonClicked() {
		Stage stage = (Stage)cancelButton.getScene().getWindow();
		stage.close();
	}
}
