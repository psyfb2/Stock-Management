package com.g52grp.controllers;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 * 
 * @author psyys4
 *
 */
public class DisplayableProduct {
	private SimpleIntegerProperty productId;
	private SimpleStringProperty productCode;
	private SimpleStringProperty description;
	private SimpleStringProperty barCode;
	private SimpleFloatProperty pricePerUnit;
	private SimpleIntegerProperty quantity;
	private SimpleStringProperty minQuantity;
	private SimpleBooleanProperty delete;
	
	public DisplayableProduct(int productId, String productCode,
			String description, String barCode, float pricePerUnit, 
			int quantity, String minQuantity) {
		this.productId = new SimpleIntegerProperty(productId);
		this.productCode = new SimpleStringProperty(productCode);
		this.description = new SimpleStringProperty(description);
		this.barCode = new SimpleStringProperty(barCode);
		this.pricePerUnit = new SimpleFloatProperty(pricePerUnit);
		this.quantity = new SimpleIntegerProperty(quantity);
		this.minQuantity = new SimpleStringProperty(minQuantity);	
		this.delete = new SimpleBooleanProperty();
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

	public float getPricePerUnit() {
		return pricePerUnit.get();
	}

	public void setPrice(float pricePerUnit) {
		this.pricePerUnit = new SimpleFloatProperty(pricePerUnit);
	}

	public int getQuantity() {
		return quantity.get();
	}

	public void setQuantity(int quantity) {
		this.quantity = new SimpleIntegerProperty(quantity);
	}

	public String getMinQuantity() {
		return minQuantity.get();
	}
	
	public void setMinQuantity(String minQuantity) {
		this.minQuantity = new SimpleStringProperty(minQuantity);
	}
	
	public String getBarCode() {
		return barCode.get();
	}
	
	public void setBarcode(String barCode) {
		this.barCode = new SimpleStringProperty(barCode);
	}
	
	public boolean getDelete() {
		return delete.get();
	}
	
	public SimpleBooleanProperty deleteProperty() {
		return delete;
	}
	
	public void setDelete(boolean delete) {
		this.delete.set(delete);
	}
}
