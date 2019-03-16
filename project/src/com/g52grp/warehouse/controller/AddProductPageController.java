package com.g52grp.warehouse.controller;

import com.g52grp.main.Main;
import com.g52grp.stockout.ConcreteProductManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
	
	private ConcreteProductManager pm = new ConcreteProductManager(Main.con);
	
	@FXML void confirmButtonClicked() {
		String code = productCode.getText();
		String des = description.getText();
		String bar = barcode.getText();

		if( code.length() != 0 && des.length() != 0) {
			//check the product already exits in the database
			if(pm.getProductFromProductcodeAndDescription(code, des) != null) {			
				errorMessage.setText(des + " with " + code + " already exits in the warehouse.");
			}else {
				if(bar.length() == 0) {
					bar = null;
				}				
				if(pm.addNewProduct(code, des, bar)) {					
					Stage stage = (Stage)cancelButton.getScene().getWindow();
					stage.close();
				}else {
					errorMessage.setText("Failed to add the product: error accessing database");
				}
			}
		}else {
			errorMessage.setText("Please enter both the code and description.");
		}		
	}
	
	@FXML void cancelButtonClicked() {
		Stage stage = (Stage)cancelButton.getScene().getWindow();
		stage.close();
	}
}
