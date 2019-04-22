package com.g52grp.controllers;

/**
 * Controllers containing a TableView which may need to be updated by other controller 
 * should implement this interface
 * @author psyfb2
 */
public interface TableViewUpdate {
	/**
	 * reload the table so it contains what's within the database
	 */
	public void updateTableView();
}
