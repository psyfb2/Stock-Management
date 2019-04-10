package model;

import javafx.beans.property.SimpleStringProperty;

public class Job {
	private SimpleStringProperty name;
	private SimpleStringProperty location;
	private SimpleStringProperty items;
	private MyCheckBox checkBox;

	public Job(String name, String location, String items) {
		this.name = new SimpleStringProperty(name);
		this.location = new SimpleStringProperty(location);
		this.items = new SimpleStringProperty(items);
		checkBox = new MyCheckBox();
	}

	public String getName() {
		return name.get();
	}

	public MyCheckBox getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(MyCheckBox checkBox) {
		this.checkBox = checkBox;
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