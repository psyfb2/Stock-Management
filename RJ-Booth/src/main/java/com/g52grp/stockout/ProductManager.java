package com.g52grp.stockout;

import com.g52grp.database.Product;

import java.util.ArrayList;

import com.g52grp.database.JobProduct;

/**
 * Acts as interface for front end code for accessing products stored on MYSQL DB
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
	 * @return All products within the database, null if database could not be queried 
	 */
	Product[] getAllProducts();
	
	/**
	 * Productcode and Description are not part of the PRIMARY KEY for a Product, however both of these combined together must be unique (this is enforced within the database)
	 * @param productcode Product code of product to find
	 * @param description Description of product to find
	 * @return Product object for the single product with the same productcode and description, null if none were found or if database could not be queried
	 */
	Product getProductFromProductcodeAndDescription(String productcode, String description);
	
	/**
	 * @return All products within the database as an ArrayList, null if database could not be queried
	 */
	ArrayList<Product> getAllProductsArrayList();
		
	/**
	 * Searches the database to find products which contain the given description or product code
	 * This search is case insensitive
	 * @param description Description to search for
	 * @param productcode Product code to search for
	 * @return Array of Product objects which match the search criteria, null if the database could not be accessed
	 */
	Product[] searchProductsByDescriptionAndProductcode(String description, String productcode);
	
	/**
	 * Reduce stock amount for each product scanned out by quantityUsed stored within JobProduct object, for the Product table
	 * And link these products which have been scanned to the job within the JobStockLink tables (if they are not already linked)
	 * e.g. Product x within Job y, x has stock 15 and quantity used 10 within job y. Passing quantity used of 1 (within JobProduct Object)
	 * means x will now have stock 14 and quantity used 11 within the job.
	 * @param productsScannedOut List of products which have been scanned out of the depot
	 * @return Whether this operation was successful or not (should only fail if the database is somehow unavailable)
	 */
	boolean decreaseStocks(JobProduct[] productsScannedOut);
	
	/**
	 * Same as above, except pass single product being scanned out, instead of array
	 * NOTE: only the jobId, productId and quantityUsed within the JobProduct object are used (the rest of the variables are not important for this query)
	 * @param productScannedOut product which have been scanned out of the depot
	 * @return Whether this operation was successful or not (should only fail if the database is somehow unavailable)
	 */
	boolean decreaseStocks(JobProduct productScannedOut);
	
	/**
	 * Barcode is a unique column in the database, get all the information by a product from a given barcode
	 * @param barcode Barcode to search for
	 * @return corresponding Product, null if none were found or if database access failed
	 */
	Product getProductFromBarcode(String barcode);
	
	/**
	 * Get a product from a productID
	 * @param productId - unique ID of the product to search for
	 * @return corresponding Product, null if none were found or if database access failed
	 */
	Product getProductFromProductId(int productId);
	
	/**
	 * Similar to decreaseStocks(), however only register the product with the job with a quantity used of 1.
	 * If the job is already registered with the product then nothing is changed (decreaseStocks() would decrease the stock by 1 even if the job existed). 
	 * Also does check that there is enough stock unlike decreaseStocks() (i.e. if a product has 0 stock nothing will happen) 
	 * This should be called when a user manually selects product to check out for a job
	 * @param jobID jobID of the job which this product is being scanned out for
	 * @param productID productID of the product which is being scanned out
	 * @return whether this operation was successful or not (should only fail if the database is somehow unavailable or if there are 0 stocks remaining for this product)
	 */
	boolean addProductToJob(int jobID, int productID);
	
	/**
	 * Removes product from a job by removing the products entry in the JobStockLink table.
	 * Any quantity used registered for the given job and product will NOT be added back onto stocks column
	 * in Stocks table for the given product. (can do this by calling decreaseStocks())
	 * @param jobID jobID of the job the product is registered for
	 * @param productID productID of the product to remove
	 * @return whether this operation was successful or not (should only fail if the database is somehow unavailable or if there are 0 stocks remaining for this product)
	 */
	boolean removeProductFromJob(int jobID, int productID);
	
	/**
	 * Returns whether a product is linked to a given job
	 * @param jobID jobID of the job to check for
	 * @param productID productID of the job to check for
	 * @return true if this product is registered to the job else false
	 */
	boolean isProductRegisteredWithJob(int jobID, int productID);
	
	/**
	 * Delete the product in the database given a productID
	 * @param productID ID of product to delete in the MYSQL database
	 * @return true if the delete was executed, false if there was an error accessing the database.
	 */
	public boolean deleteProduct(int productID);
	
	/**
	 * @return Description of most used product
	 */
	public String getMostUsedProduct();
	
	/**
	 * Add a new product to the database
	 * @param code productCode of the product to add
	 * @param description description of the product to add
	 * @param barCode unique barcode of the product to add
	 * @return success of operation
	 */
	public boolean addNewProduct(String code, String description, String barCode, int pricePerUnit);
	
	/**
	 * Update minimum quantity of a product
	 * @param id 
	 * @param newMinQuantity
	 * @return success of operation
	 */
	public boolean updateMinQuantity(int id, int newMinQuantity);
	
	/**
	 * Update barcode of a product
	 * @param id
	 * @param newBarcode
	 * @return success of operation
	 */
	public boolean updateBarcode(int id, String newBarcode);
}
