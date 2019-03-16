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
			PreparedStatement ps = con.getPreparedStatement("SELECT * FROM JobStockLink, Stocks WHERE JobStockLink.productID = Stocks.productID AND JobStockLink.jobID = ?");
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
	public Product getProductFromProductcodeAndDescription(String productcode, String description) {
		try {
			PreparedStatement ps = con.getPreparedStatement("SELECT * FROM Stocks WHERE productCode = ? AND description = ?");
			ps.setString(1, productcode);
			ps.setString(2, description);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				Product p = getProduct(rs);
				ps.close();
				return p;
			} else {
				ps.close();
				return null; // product was not found
			}
		} catch(SQLException e) {
			return null;
		}
	}

	
	@Override
	public ArrayList<Product> getAllProductsArrayList() {
		ArrayList<Product> products = new ArrayList<Product>();
		try {
			PreparedStatement ps = con.getPreparedStatement("SELECT * FROM Stocks");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				products.add(getProduct(rs));
			}
			ps.close();
			return products;
		} catch(SQLException e) {
			return null;
		}
	}

	@Override
	public Product[] getAllProducts() {
		ArrayList<Product> products = getAllProductsArrayList();
		Product[] productsArr = new Product[products.size()];
		productsArr = products.toArray(productsArr);
		return productsArr;
	}
	
	@Override
	public Product[] searchProductsByDescriptionAndProductcode(String description, String productcode) {
		ArrayList<Product> products = new ArrayList<Product>();
		try {
			PreparedStatement ps;
			ps = con.getPreparedStatement("SELECT * FROM Stocks WHERE description LIKE ? OR productCode LIKE ?");

			ps.setString(1, "%" + description + "%");
			ps.setString(2, "%" + productcode + "%");
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
	public boolean decreaseStocks(JobProduct[] productsScannedOut) {
		for(JobProduct productScannedOut : productsScannedOut) {
			int productID = productScannedOut.getProduct().getProductId();
			int quantityUsed = productScannedOut.getQuantityUsed();
			int jobID = productScannedOut.getJobId();
			PreparedStatement ps;
			try {
				// reduce stock by quantity used
				ps = con.getPreparedStatement("UPDATE Stocks SET stock = stock - ? WHERE productID = ?");
				ps.setInt(1, quantityUsed);
				ps.setInt(2, productID);
				ps.executeUpdate();
				ps.close();
				
				// update JobStockLink table to connect products scanned out to jobs
				ps = con.getPreparedStatement("INSERT INTO JobStockLink (jobID, productID, quantityUsed) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE quantityUsed = quantityUsed + ?");
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
	
	@Override
	public Product getProductFromBarcode(String barcode) {
		try {
			PreparedStatement ps = con.getPreparedStatement("SELECT * FROM Stocks WHERE barcode = ?");
			ps.setString(1, barcode);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				Product p = getProduct(rs);
				ps.close();
				return p;
			} else {
				ps.close();
				return null;
			}
		} catch(SQLException e) {
			return null;
		}
	}
	
	@Override
	public boolean addProductToJob(int jobID, int productID) {
		try {
			// check the product with productID has stocks more then 0
			PreparedStatement ps = con.getPreparedStatement("SELECT stock FROM Stocks WHERE productID = ?");
			ps.setInt(1, productID);
			ResultSet rs = ps.executeQuery();
			if(rs.next() && rs.getInt("stock") <= 0) {
				ps.close();
				return false;
			}
			ps.close();
			
			// decrease the stock by one if the product is not registered with the job
			ps = con.getPreparedStatement("UPDATE Stocks SET stock = CASE WHEN EXISTS(SELECT * FROM JobStockLink WHERE productID = ? AND JobStockLink.jobID = ?) THEN stock ELSE stock - 1 END WHERE productID = ?");
			ps.setInt(1, productID);
			ps.setInt(2, jobID);
			ps.setInt(3, productID);
			ps.executeUpdate();
			ps.close();
			
			// add the product to the job if it hasnt already been added
			ps = con.getPreparedStatement("INSERT INTO JobStockLink (jobID, productID, quantityUsed) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE quantityUsed = quantityUsed");
			ps.setInt(1, jobID);
			ps.setInt(2, productID);
			ps.setInt(3, 1);
			ps.executeUpdate();
			ps.close();
		} catch(SQLException e) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean removeProductFromJob(int jobID, int productID) {
		try {
			PreparedStatement ps = con.getPreparedStatement("DELETE FROM JobStockLink WHERE jobID = ? AND productID = ?");
			ps.setInt(1, jobID);
			ps.setInt(2, productID);
			ps.executeUpdate();
			ps.close();
		} catch(SQLException e) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean isProductRegisteredWithJob(int jobID, int productID) {
		try {
			PreparedStatement ps = con.getPreparedStatement("SELECT CASE WHEN EXISTS(SELECT * FROM JobStockLink WHERE productID = ? AND jobID = ?) THEN \"true\" ELSE \"false\" END as b");
			ps.setInt(1,  productID);
			ps.setInt(2, jobID);
			ResultSet rs = ps.executeQuery();
			if(rs.next() && rs.getString("b").equals("yes")) {
				return true;
			}
			return false;
		} catch(SQLException e) {
			return false;
		}
	}
	
	//new added function
	//delete the product in the database given the productId
	public boolean deleteProduct(int productID){
		PreparedStatement ps;
		try { 
			ps = con.getPreparedStatement("delete from Stocks where productID = ?");
			ps.setInt(1, productID);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			return false;
		}		
		return true;
	}
	
	//new added function
	//show the most used product
	public String getMostUsedProduct() {
		String mostUsedProduct = null;
		PreparedStatement ps;
		try { 
			ps = con.getPreparedStatement("SELECT MAX(sum), a.description FROM (SELECT SUM(quantityUsed) as sum, Stocks.description FROM JobStockLink, Stocks WHERE JobStockLink.productID = Stocks.productID GROUP BY Stocks.productID) a");
			ResultSet rs = ps.executeQuery();
			if(!rs.next()) {
				return null;
			}
			mostUsedProduct = new String(rs.getString("description"));
			ps.close();
			return mostUsedProduct;
		} catch (SQLException e) {
			return null;
		}
		
	}
		
	/**
	 * @author psyys4
	 * @param productID
	 * @return ArrayList contains all product with productID used in current job.
	 */
	//new added function
	//check whether the product is in used
	public ArrayList<Integer> checkProductInUsed(int productID) {
		ArrayList<Integer> jobIDs = new ArrayList<>();
		PreparedStatement ps;
		try { 
			ps = con.getPreparedStatement("select jobID from JobStockLink where productID = ?");
			ps.setInt(1, productID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				jobIDs.add(rs.getInt("jobID"));
			}			
			ps.close();
		} catch (SQLException e) {
			return null;
		}	
		return jobIDs;
	}
		
	//new added function
	//add a totally new product into the database
	public boolean addNewProduct(String code, String description, String barCode) {
		PreparedStatement ps;
		try { 
			if(barCode != null) {
				ps = con.getPreparedStatement("insert into Stocks (productCode, description, barcode, stock) values (?, ?, ?, 0)");
				ps.setString(3, barCode);
			}else {
				ps = con.getPreparedStatement("insert into Stocks (productCode, description, barcode, stock) values (?, ?, null, 0)");				
			}
			ps.setString(1, code);
			ps.setString(2, description);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}		
		return true;		
	}
	
	private Product getProduct(ResultSet rs) throws SQLException {
		return new Product(rs.getInt("productID"), rs.getString("productCode"), rs.getString("description"), 
				rs.getFloat("pricePerUnit"), rs.getInt("Stock"), rs.getString("barCode"), rs.getInt("minQuantity"));
	}
}
