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
	 * @param sql SQL command to send to the mysql server
	 * @return ResultSet for the query, null if query was unsuccessful 
	 */
	public ResultSet sendSql(String sql) {
		try {
			Statement s = con.createStatement();
			return s.executeQuery(sql);
		} catch(Exception e) {
			return null;
		}
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
