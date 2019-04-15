package com.g52grp.views;

import com.g52grp.main.Main;
import com.g52grp.stockout.ConcreteJobManager;
import com.g52grp.stockout.JobManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
	    
	TableViewUpdate tb; // used to update the table after job is added
	
	/**
	 * @param tb
	 */
	public void initData(TableViewUpdate tb) {
		this.tb = tb;
	}
	
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
		
		if(sn.length() > 255) {
			errorMessage.setText("Site Name cannot be longer then 255 characters");
			return;
		}
		if(pn.length() > 10) {
			errorMessage.setText("Plot Number cannot be more then 10 digits");
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
			errorMessage.setText("The Job '" + sn + " " + pn + "' already exists!");
			return;
		}
		
		addJob.setDisable(true);
		if(tb != null) {
			tb.updateTableView();
		}
		
		Stage theStage = (Stage) (((Node) e.getSource()).getScene().getWindow());
		theStage.close();
	}
}
