package com.g52grp.database;

import java.sql.*;

/**
 * @author psyfb2
 * Connect to the mysql database
 */
public class DatabaseConnection {
	private static final String endPoint = "stocks.cv2g2wcvs9bq.us-east-1.rds.amazonaws.com";
	private static final String port = "3306";
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
	 * 
	 * @param sql SQL query to be sent to the database, typically a SQL SELECT statement
	 * @return ResultSet for the query, null if query was unsuccessful 
	 */
	public ResultSet sendQuery(String sql) {
		try {
			Statement s = con.createStatement();
			return s.executeQuery(sql);
		} catch(Exception e) {
			return null;
		}
	}
	
	/**
	 * @param sql SQL Data Manipulation Language (DML) statement, such as INSERT, UPDATE or DELETE; or an SQL statement that returns nothing, such as a DDL statement.
	 * @return whether the sql statement was executed by the database successfully
	 */
	public boolean sendUpdate(String sql) {
		try {
			Statement s = con.createStatement();
			s.executeUpdate(sql);
		} catch(Exception e) {
			return false;
		}
		return true;
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
