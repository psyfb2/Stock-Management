package com.g52grp.views;

/**
 * @author psyfb2
 * Controllers containing a TableView which may need to be updated by other controller 
 * should implement this interface
 */
public interface TableViewUpdate {
	/**
	 * reload the table so it contains whats within the database
	 */
	public void updateTableView();
}
