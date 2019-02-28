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
	 *  which are registered for this job, null if couldn't if database could not be queried 
	 */
	JobProduct[] getProductsFromJobId(int jobId);
	
	/**
	 * @return All products within the database, null if couldn't if database could not be queried 
	 */
	Product[] getAllProducts();
	
	/**
	 * Searches the database to find products which contain the given product code (it's possible for multiple products to have the same product code)
	 * @param productcode Product code to search for
	 * @return Array of Product objects, null if the database could not be accessed 
	 */
	Product[] searchProductsByProductcode(String productcode);
	
	/**
	 * Searches the database to find products which contain the given description (it's possible for multiple products to have the same description)
	 * @param description Description to search for
	 * @return Array of Product objects, null if the database could not be accessed 
	 */
	Product[] searchProductsByDescription(String description);
		
	/**
	 * Reduce stock amount for each product scanned out by quantityUsed stored within JobProduct object, for the Product table
	 * And link these products which have been scanned to the job within the JobStockLink tables (if they are not already linked)
	 * NOTE: just pass the quantityUsed within JobProduct, this method will automatically account for the difference 
	 * between old quantity used and new quantity used when calculating the new value for remaining stock of a product
	 * @param productsScannedOut List of products which have been scanned out of the depot
	 * @return Whether this operation was successful or not (should only fail if the database is somehow unavailable)
	 */
	boolean decreaseStocks(JobProduct[] productsScannedOut);
	
	/**
	 * Same as above, except pass single product being scanned out, instead of array
	 * @param productScannedOut product which have been scanned out of the depot
	 * @return Whether this operation was successful or not (should only fail if the database is somehow unavailable)
	 */
	boolean decreaseStocks(JobProduct productScannedOut);
}
