package com.g52grp.warehouse.controller;

import java.awt.Toolkit;

import com.g52grp.main.Main;
import com.g52grp.stockout.ConcreteProductManager;
import com.g52grp.views.TableViewUpdate;

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
	
	@FXML
	private TextField price;
	
	private ConcreteProductManager pm = new ConcreteProductManager(Main.con);
	TableViewUpdate tb; // used to update the table after a product is added
	
	public void initData(TableViewUpdate tb) {
		this.tb = tb;
	}
	
	@FXML void confirmButtonClicked() {
		errorMessage.setText("");
		String code = productCode.getText();
		String des = description.getText();
		String bar = barcode.getText();
		
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
		
		// wtf is this spaghetti code....seriously guys
		
		if( code.length() != 0 && des.length() != 0) {
			//check the product already exits in the database
			if(pm.getProductFromProductcodeAndDescription(code, des) != null) {			
				//errorMessage.setText(des + " with " + code + " already exits in the warehouse.");
				if(Toolkit.getDefaultToolkit().getScreenSize().getWidth()  >1700) {
					errorMessage.setText("Product " + code +  " (" + des + ") already exits in the warehouse.");	  
				}else {
					//errorMessage.setPrefHeight(222);
					errorMessage.setText(des + " with " + code + " already exits in the warehouse.");	
				}
			}else {
				//Check if barcode is illegal.
				if(!bar.matches("[0-9]{1,}") && bar.length() != 0) {
			
					errorMessage.setText("Please only enter digits for barcode");
				}else if(bar.length() < 4 && bar.length() != 0){
					errorMessage.setText("Barcode must have at least 4 digits");
				}else {
					if(bar.length() == 0) {
						bar = null;
					}
					float pri;
					try {
						pri = Float.parseFloat(price.getText());
					} catch(NumberFormatException ex) {
						errorMessage.setText("Please enter a number for Price");
						return;
					}
					if(pm.addNewProduct(code, des, bar, pri)) {					
						Stage stage = (Stage)cancelButton.getScene().getWindow();
						stage.close();
					}else {
						errorMessage.setText("Failed to add the product: error accessing database");
					}
				}				
			}
		}else {
			errorMessage.setText("Please enter both the code and description.");
		}
		
		if(tb != null) {
			tb.updateTableView();
		}
		
	}
	
	@FXML void cancelButtonClicked() {
		Stage stage = (Stage)cancelButton.getScene().getWindow();
		stage.close();
	}
}
