package com.g52grp.database;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import com.g52grp.main.Main;

import java.sql.*;

/**
 * Connect to the mysql database
 * Tables: Jobs, Stocks, JobStockLink
 * @author psyfb2
 */
public class DatabaseConnection {
	private HashMap<String, String> result;
	private String endPoint;
	private String port;
	private String userName;
	private String pw;
	private String dbName;
	private Connection con;
	private boolean connected;
	
	public DatabaseConnection() {
		try {
			result = getConfigValues();
			endPoint = result.get("endPoint");
			port = result.get("port");
			userName = result.get("userName");
			pw = result.get("pw");
			dbName = result.get("dbName");
		} catch(IOException e) {
			e.printStackTrace();
			result = null;
			endPoint = "";
			port = "";
			pw = "";
			dbName = "";
		}
		connected = false;
	}
	
	/**
	 * Opens a connection to the mysql server
	 * @param unitTestConnection Pass true if this connection is to a hqldb for testing, else false
	 * @return Whether the connection was successful or not
	 */
	public boolean openConnection(boolean unitTestConnection) {
		// Please guys only make ONE connection for the whole program
		try {
			if(unitTestConnection) {
				 // Registering the HSQLDB JDBC driver
		         Class.forName("org.hsqldb.jdbcDriver");
		         con = DriverManager.getConnection("jdbc:hsqldb:mem:testdb", "psyfb2", "");
			} else {
				Class.forName("com.mysql.cj.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://" + endPoint + ":" + port + "/" + dbName, userName, pw);
			}
			connected = true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Only use for static SQL queries (e.g. CREATE, ALTER etc)
	 * @return Statement object which can used to produce a result set
	 * @throws SQLException connection lost
	 */
	public Statement getStatement() throws SQLException {
		return con.createStatement();
	}
	
	/**
	 * prepared statements are much faster then normal statements and safer (cannot do SQL Injection)
	 * @param preparedStatement MYSQL statement e.g. insert into Emp values(?,?)
	 * @return PreparedStatement object, you can call set methods on the preparedStatement to dynamically replace the question marks with values
	 * @throws SQLException connection lost
	 */
	public PreparedStatement getPreparedStatement(String preparedStatement) throws SQLException {
		return con.prepareStatement(preparedStatement);
	}
	
	
	/**
	 * Close connection to the mysql server
	 * @return Whether the connection was closed successfully 
	 */
	public boolean closeConnection() {
		try {
			con.close();
		} catch(Exception e) {
			return false;
		}
		return true;
	}
	
	/**
	 * @return True if a successful connection has been made using openConnection, else False
	 */
	public boolean isConnected() {
		return connected;
	}
	
	private HashMap<String, String> getConfigValues() throws IOException {
		InputStream inputStream = null;
		HashMap<String, String> result = new HashMap<>();
		try {
			Properties prop = new Properties();
			inputStream = getClass().getClassLoader().getResourceAsStream(Main.CONFIGPATH);
			
			if(inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file " + Main.CONFIGPATH + " not found in classpath");
			}
			
			result.put("endPoint", prop.getProperty("endPoint"));
			result.put("port", prop.getProperty("port"));
			result.put("userName", prop.getProperty("userName"));
			result.put("pw", prop.getProperty("pw"));
			result.put("dbName", prop.getProperty("dbName")); 
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			inputStream.close();
		}
		return result;
	}
}
