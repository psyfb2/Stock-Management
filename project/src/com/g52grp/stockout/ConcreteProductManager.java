package com.g52grp.stockout;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.g52grp.database.DatabaseConnection;
import com.g52grp.database.JobProduct;
import com.g52grp.database.Product;

/**
 * @author psyfb2
 */
public class ConcreteProductManager implements ProductManager {
	private DatabaseConnection con;
	
	public ConcreteProductManager(DatabaseConnection con) {
		this.con = con;
	}

	@Override
	public JobProduct[] getProductsFromJobId(int jobId) {
		ArrayList<JobProduct> jobProducts = new ArrayList<JobProduct>();
		try {
			PreparedStatement ps = con.getPreparedStatement("select * FROM JobStockLink, Stocks WHERE JobStockLink.productID = Stocks.productID AND JobStockLink.jobID = ?");
			ps.setInt(1, jobId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Product p = getProduct(rs);
				jobProducts.add(new JobProduct(jobId, p, rs.getInt("quantityUsed")));
			}
			ps.close();
			JobProduct[] jobProductsArr = new JobProduct[jobProducts.size()];
			jobProductsArr = jobProducts.toArray(jobProductsArr);
			return jobProductsArr;
		} catch(SQLException e) {
			return null;
		}
	}

	@Override
	public Product[] getAllProducts() {
		ArrayList<Product> products = new ArrayList<Product>();
		try {
			PreparedStatement ps = con.getPreparedStatement("SELECT * FROM Stocks");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				products.add(getProduct(rs));
			}
			ps.close();
			Product[] productsArr = new Product[products.size()];
			productsArr = products.toArray(productsArr);
			return productsArr;
		} catch(SQLException e) {
			return null;
		}
	}
	
	@Override
	public Product[] searchProductsByProductcode(String productcode) {
		return searchProducts("productCode", productcode);
	}

	@Override
	public Product[] searchProductsByDescription(String description) {
		return searchProducts("description", description);
	}

	@Override
	public boolean decreaseStocks(JobProduct[] productsScannedOut) {
		for(JobProduct productScannedOut : productsScannedOut) {
			int productID = productScannedOut.getProduct().getProductId();
			int quantityUsed = productScannedOut.getQuantityUsed();
			int jobID = productScannedOut.getJobId();
			// reduce stock by quantity used for each product
			PreparedStatement ps;
			try {
				// if there exists previous quantity used then 
				// stock = stock - (new quantity used - old quantity used) 
				// else stock = stock - new quantity used
				ps = con.getPreparedStatement("UPDATE Stocks SET stock = CASE WHEN EXISTS(SELECT * FROM JobStockLink WHERE productID = ? and jobID = ?) THEN stock - (? - (SELECT quantityUsed FROM JobStockLink WHERE productID = ? AND jobID = ?)) ELSE stock - ? END WHERE productID = ?");
				ps.setInt(1, productID);
				ps.setInt(2, jobID);
				ps.setInt(3, quantityUsed);
				ps.setInt(4, productID);
				ps.setInt(5, jobID);
				ps.setInt(6, quantityUsed);
				ps.setInt(7, productID);
				ps.executeUpdate();
				ps.close();
				
				// update JobStockLink table to connect products scanned out to jobs
				ps = con.getPreparedStatement("INSERT INTO JobStockLink (jobID, productID, quantityUsed) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE quantityUsed = ?");
				ps.setInt(1, jobID);
				ps.setInt(2, productID);
				ps.setInt(3, quantityUsed);
				ps.setInt(4, quantityUsed);
				ps.executeUpdate();
				ps.close();
			} catch (SQLException e) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean decreaseStocks(JobProduct productScannedOut) {
		JobProduct[] singleElementArr = {productScannedOut};
		return decreaseStocks(singleElementArr);
	}
	
	private Product[] searchProducts(String columnName, String val) {
		ArrayList<Product> products = new ArrayList<Product>();
		try {
			PreparedStatement ps = con.getPreparedStatement("SELECT * FROM Stocks WHERE UPPER(?) LIKE UPPER('%?%')'");
			ps.setString(1, columnName);
			ps.setString(1, val);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				products.add(getProduct(rs));
			}
			ps.close();
			Product[] productsArr = new Product[products.size()];
			productsArr = products.toArray(productsArr);
			return productsArr;
		} catch(SQLException e) {
			return null;
		}
	}
	
	private Product getProduct(ResultSet rs) throws SQLException {
		return new Product(rs.getInt("productID"), rs.getString("productCode"), rs.getString("description"), rs.getInt("bayNumber"), 
				rs.getInt("rowNumber"), rs.getFloat("pricePerUnit"), rs.getInt("Stock"), rs.getLong("barCode"));
	}
}
