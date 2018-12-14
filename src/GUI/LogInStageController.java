package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.stage.Stage;

public class LogInStageController {

	@FXML private TextField PIN;
	@FXML private Button enterButton;
	
	private DropShadow shadow = new DropShadow();
	
	public void initialize() {
		
	}
	
	@FXML private void enterClicked() {
		new JobStage(new Stage()); 
		Stage stage = (Stage)enterButton.getScene().getWindow();
	    stage.close();
	}
	
	@FXML private void enterEntered() {
		enterButton.setEffect(shadow);
	}
	
	@FXML private void enterExited() {
		enterButton.setEffect(null);
	}
}
