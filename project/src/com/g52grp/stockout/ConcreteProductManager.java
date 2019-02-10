package com.g52grp.stockout;

import com.g52grp.database.DatabaseConnection;
import com.g52grp.database.JobProduct;
import com.g52grp.database.Product;

/**
 * @author psyfb2, lily
 */
public class ConcreteProductManager implements ProductManager {
	private DatabaseConnection con;
	
	public ConcreteProductManager(DatabaseConnection con) {
		this.con = con;
	}

	@Override
	public JobProduct[] getProductsFromJobId(int jobId) {
		return null;
	}

	@Override
	public Product[] getAllProducts() {
		return null;
	}

	@Override
	public boolean decreaseStocks(JobProduct[] productsScannedOut) {
		for(JobProduct productScannedOut : productsScannedOut) {
			int productID = productScannedOut.getProduct().getProductId();
			int quantityUsed = productScannedOut.getQuantityUsed();
			int jobID = productScannedOut.getJobId();
			// reduce stock by quantity used for each product
			if(!con.sendUpdate("UPDATE Stocks SET stock = stock - " + quantityUsed + " WHERE productID = " + productID)) {
				return false;
			}
			// now either add or update rows to JobStockLink to link productsScannedOut with jobs 
			if(!con.sendUpdate("INSERT INTO JobStockLink (jobID, productID, quantityUsed) VALUES (" + jobID +", " + productID + ", " + quantityUsed + " ) ON DUPLICATE KEY UPDATE quantityUsed = " + quantityUsed)) {
				return false;
			}
		}
		return true;
	}
}
