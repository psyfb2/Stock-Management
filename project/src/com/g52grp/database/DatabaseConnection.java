package com.g52grp.database;

import java.sql.*;

/**
 * @author psyfb2
 * Connect to the mysql database
 * Tables: Jobs, Stocks, JobStockLink
 */
public class DatabaseConnection {
	private static final String endPoint = "stocks.cv2g2wcvs9bq.us-east-1.rds.amazonaws.com";
	private static final String port = "8080";
	private static final String userName = "psyfb2";
	private static final String pw = "G52GROUPPROJECT";
	private static final String dbName = "stocks";
	private Connection con;
	
	/**
	 * Opens a connection to the mysql server
	 * @return Whether the connection was successful or not
	 */
	public boolean openConnection() {
		// Please guys only make ONE connection for the whole program
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://" + endPoint + ":" + port + "/" + dbName, userName, pw);
		} catch(Exception e) {
			return false;
		}
		return true;
	}
	
	/**
	 * @param sql SQL query to be sent to the database, typically a SQL SELECT statement
	 * @return ResultSet for the query, null if query was unsuccessful 
	 * Only use for static SQL queries (e.g. CREATE, ALTER etc)
	 * @return Statement object which can used to produce a result set
	 */
	public Statement getStatement() throws SQLException {
		return con.createStatement();
	}
	
	/**
	 * prepared statements are much faster then normal statements and safer (cannot do SQL Injection)
	 * @param preparedStatement MYSQL statement e.g. insert into Emp values(?,?)
	 * @return PreparedStatement object, you can call set methods on the preparedStatement to dynamically replace the question marks with values
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
	
}
