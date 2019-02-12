package com.g52grp.stockout;

import com.g52grp.database.Product;
import com.g52grp.database.JobProduct;

/**
 * @author psyfb2
 */
public interface ProductManager {
	/**
	 * @param jobId jobID within the database of the job to look for
	 * @return All products and there quantity used for the job
	 *  which are registered for this job
	 */
	JobProduct[] getProductsFromJobId(int jobId);
	
	/**
	 * @return All products within the database
	 */
	Product[] getAllProducts();
	
	/**
	 * Reduce stock amount for each product scanned out by quantityUsed stored within JobProduct object, for the Product table
	 * And link these products which have been scanned to the job within the JobStockLink table
	 * @param productsScannedOut List of products which have been scanned out of the depot
	 * @return Whether this operation was successful or not (should only fail if the database is somehow unavailable)
	 */
	boolean decreaseStocks(JobProduct[] productsScannedOut);
}
