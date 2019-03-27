package model;

import javafx.stage.Stage;

public class StageManager {
	private static Stage stockWindow = null;
	private static Stage AddJobWindow = null;
	
	public static Stage getStockWindow() {
		return stockWindow;
	}
	public static void setStockWindow(Stage stockWindow) {
		StageManager.stockWindow = stockWindow;
	}
	public static Stage getAddJobWindow() {
		return AddJobWindow;
	}
	public static void setAddJobWindow(Stage addJobWindow) {
		AddJobWindow = addJobWindow;
	}
	
	public static void stockWindowClose() {
		if(stockWindow != null) {
			stockWindow.close();
			stockWindow = null;
		}
	}
	
	public static void addJobWindowClose() {
		if(AddJobWindow != null) {
			AddJobWindow.close();
			AddJobWindow = null;
		}
	}
}
