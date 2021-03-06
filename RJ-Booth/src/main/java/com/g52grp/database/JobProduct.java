package com.g52grp.database;

/**
 * Represents JobStockLink table entry within the database
 * @author psyfb2
 */
public class JobProduct {
	private int jobId;
	private Product product;
	private int quantityUsed;
	
	public JobProduct(int jobId, Product product, int quantityUsed) {
		this.jobId = jobId;
		this.product = product;
		this.quantityUsed = quantityUsed;
	}
	
	public int getJobId() {
		return jobId;
	}
	public Product getProduct() {
		return product;
	}
	
	public int getQuantityUsed() {
		return quantityUsed;
	}
	
	
}
