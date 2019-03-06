package com.g52grp.database;

/**
 * @author psyfb2
 * Represents product within Stocks table
 */
public class Product {
	private int productId;
	private String productCode;
	private String description;
	private int bayNumber;
	private int rowNumber;
	private float pricePerUnit;
	private int stock;
	private long barCode;
	
	public Product(int productId, String productCode, String description, int bayNumber, int rowNumber,
			float pricePerUnit, int stock, long barCode) {
		this.productId = productId;
		this.productCode = productCode;
		this.description = description;
		this.bayNumber = bayNumber;
		this.rowNumber = rowNumber;
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

	public int getBayNumber() {
		return bayNumber;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public float getPricePerUnit() {
		return pricePerUnit;
	}

	public int getStock() {
		return stock;
	}

	public long getBarCode() {
		return barCode;
	}
	
	
	@Override
	public String toString() {
		// toString required for auto complete search for a product
		return getProductCode() + " " + getDescription();
	}
}
