package model;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class MyTextField {
	TextField textField = new TextField();
	
	public ObservableValue<TextField> getTextField() {
		return new ObservableValue<TextField>() {

			@Override
			public void addListener(InvalidationListener arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void removeListener(InvalidationListener arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void addListener(ChangeListener<? super TextField> arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public TextField getValue() {
				return textField;
			}

			@Override
			public void removeListener(ChangeListener<? super TextField> arg0) {
				// TODO Auto-generated method stub
			}
		};
	}
	
	public String getText() {
		return textField.getText();
	}
	
	public void setText(String quantity) {
		textField.setText(quantity);
	}
}
