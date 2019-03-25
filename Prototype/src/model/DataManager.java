package model;

import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class DataManager {
	
	public static HashMap<String, String> jobs = new HashMap<>() {{		
		put("job1", "London");
		put("job2", "Nottingham");
	}};
	
	public static ObservableList<Job> jobDetails =  FXCollections.observableArrayList();
	public static ObservableList<Item> itemDetails =  FXCollections.observableArrayList();
	
	public static Job job = null;
	public static TableView<Job> jobTable = null;
	public static TableView<Item> itemTable = null;
	
	public static HashMap<String, String> items = new HashMap<>() {{
		put("item1", "10");
		put("item2", "5");
		put("item3", "15");
	}};
	
	public static String warning = null;
}
