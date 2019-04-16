package com.g52grp.backend;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.g52grp.database.DatabaseConnection;
import com.g52grp.database.JobProduct;
import com.g52grp.database.Product;

/**
 * @author psyfb2
 * Implementation of ProductManager
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
			PreparedStatement ps = con.getPreparedStatement("SELECT * FROM JobStockLink, Stocks WHERE JobStockLink.productID = Stocks.productID AND JobStockLink.jobID = ? ORDER BY Stocks.productID");
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
			PreparedStatement ps = con.getPreparedStatement("SELECT * FROM Stocks ORDER BY productID");
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
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean deleteProduct(int productID){
		PreparedStatement ps;
		try { 
			ps = con.getPreparedStatement("DELETE from Stocks where productID = ?");
			ps.setInt(1, productID);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			return false;
		}		
		return true;
	}
	
	@Override
	public String getMostUsedProduct() {
		String mostUsedProduct = "";
		PreparedStatement ps;
		try { 
			ps = con.getPreparedStatement("SELECT MAX(sum), a.description FROM (SELECT SUM(quantityUsed) as sum, Stocks.description FROM JobStockLink, Stocks WHERE JobStockLink.productID = Stocks.productID GROUP BY Stocks.productID) a");
			ResultSet rs = ps.executeQuery();
			if(!rs.next()) {
				return "None";
			}
			mostUsedProduct = rs.getString("description");
			ps.close();
			return mostUsedProduct;
		} catch (SQLException e) {
			return null;
		}
		
	}

	//check whether the product is in used
	public ArrayList<String> checkProductInUsed(int productID) {
		ArrayList<String> jobDetails = new ArrayList<>();
		PreparedStatement ps;
		try { 
			ps = con.getPreparedStatement("select siteName from JobStockLink, Jobs where productID = ? and JobStockLink.jobID = Jobs.jobID");
			ps.setInt(1, productID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				jobDetails.add(rs.getString("siteName"));
			}			
			ps.close();
		} catch (SQLException e) {
			return null;
		}	
		return jobDetails;
	}
		
	@Override
	public boolean addNewProduct(String code, String description, String barCode, float pricePerUnit) {
		PreparedStatement ps;
		try { 
			ps = con.getPreparedStatement("insert into Stocks (productCode, description, barcode, stock, minQuantity, pricePerUnit) values (?, ?, ?, 0, 1, ?)");
			ps.setString(1, code);
			ps.setString(2, description);
			ps.setString(3, barCode);
			ps.setFloat(4, pricePerUnit);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}		
		return true;		
	}
	
	@Override
	public boolean updateMinQuantity(int id, int newMinQuantity) {
		PreparedStatement ps;
		try { 
			ps = con.getPreparedStatement("update Stocks SET minQuantity = ? where productID = ?");
			ps.setInt(1, newMinQuantity);
			ps.setInt(2, id);
			ps.executeUpdate();			
			ps.close();
		} catch (SQLException e) {
			return false;
		}	
		return true;		
	}
	
	@Override
	public boolean updateBarcode(int id, String newBarcode) {
		PreparedStatement ps;
		try { 
			ps = con.getPreparedStatement("update Stocks SET barcode = ? where productID = ?");
			ps.setString(1, newBarcode);
			ps.setInt(2, id);
			ps.executeUpdate();			
			ps.close();
		} catch (SQLException e) {
			return false;
		}			
		return true;		
	}
	
	public boolean importNewProduct(String code, String description,double salesPrice, int quantity) {
		PreparedStatement ps;
		try { 
			ps = con.getPreparedStatement("insert ignore into Stocks (productCode, description, pricePerUnit, stock) values (?, ?, ?, ?)");				
			ps.setString(1, code);
			ps.setString(2, description);
			ps.setDouble(3, salesPrice);
			ps.setInt(4, quantity);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			return false;
		}		
		return true;		
	}
	
	public int getStockForOne(String code, String description) {
		PreparedStatement ps;
		int stock = 0;
		try {
			ps = con.getPreparedStatement("select stock from Stocks where productCode = ? AND description = ?");				
			ps.setString(1, code);
			ps.setString(2, description);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				stock = rs.getInt("stock");
			}
			ps.close();
		} catch (SQLException e) {
			return 0;
		}	
		return stock;
	}
	

	public boolean importRestock(String code, String description, int quantity, int stockRemaining) {
		PreparedStatement ps;
		try { 
			ps = con.getPreparedStatement("update Stocks SET stock = ? where productCode = ? AND description = ?");				
			ps.setInt(1, quantity + stockRemaining);
			ps.setString(2, code);
			ps.setString(3, description);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			return false;
		}		
		return true;		
	}
	
	private Product getProduct(ResultSet rs) throws SQLException {
		return new Product(rs.getInt("productID"), rs.getString("productCode"), rs.getString("description"), 
				rs.getFloat("pricePerUnit"), rs.getInt("Stock"), rs.getString("barCode"), rs.getInt("minQuantity"));
	}

	@Override
	public Product getProductFromProductId(int productID) {
		try {
			PreparedStatement ps = con.getPreparedStatement("SELECT * FROM Stocks WHERE productID = ?");
			ps.setInt(1, productID);
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
}
