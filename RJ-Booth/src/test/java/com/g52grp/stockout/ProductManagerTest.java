package com.g52grp.stockout;

import java.util.ArrayList;

import org.junit.Test;

import com.g52grp.database.JobProduct;
import com.g52grp.database.Product;

/**
 * Tests ProductManager backend class which utilises SQL statements.
 * Runs with dbunit to set up and tear down database tables and HyperSQL as a mysql database running on the local machine.
 * Note: Between Tests database is reset to a known state (delete all then insert data set again between tests) so that tests don't interfere with one another.
 * @author psyfb2
 */
public class ProductManagerTest extends TestDB {
	private ConcreteProductManager pm;
	
	public ProductManagerTest(String name) {
		super(name);
		pm = new ConcreteProductManager(con);
	}

	/**
	 * Test correctness of first product returned from getting product registered with a jobID of 1.
	 */
	@Test
	public void testGetProductsFromJobID1() {
		JobProduct[] registeredProducts = pm.getProductsFromJobId(1);
		assertEquals(3, registeredProducts.length);
		
		assertEquals(1, registeredProducts[0].getJobId());
		
		Product p = registeredProducts[0].getProduct();
		assertEquals(1, p.getProductId());
		assertEquals("DetaTestProduct101", p.getProductCode());
		assertEquals("For testing purposes£$%", p.getDescription());
		assertEquals(1.69f, p.getPricePerUnit());
		assertEquals(25, p.getStock());
		assertEquals(null, p.getBarCode());
		assertEquals(5, p.getMinQuantity());
	}
	
	/**
	 * Test correctness of second product returned from getting product registered with a jobID of 1.
	 */
	@Test
	public void testGetProductsFromJobID2() {
		JobProduct[] registeredProducts = pm.getProductsFromJobId(1);
		assertEquals(3, registeredProducts.length);
		
		assertEquals(1, registeredProducts[1].getJobId());
		
		Product p = registeredProducts[1].getProduct();
		assertEquals(2, p.getProductId());
		assertEquals("!£$%^*()_+{}~@:?", p.getProductCode());
		assertEquals("Test Product", p.getDescription());
		assertEquals(400.92f, p.getPricePerUnit());
		assertEquals(0, p.getStock());
		assertEquals("5010459007289", p.getBarCode());
		assertEquals(0, p.getMinQuantity());
	}
	
	/**
	 * Test correctness of third and last product returned from getting product registered with a jobID of 1.
	 */
	@Test
	public void testGetProductsFromJobID3() {
		JobProduct[] registeredProducts = pm.getProductsFromJobId(1);
		assertEquals(3, registeredProducts.length);
		
		assertEquals(1, registeredProducts[2].getJobId());
		
		Product p = registeredProducts[2].getProduct();
		assertEquals(3, p.getProductId());
		assertEquals("DetaDB170", p.getProductCode());
		assertEquals("6/8 Gang Box", p.getDescription());
		assertEquals(1.5f, p.getPricePerUnit());
		assertEquals(1, p.getStock());
		assertEquals(null, p.getBarCode());
		assertEquals(500, p.getMinQuantity());
	}
	
	/**
	 * Test getting products registered with an invalid jobID.
	 */
	@Test
	public void testGetProductsFromJobIDInvalid() {
		JobProduct[] registeredProducts = pm.getProductsFromJobId(-1);
		assertEquals(0, registeredProducts.length);
	}
	
	/**
	 * Test getting products registered with a jobID which has no products registered to it.
	 */
	@Test
	public void testGetProductsFromJobIDEmptyJob() {
		JobProduct[] registeredProducts = pm.getProductsFromJobId(3);
		assertEquals(0, registeredProducts.length);
	}
	
	/**
	 * Test getting product from product code and description. Note that (productCode, description) combined together are unique
	 */
	@Test
	public void testGetProductFromProductcodeAndDescriptionValid() {
		Product p = pm.getProductFromProductcodeAndDescription("DetaTestProduct101", "For testing purposes£$%");
		
		assertEquals(1, p.getProductId());
		assertEquals(null, p.getBarCode());
		assertEquals("DetaTestProduct101", p.getProductCode());
		assertEquals("For testing purposes£$%", p.getDescription());
		assertEquals(5, p.getMinQuantity());
		assertEquals(25, p.getStock());
		assertEquals(1.69f, p.getPricePerUnit());
	}
	
