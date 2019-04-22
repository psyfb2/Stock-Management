package com.g52grp.database;

import java.sql.*;

/**
 * Connect to the mysql database
 * Tables: Jobs, Stocks, JobStockLink
 * @author psyfb2
 */
public class DatabaseConnection {
	private static final String endPoint = "stocks.cv2g2wcvs9bq.us-east-1.rds.amazonaws.com";
	private static final String port = "8080";
	private static final String userName = "psyfb2";
	private static final String pw = "G52GROUPPROJECT";
	private static final String dbName = "stocks";
	private Connection con;
	private boolean connected = false;
	
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
	
}
