package com.g52grp.controllers;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author psyfb2
 * JobProduct contains a product object (which cannot be displayed within the TableView)
 * so displayableJobProduct is a flat version which only contains Strings, Integer etc
 * also here we use SimpleStringProperty, SimpleIntegerProperty etc to allow columns to be editable
 */
public class DisplayableJobProduct {
	private SimpleIntegerProperty productId;
	private SimpleStringProperty productCode;
	private SimpleStringProperty description;
	private SimpleFloatProperty price;
	private SimpleStringProperty quantity;
	private SimpleIntegerProperty stocksRemaining;
	private SimpleStringProperty barcode;
	private SimpleIntegerProperty minQuantity;
	private SimpleFloatProperty priceOfRow;
	
	public DisplayableJobProduct(int productId, String productCode,
			String description, float price, String quantity,
			int stocksRemaining, String barcode, int minQuantity) {
		this.productId = new SimpleIntegerProperty(productId);
		this.productCode = new SimpleStringProperty(productCode);
		this.description = new SimpleStringProperty(description);
		this.price = new SimpleFloatProperty(price);
		this.quantity = new SimpleStringProperty(quantity);
		this.stocksRemaining = new SimpleIntegerProperty(stocksRemaining);
		this.barcode = new SimpleStringProperty(barcode);
		this.minQuantity = new SimpleIntegerProperty(minQuantity);
		this.priceOfRow = new SimpleFloatProperty(Integer.parseInt(quantity) * price);
	}

	public int getProductId() {
		return productId.get();
	}

	public void setProductId(int productId) {
		this.productId = new SimpleIntegerProperty(productId);
	}

	public String getProductCode() {
		return productCode.get();
	}

	public void setProductCode(String productCode) {
		this.productCode = new SimpleStringProperty(productCode);
	}

	public String getDescription() {
		return description.get();
	}

	public void setDescription(String description) {
		this.description = new SimpleStringProperty(description);
	}

	public float getPrice() {
		return price.get();
	}

	public void setPrice(float price) {
		this.price = new SimpleFloatProperty(price);
		setPriceOfRow(Integer.parseInt(quantity.get()) * price);
	}

	public String getQuantity() {
		return quantity.get();
	}

	public void setQuantity(String quantity) {
		this.quantity = new SimpleStringProperty(quantity);
		setPriceOfRow(Integer.parseInt(quantity) * price.get());
	}

	public int getStocksRemaining() {
		return stocksRemaining.get();
	}
	

	public void setStocksRemaining(int stocksRemaining) {
		this.stocksRemaining = new SimpleIntegerProperty(stocksRemaining);
	}
	
	public String getBarcode() {
		return barcode.get();
	}
	
	public void setBarcode(String barcode) {
		this.barcode = new SimpleStringProperty(barcode);
	}
	
	public int getMinQuantity() {
		return minQuantity.get();
	}
	
	public void setMinQuantity(int minQuantity) {
		this.minQuantity = new SimpleIntegerProperty(minQuantity);
	}
	
	public float getPriceOfRow() {
		return priceOfRow.get();
	}

	public void setPriceOfRow(float priceOfRow) {
		this.priceOfRow = new SimpleFloatProperty(priceOfRow);
	}

}