	/**
	 * Test getting product from product code and description which does not exist for a product.
	 */
	@Test
	public void testGetProductFromProductcodeAndDescriptionInvalid1() {
		Product p = pm.getProductFromProductcodeAndDescription("DetaTestProduct1010", "For testing purposes£$%");
		
		assertEquals(null, p);
	}
	
	/**
	 * Test getting product from product code and description which does not exist for a product.
	 */
	@Test
	public void testGetProductFromProductcodeAndDescriptionInvalid2() {
		Product p = pm.getProductFromProductcodeAndDescription("DetaTestProduct101", "For testing purposes£$%*");
		
		assertEquals(null, p);
	}
	
	/**
	 * Test getting product from product code and description which does not exist for a product.
	 */
	@Test
	public void testGetProductFromProductcodeAndDescriptionInvalid3() {
		Product p = pm.getProductFromProductcodeAndDescription("", "");
		
		assertEquals(null, p);
	}
	
	/**
	 * Test get all products works and that first job returned is correct.
	 */
	@Test
	public void testGetAllProducts1() {
		Product[] products = pm.getAllProducts();
		assertEquals(3, products.length);
		
		Product p = products[0];
		assertEquals(1, p.getProductId());
		assertEquals(null, p.getBarCode());
		assertEquals("DetaTestProduct101", p.getProductCode());
		assertEquals("For testing purposes£$%", p.getDescription());
		assertEquals(5, p.getMinQuantity());
		assertEquals(25, p.getStock());
		assertEquals(1.69f, p.getPricePerUnit());
	}
	
	/**
	 * Test get all products works and that second job returned is correct.
	 */
	@Test
	public void testGetAllProducts2() {
		Product[] products = pm.getAllProducts();
		assertEquals(3, products.length);
		
		Product p = products[1];
		assertEquals(2, p.getProductId());
		assertEquals("!£$%^*()_+{}~@:?", p.getProductCode());
		assertEquals("Test Product", p.getDescription());
		assertEquals(400.92f, p.getPricePerUnit());
		assertEquals(0, p.getStock());
		assertEquals("5010459007289", p.getBarCode());
		assertEquals(0, p.getMinQuantity());
	}
	
	/**
	 * Test get all products works and that third job returned is correct.
	 */
	@Test
	public void testGetAllProducts3() {
		Product[] products = pm.getAllProducts();
		assertEquals(3, products.length);
		
		Product p = products[2];
		assertEquals(3, p.getProductId());
		assertEquals("DetaDB170", p.getProductCode());
		assertEquals("6/8 Gang Box", p.getDescription());
		assertEquals(1.5f, p.getPricePerUnit());
		assertEquals(1, p.getStock());
		assertEquals(null, p.getBarCode());
		assertEquals(500, p.getMinQuantity());
	}
	
	/**
	 * Test getting all products as array list
	 */
	@Test
	public void testGetAllProductsArrayList() {
		ArrayList<Product> products = pm.getAllProductsArrayList();
		assertEquals(3, products.size());
	}
	
	/**
	 * Test getting a product from a barcode is correct (note: barcode is unique)
	 */
	@Test
	public void testGetProductFromBarcode() {
		Product p = pm.getProductFromBarcode("5010459007289");
		
		assertEquals(2, p.getProductId());
		assertEquals("!£$%^*()_+{}~@:?", p.getProductCode());
		assertEquals("Test Product", p.getDescription());
		assertEquals(400.92f, p.getPricePerUnit());
		assertEquals(0, p.getStock());
		assertEquals("5010459007289", p.getBarCode());
		assertEquals(0, p.getMinQuantity());
	}
	
	/**
	 * Test getting a product from a barcode is correct given a barcode which does not exist for any product
	 */
	@Test
	public void testGetProductFromBarcodeInvalid() {
		Product p = pm.getProductFromBarcode("abcd");
		
		assertEquals(p, null);
	}
	
	/**
	 * Test getting a product from a barcode is correct given an empty string
	 */
	@Test
	public void testGetProductFromBarcodeInvalid2() {
		Product p = pm.getProductFromBarcode("");
		
		assertEquals(p, null);
	}
	
	
}
