package com.g52grp.warehouse.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import org.controlsfx.control.textfield.TextFields;

import com.g52grp.database.Product;
import com.g52grp.stockout.ConcreteProductManager;
import com.g52grp.warehouse.model.Demo;
import com.g52grp.warehouse.model.DisplayableProduct;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class StockManagementPageController {

	@FXML private TableView<DisplayableProduct> stockTable;
	@FXML private TableColumn<DisplayableProduct, Integer>  idCol;
	@FXML private TableColumn<DisplayableProduct, String> codeCol;
	@FXML private TableColumn<DisplayableProduct, String> descriptionCol;
	@FXML private TableColumn<DisplayableProduct, Long> barcodeCol;
	@FXML private TableColumn<DisplayableProduct, Integer> bayNoCol;
	@FXML private TableColumn<DisplayableProduct, Integer> rowNoCol;
	@FXML private TableColumn<DisplayableProduct, Float> pricePerUnitCol;
	@FXML private TableColumn<DisplayableProduct, Integer> quantityCol;
	@FXML private TableColumn<DisplayableProduct, Integer> minQuantityCol;
	@FXML private TableColumn<DisplayableProduct, Boolean> deleteCol;
	
	@FXML private Label errorMessage;
	@FXML private Button deleteButton;
	@FXML private Button saveButton;
	@FXML private Text totalValue;
	@FXML private Text mostUsedProduct;
	@FXML private TextField searchProduct;	
	private static ObservableList<DisplayableProduct> stocks =  FXCollections.observableArrayList();
	private ConcreteProductManager pm = new ConcreteProductManager(Demo.con);
	
	@FXML public void initialize() {		
		idCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
		codeCol.setCellValueFactory(new PropertyValueFactory<>("productCode"));
		descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		barcodeCol.setCellValueFactory(new PropertyValueFactory<>("barCode"));
		bayNoCol.setCellValueFactory(new PropertyValueFactory<>("bayNumber"));
		rowNoCol.setCellValueFactory(new PropertyValueFactory<>("rowNumber"));
		pricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("pricePerUnit"));
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		minQuantityCol.setCellValueFactory(new PropertyValueFactory<>("minQuantity"));
		
		deleteCol.setCellFactory(CheckBoxTableCell.forTableColumn(deleteCol));
		deleteCol.setCellValueFactory(new PropertyValueFactory<>("delete"));
		deleteCol.setVisible(false);
		saveButton.setVisible(false);
		
		quantityCol.setCellFactory(new Callback<TableColumn<DisplayableProduct, Integer>, TableCell<DisplayableProduct, Integer>>() {
			@Override
			public TableCell<DisplayableProduct, Integer> call(TableColumn<DisplayableProduct, Integer> param) {
				return new TableCell<DisplayableProduct, Integer>(){
					@Override
					public void updateItem(Integer quantity, boolean empty) {
						super.updateItem(quantity, empty);
						if(!empty) {
							if(quantity < 5) {
								this.setStyle("-fx-background-color: red;");															
							}else {
								this.setStyle("-fx-background-color: null;");
							}
							setText(Integer.toString(quantity));
						}else {
							setText(null);
							this.setStyle("-fx-background-color: null;");
						}
					}
				};
			}			
		});
		
		showProducts();		
		showTotalValue();
		showMostUsedProduct();
		
		// auto complete text field
		ArrayList<Product> allProducts = pm.getAllProductsArrayList();		
		TextFields.bindAutoCompletion(searchProduct, input -> {
			if(input.getUserText().isEmpty() || allProducts == null) {
				return Collections.emptyList();
			}
			// search allProducts for a match with the input and return this list
			return allProducts.stream().filter(i -> {
				return i.toString().toLowerCase().contains(input.getUserText().toLowerCase());
			}).collect(Collectors.toList());
		});
	}
	
	@FXML private void deleteClicked() {
		deleteCol.setVisible(true);
		stockTable.setEditable(true);	
		saveButton.setVisible(true);
	}
	
	@FXML private void saveClicked() {
		for(DisplayableProduct product : stockTable.getItems()) {
			if(product.getDelete()) {
				if(!pm.deleteProduct(product.getProductId())) {
					errorMessage.setText("Failed to delete products: error accessing database");
					return;
				}
			}
		}
		
		showProducts();
		showTotalValue();
		saveButton.setVisible(false);
		deleteCol.setVisible(false);		
	}
	
	private void showProducts() {
		stocks.removeAll(stocks);
		ArrayList<Product> allProducts = pm.getAllProductsArrayList();
		if(allProducts == null) {
			errorMessage.setText("Failed to load products: error accessing database");
			return;
		}
		
		for(Product product : allProducts) {
			stocks.add(new DisplayableProduct(product.getProductId(), product.getProductCode(),
					product.getDescription(), product.getBarCode(), product.getBayNumber(), 
					product.getRowNumber(), product.getPricePerUnit(), product.getStock(), 5));
		}		
		stockTable.setItems(stocks);
	}
	
	private void showTotalValue() {
		double value = 0;
		for(DisplayableProduct product : stockTable.getItems()) {
			value += product.getPricePerUnit() * product.getQuantity();
		}
		totalValue.setText(Double.toString(value));
	}
		
	private void showMostUsedProduct() {
		String product = pm.getMostUsedProduct();
		if(product == null) {
			errorMessage.setText("Failed to load most used product: error accessing database");
			return;
		}
		mostUsedProduct.setText(product);
	}
}
