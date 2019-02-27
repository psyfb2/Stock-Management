package com.g52grp.views;

import com.g52grp.main.Main;
import com.g52grp.stockout.ConcreteJobManager;
import com.g52grp.stockout.JobManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * @author psyfb2
 * Controller for AddNewJob FXML file
 * Allows user to:
 * 		Add new job to the database
 */
public class AddNewJobController {
	@FXML TextField siteName;
	@FXML TextField plotNumber;
	@FXML Label errorMessage; // initially empty, lets user know if they have done something wrong (e.g. empty siteName or plotNumber)
	@FXML Button addJob;
	
	/**
	 * Called when addJob button is clicked
	 * will get inputs from text fields siteName and plotNumber also ensuring these are both non-empty before adding anything to the db
	 */
	@FXML public void addNewJob(ActionEvent e) {
		String sn = siteName.getText();
		String pn = plotNumber.getText();
		
		if(sn.isEmpty()) {
			errorMessage.setText("Site Name must not be empty");
			return;
		} else if(pn.isEmpty()) {
			errorMessage.setText("Plot Number must not be empty");
			return;
		}
		
		int pnInt;
		try {
			pnInt = Integer.parseInt(pn);
		} catch(NumberFormatException event) {
			errorMessage.setText("Please enter a number for the Plot Number");
			return;
		}
		
		JobManager jm = new ConcreteJobManager(Main.con);
		if(!jm.addNewJobToDb(sn, pnInt)) {
			errorMessage.setText("Error connecting to the database!");
			addJob.setDisable(true);
			return;
		}
		
		addJob.setDisable(true);
		errorMessage.setText("Job added successfully");
	}
}
