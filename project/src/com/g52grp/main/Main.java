package com.g52grp.main;

import com.g52grp.database.DatabaseConnection;

public class Main {
	
	public static void main(String[] args) {
		// only create ONE connection for the whole program (take DatabaseConnection object as a parameter for your classes)
		DatabaseConnection test = new DatabaseConnection();
		
		// connection always fails in uni for some reason, must be the damn firewall
		if (test.openConnection()) {
			System.out.println("Successful Connection");
			test.closeConnection();
		} else {
			System.out.println("Connection Failed");
		}
		
	}

}
