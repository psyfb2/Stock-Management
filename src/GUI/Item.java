package GUI;

import javafx.beans.property.SimpleStringProperty;

public class Item {
	private SimpleStringProperty name;
	private SimpleStringProperty quantity;
	
	public Item(String name, String quantity) {
		this.name = new SimpleStringProperty(name);
		this.quantity = new SimpleStringProperty(quantity);
	}
	
	public String getName() {
		return name.get();
	}
	
	public String getQuantity() {
		return quantity.get();
	}
	
}
