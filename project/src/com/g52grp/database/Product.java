package com.g52grp.database;

/**
 * @author psyfb2
 * Represents product within Stocks table
 */
public class Product {
	private int productId; // unique PRIMARY KEY
	private String productCode;
	private String description; // product code and description unique
	private float pricePerUnit;
	private int stock;
	private String barCode; // unique
	
	public Product(int productId, String productCode, String description,
			float pricePerUnit, int stock, String barCode) {
		this.productId = productId;
		this.productCode = productCode;
		this.description = description;
		this.pricePerUnit = pricePerUnit;
		this.stock = stock;
		this.barCode = barCode;
	}

	public int getProductId() {
		return productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public String getDescription() {
		return description;
	}

	public float getPricePerUnit() {
		return pricePerUnit;
	}

	public int getStock() {
		return stock;
	}

	public String getBarCode() {
		return barCode;
	}
	
	@Override
	public String toString() {
		// toString required for auto complete search for a product
		return getProductCode() + " " + getDescription();
	}
}
