package com.g52grp.views;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
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
	private SimpleLongProperty barcode;
	
	public DisplayableJobProduct(int productId, String productCode,
			String description, float price, String quantity,
			int stocksRemaining, long barcode) {
		this.productId = new SimpleIntegerProperty(productId);
		this.productCode = new SimpleStringProperty(productCode);
		this.description = new SimpleStringProperty(description);
		this.price = new SimpleFloatProperty(price);
		this.quantity = new SimpleStringProperty(quantity);
		this.stocksRemaining = new SimpleIntegerProperty(stocksRemaining);
		this.barcode = new SimpleLongProperty(barcode);
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
	}

	public String getQuantity() {
		return quantity.get();
	}

	public void setQuantity(String quantity) {
		this.quantity = new SimpleStringProperty(quantity);
	}

	public int getStocksRemaining() {
		return stocksRemaining.get();
	}
	

	public void setStocksRemaining(int stocksRemaining) {
		this.stocksRemaining = new SimpleIntegerProperty(stocksRemaining);
	}
	
	public long getBarcode() {
		return barcode.get();
	}
	
	public void setBarcode(long barcode) {
		this.barcode = new SimpleLongProperty(barcode);
	}
}
