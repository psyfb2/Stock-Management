package com.g52grp.stockout;

import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

/**
 * All classes which perform backend tests should inherit this class.
 * Sets up dbunit connection to hyperSQL. hyperSQL database should be created in main memory automatically for you.
 * @author psyfb2
 */
public class TestDB extends DBTestCase {
	protected com.g52grp.database.DatabaseConnection con;
	
	public TestDB(String name) {
		super( name );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.hsqldb.jdbcDriver" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:hsqldb:mem:testdb" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "psyfb2" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "" );
        
        con = new com.g52grp.database.DatabaseConnection();
		con.openConnection(true);
		
		if(!con.isConnected()) {
			System.err.println("Could not connect to HyperSQL db, perhaps it is not open on your local machine?");
		}
		
		org.junit.Assume.assumeTrue(con.isConnected());
		
		// since the hypersql server is in main memory need to create the tables Jobs, JobStockLink, Stocks
		try {
			PreparedStatement ps = con.getPreparedStatement("CREATE TABLE IF NOT EXISTS Jobs ("
					+ "jobID INT, siteName varchar(256) NOT NULL, plotNumber INT NOT NULL, startDate DATE, archived BOOLEAN, PRIMARY KEY(jobID));");
			ps.executeUpdate();
			ps.close();
			
			ps = con.getPreparedStatement("CREATE TABLE IF NOT EXISTS JobStockLink ("
					+ "jobID INT NOT NULL, productID INT NOT NULL, quantityUsed INT NOT NULL, PRIMARY KEY(jobID, productID));");
			ps.executeUpdate();
			ps.close();
			
			ps = con.getPreparedStatement("CREATE TABLE IF NOT EXISTS Stocks ("
					+ "productID INT, productCode varchar(256) NOT NULL, description varchar(256) NOT NULL, pricePerUnit FLOAT NOT NULL," + 
					"stock INT NOT NULL, barcode varchar(256), minQuantity INT, PRIMARY KEY(productID));");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new FileInputStream(getClass().getResource("/testData.xml").getFile()));
	}

}
