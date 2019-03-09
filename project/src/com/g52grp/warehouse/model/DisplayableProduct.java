package com.g52grp.warehouse.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
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
	private SimpleLongProperty barCode;
	private SimpleIntegerProperty bayNumber;
	private SimpleIntegerProperty rowNumber;
	private SimpleFloatProperty pricePerUnit;
	private SimpleIntegerProperty quantity;
	private SimpleIntegerProperty minQuantity;
	private SimpleBooleanProperty delete;
	
	/**
	 * 
	 * @param productId
	 * @param productCode
	 * @param description
	 * @param barCode
	 * @param bayNumber
	 * @param rowNumber
	 * @param pricePerUnit
	 * @param quantity
	 * @param minQuantity
	 * 
	 * Setting stock table parameter
	 */
	public DisplayableProduct(int productId, String productCode,
			String description, long barCode, int bayNumber,
			int rowNumber, float pricePerUnit, int quantity,
			int minQuantity) {
		this.productId = new SimpleIntegerProperty(productId);
		this.productCode = new SimpleStringProperty(productCode);
		this.description = new SimpleStringProperty(description);
		this.barCode = new SimpleLongProperty(barCode);
		this.bayNumber = new SimpleIntegerProperty(bayNumber);
		this.rowNumber = new SimpleIntegerProperty(rowNumber);
		this.pricePerUnit = new SimpleFloatProperty(pricePerUnit);
		this.quantity = new SimpleIntegerProperty(quantity);
		this.minQuantity = new SimpleIntegerProperty(minQuantity);	
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

	public int getMinQuantity() {
		return minQuantity.get();
	}
	

	public void setMinQuantity(int minQuantity) {
		this.minQuantity = new SimpleIntegerProperty(minQuantity);
	}
	
	public long getBarCode() {
		return barCode.get();
	}
	
	public void setBarcode(long barCode) {
		this.barCode = new SimpleLongProperty(barCode);
	}
	
	public int getRowNumber() {
		return rowNumber.get();
	}
	
	public void setRowNumber(int rowNumber) {
		this.rowNumber = new SimpleIntegerProperty(rowNumber);
	}
	
	public int getBayNumber() {
		return bayNumber.get();
	}
	
	public void setBayNumber(int bayNumber) {
		this.bayNumber = new SimpleIntegerProperty(bayNumber);
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
