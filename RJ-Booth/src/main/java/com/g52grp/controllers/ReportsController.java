package com.g52grp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.g52grp.backend.*;
import com.g52grp.database.Job;
import com.g52grp.database.JobProduct;
import com.g52grp.main.*;
import com.g52grp.pageloaders.HomePage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ReportsController implements Initializable {
	private JobManager jm;
	private ProductManager pm;
	private ArrayList<Job> allJobs; // including archived jobs
	
	@FXML Button homePageButton;
	@FXML ComboBox<String> selectMonth;
	@FXML Label spendLabel;
	@FXML Label errorMessage;
	@FXML LineChart<String, Number> cumulativeYearlySpend;
	
	public ReportsController() {
		jm = new ConcreteJobManager(Main.con);
		pm = new ConcreteProductManager(Main.con);
		allJobs = jm.getAllJobsArrayList();
		allJobs.addAll(jm.getAllArchivedJobsArrayList());
	}

	@FXML public void homePageButtonClicked(ActionEvent e) throws IOException {
		Stage theStage = (Stage) homePageButton.getScene().getWindow();
		new HomePage(theStage);
	}
	
	@FXML public void comboChanged(ActionEvent e) {
		float spend = calcSpending(selectMonth.getValue());
		if(spend < 0) {
			return;
		}
		spendLabel.setText("Net Spend on " + selectMonth.getValue() + ": £" + Float.toString(spend));
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// initialise combobox
		ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", 
				"August", "September", "October", "November", "December");
		selectMonth.setItems(months);
		
		selectMonth.getSelectionModel().selectFirst();
		comboChanged(new ActionEvent());
		
		// initialise line chart
		XYChart.Series<String, Number> points = new XYChart.Series<String, Number>();
		float cumulativeSpend = 0.0f;
		for(String month : months) {
			cumulativeSpend += calcSpending(month);
			points.getData().add(new XYChart.Data<String, Number>(month, cumulativeSpend));
		}
		points.setName("Month");
		cumulativeYearlySpend.getData().add(points);
	}
	
	private float calcSpending(String month) {
		errorMessage.setText("");
		ArrayList<Job> jobsInMonth = getJobsForSpecificMonth(month);
		float spend = 0.0f;
		
		for(Job j : jobsInMonth) {
			JobProduct[] products = pm.getProductsFromJobId(j.getJobId());
			if(products == null) {
				errorMessage.setText("Failed to load products: error accessing database");
				return -1f;
			}
			for(JobProduct p : products) {
				spend += p.getProduct().getPricePerUnit() * p.getQuantityUsed();
			}
		}
		
		return spend;
	}
	
	private ArrayList<Job> getJobsForSpecificMonth(String month) {
		month = month.toLowerCase();
		String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		String dateToSearch;
		
		switch(month) {
			case "january":
				dateToSearch = "01";
				break;
			case "february":
				dateToSearch = "02";
				break;
			case "march":
				dateToSearch = "03";
				break;
			case "april":
				dateToSearch = "04";
				break;
			case "may":
				dateToSearch = "05";
				break;
			case "june":
				dateToSearch = "06";
				break;
			case "july":
				dateToSearch = "07";
				break;
			case "august":
				dateToSearch = "08";
				break;
			case "september":
				dateToSearch = "09";
				break;
			case "october":
				dateToSearch = "10";
				break;
			case "november":
				dateToSearch = "11";
				break;
			case "december":
				dateToSearch = "12";
				break;
			default:
				dateToSearch = "01";
		}

		return new ArrayList<Job>(allJobs.stream().filter(
				j -> j.getDate().substring(3, 5).equals(dateToSearch) && j.getDate().substring(6, 10).equals(year)).collect(Collectors.toList()));
	}

}
