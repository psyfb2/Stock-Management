package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class StockStageController {

	@FXML Button stockInButton;
	@FXML Button stockOutButton;
	
	
	@FXML private void onStockInbuttonClicked() {
		new StockInStage(new Stage());
		Stage stage = (Stage)stockInButton.getScene().getWindow();
	    stage.close();
	}
	
	@FXML private void onStockOutbuttonClicked() {
		new StockOutStage(new Stage());
		Stage stage = (Stage)stockOutButton.getScene().getWindow();
	    stage.close();
	}
	
	@FXML private void closeStage() {
		Stage stage = (Stage)stockOutButton.getScene().getWindow();
	    stage.close();
	}
}
