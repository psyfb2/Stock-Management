package GUI;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class DataManager {
	/*public static ArrayList<String> names = new ArrayList<>() {{
		add("job1");
		add("job2");
	}};	
	public static ArrayList<String> locations = new ArrayList<>() {{
		add("London");
		add("Nottingham");
	}};*/
	
	public static HashMap<String, String> jobs = new HashMap<>() {{		
		put("job1", "London");
		put("job2", "Nottingham");
	}};
	
	public static ObservableList<Job> data =  FXCollections.observableArrayList();
	
	public static Job job = null;
	public static TableView<Job> jobTable = null;
	//public static ObservableList<Item> inItems =  FXCollections.observableArrayList();
	
	//public static ArrayList<String> itemNames = new ArrayList<>();
	//public static ArrayList<String> itemQuantity = new ArrayList<>();
	
	public static HashMap<String, String> items = new HashMap<>() {{
		put("item1", "10");
		put("item2", "8");
		put("item3", "15");
	}};
	
	
}
