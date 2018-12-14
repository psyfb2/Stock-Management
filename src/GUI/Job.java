package GUI;

import javafx.beans.property.SimpleStringProperty;

public class Job {
	private SimpleStringProperty name;
	private SimpleStringProperty location;
	
	public Job(String name, String location) {
		this.name = new SimpleStringProperty(name);
		this.location = new SimpleStringProperty(location);
	}
	
	public String getName() {
		return name.get();
	}
	
	public String getLocation() {
		return location.get();
	}
}
