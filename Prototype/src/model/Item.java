package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class Item {
	private SimpleStringProperty name;
	private SimpleStringProperty quantity;
	private MyTextField textField;
	
	public Item(String name, String quantity) {
		this.name = new SimpleStringProperty(name);
		this.quantity = new SimpleStringProperty(quantity);
		
	}
	
	public Item(String name, int defaultValue) {
		this.name = new SimpleStringProperty(name);
		this.textField = new MyTextField();
		TextField textField2 = textField.getTextField().getValue();
		
		textField2.textProperty().addListener((ChangeListener<? super String>) new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            	if(Integer.parseInt(DataManager.items.get(name)) < Integer.parseInt(textField2.getText())) {
    				textField2.setStyle(" -fx-text-fill: #ff0000");
    			}else {
    				textField2.setStyle(" -fx-text-fill: #000000");
    			}
            }
        });

		textField.setText(String.valueOf(defaultValue));
	}
	
	public String getName() {
		return name.get();
	}
	
	public String getQuantity() {
		return quantity.get();
	}
	
	public MyTextField getMyTextField() {
		return textField;
	}
}
