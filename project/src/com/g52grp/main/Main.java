package com.g52grp.main;

import com.g52grp.database.*;
import com.g52grp.stockout.*;

public class Main {
	
	public static void main(String[] args) {
		// only create ONE connection for the whole program (take DatabaseConnection object as a parameter for your classes)
		DatabaseConnection test = new DatabaseConnection();
		
		// connection always fails in uni for some reason, must be the damn firewall
		if (test.openConnection()) {
			ProductManager pm = new ConcreteProductManager(test);
			
			System.out.println("Successful Connection");
			/*
			Product p = new Product(1, "DetaDB171", "Lounge Plate Black Box", 0, 0, 1.0f, 10, 0);
			JobProduct[] productsScannedOut = {new JobProduct(1, p, 5)};
			pm.decreaseStocks(productsScannedOut);
			*/
			
			/*
			Product[] products = pm.getAllProducts();
			for(Product p : products) {
				System.out.println(p.getProductId() + " " + p.getDescription());
			}
			*/
			
			/*
			JobProduct[] jobProducts= pm.getProductsFromJobId(1);
			for(JobProduct p : jobProducts) {
				System.out.println(p.getJobId() + " " + p.getProduct().getProductId() + " " + p.getQuantityUsed());
			}
			*/
			
			JobManager jm = new ConcreteJobManager(test);
			/*
			jm.addNewJobToDb("Added from java", 10);
			*/
			

			Job[] jobs = jm.getAllJobs();
			for(Job j : jobs) {
				System.out.println(j.getJobId() + " " + j.getSiteName());
			}
			
			test.closeConnection();
		} else {
			System.out.println("Connection Failed");
		}
		
	}

}
