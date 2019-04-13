package com.g52grp.stockout;

import org.junit.Test;

import com.g52grp.database.JobProduct;

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
	 * Test getting products registered with a specific jobID.
	 */
	@Test
	public void testGetProductsFromJobID() {
		JobProduct[] registeredProducts = pm.getProductsFromJobId(1);
		assertEquals(3, registeredProducts.length);
		
		assertEquals(1, registeredProducts[0].getJobId());
		
		assertEquals(1, registeredProducts[0].getProduct().getProductId());
		assertEquals(2, registeredProducts[1].getProduct().getProductId());
		assertEquals(3, registeredProducts[2].getProduct().getProductId());
	}
	
	/**
	 * Testing getting products registered with an invalid jobID.
	 */
	@Test
	public void testGetProductsFromJobIDInvalid() {
		JobProduct[] registeredProducts = pm.getProductsFromJobId(-1);
		assertEquals(0, registeredProducts.length);
	}
	
	/**
	 * Testing getting products registered with a jobID which has no products registered to it.
	 */
	@Test
	public void testGetProductsFromJobIDEmptyJob() {
		JobProduct[] registeredProducts = pm.getProductsFromJobId(3);
		assertEquals(0, registeredProducts.length);
	}
	
}
