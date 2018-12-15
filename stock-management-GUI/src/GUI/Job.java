package GUI;

import javafx.beans.property.SimpleStringProperty;

public class Job {
	private SimpleStringProperty name;
	private SimpleStringProperty location;
	private SimpleStringProperty items;
	
	public Job(String name, String location, String items) {
		this.name = new SimpleStringProperty(name);
		this.location = new SimpleStringProperty(location);
		this.items = new SimpleStringProperty(items);
	}
	
	public String getName() {
		return name.get();
	}
	
	public String getLocation() {
		return location.get();
	}
	
	public String getItems() {
		return items.get();
	}
	
	public void setItems(String items) {
		this.items.set(items);
	}

}