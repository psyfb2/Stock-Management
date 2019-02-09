package com.g52grp.stockout;

import com.g52grp.database.Product;
import com.g52grp.database.JobProduct;

/**
 * @author psyfb2 - LILLY AND ME IMPLEMENT THESE METHODS IN ONE CLASS
 * REMINDER FOR ME: ALTER JobStockLink table to contain extra field quantityUsed
 */
public interface ProductManager {
	/**
	 * --- LILY IMPLEMENT THIS METHOD ---
	 * @param jobId jobID within the database of the job to look for
	 * @return All products and there quantity used for the job
	 *  which are registered for this job
	 */
	JobProduct[] getProductsFromJobId(int jobId);
	
	/**
	 * --- LILY IMPLEMENT THIS METHOD  ---
	 * @return All products within the database
	 */
	Product[] getAllProducts();
	
	/**
	 * --- I IMPLEMENT THIS METHOD ---
	 * Reduce stock amount for each product scanned out, within the Product table
	 * And link these products which have been scanned to the job within the JobStockLink table
	 * @param productsScannedOut List of products which have been scanned out of the depot
	 * @return Whether this operation was successful or not
	 */
	boolean decreaseStocks(JobProduct[] productsScannedOut);
}
