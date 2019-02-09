package com.g52grp.stockout;

import com.g52grp.database.JobProduct;
import com.g52grp.database.Product;

public class ConcreteProductManager implements ProductManager {

	public ConcreteProductManager() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public JobProduct[] getProductsFromJobId(int jobId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product[] getAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean decreaseStocks(JobProduct[] productsScannedOut) {
		// TODO Auto-generated method stub
		return false;
	}

}
