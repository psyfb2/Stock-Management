package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.DataManager;
import model.StageManager;
import model.StockInStage;
import model.StockOutStage;

public class StockStageController {
	@FXML Pane pane;
	@FXML Button stockInButton;
	@FXML Button stockOutButton;
	
	@FXML public void initialize() {
		if(DataManager.job.getItems() == null) {
			stockInButton.setDisable(true);
		}else {
			stockInButton.setDisable(false);
		}
	}
	
	@FXML private void onStockInbuttonClicked() {
		new StockInStage(new Stage());
		StageManager.getStockWindow().close();
	}
	
	@FXML private void onStockInButtonEntered() {
		stockInButton.setStyle("-fx-background-color: #e7e3e3");
	}
	
	@FXML private void onStockInButtonExited() {
		stockInButton.setStyle("-fx-background-color: #ffffff");
	}
	
	@FXML private void onStockOutbuttonClicked() {
		new StockOutStage(new Stage());
		StageManager.getStockWindow().close();
	}
	
	@FXML private void onStockOutButtonEntered() {
		stockOutButton.setStyle("-fx-background-color: #e7e3e3");
	}
	
	@FXML private void onStockOutButtonExited() {
		stockOutButton.setStyle("-fx-background-color: #ffffff");
	}
}
