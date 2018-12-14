package GUI;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataManager {
	public static ArrayList<String> names = new ArrayList<>() {{
		add("job1");
		add("job2");
	}};	
	public static ArrayList<String> locations = new ArrayList<>() {{
		add("London");
		add("Nottingham");
	}};
	
	public static ObservableList<Job> data =  FXCollections.observableArrayList();
	//public static ObservableList<Item> inItems =  FXCollections.observableArrayList();
	
	//public static ArrayList<String> itemNames = new ArrayList<>();
	//public static ArrayList<String> itemQuantity = new ArrayList<>();
	
	public static HashMap<String, String> items = new HashMap<>() {{
		put("item1", "10");
		put("item2", "15");
		put("item3", "20");
	}};
	
	
}
