package com.g52grp.views;

import java.net.URL;
import java.util.ResourceBundle;

import com.g52grp.database.Job;
import com.g52grp.database.JobProduct;
import com.g52grp.main.Main;
import com.g52grp.stockout.ConcreteProductManager;
import com.g52grp.stockout.ProductManager;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author psyfb2
 * View information about a selected job including
 * 		Products registered with this job and there quantity
 * 		Add new product and quantity used to this job (will decrease stocks for that product)
 * 		Do the above however allow products to be inputted into the system with barcode scanner (default quantity used is 1)
 * 		change quantity used for a product (needs to be validated with current stocks of that product, then stocks updated by difference between old quantity used and new quantity used)
 */
public class SingleJobController implements Initializable, TableViewUpdate {
	private int jobId;
	private ProductManager pm;
	@FXML Label jobTitle;
	@FXML Label totalPrice;
	@FXML TableView<DisplayableJobProduct> jobProductTable;
	@FXML TableColumn<DisplayableJobProduct, String> productCode;
	@FXML TableColumn<DisplayableJobProduct, String> description;
	@FXML TableColumn<DisplayableJobProduct, Float> price;
	@FXML TableColumn<DisplayableJobProduct, Integer> quantityUsed;
	@FXML TableColumn<DisplayableJobProduct, Integer> stocksRemaining;
	@FXML TableColumn<DisplayableJobProduct, Integer> productId; // hidden from the user
	@FXML Button deleteJob;
	@FXML Button backButton;
	
	public SingleJobController() {
		jobId = -1;
		pm = new ConcreteProductManager(Main.con);
	}
	
	public void initData(int jobId) {
		this.jobId = jobId;
	}
	
	
	/**
	 * Loads products associated with the jobId given in the initData method
	 * @return
	 */
	public ObservableList<DisplayableJobProduct> getJobProducts() {
		ObservableList<DisplayableJobProduct> productForThisJobList = FXCollections.observableArrayList();
		if(jobId == -1) {
			return productForThisJobList;
		}
		JobProduct[] productForThisJobArr = pm.getProductsFromJobId(jobId);
	}
	
	@Override
	public void updateTableView() {
		ObservableList<DisplayableJobProduct> productsForThisJob = jobProductTable.getItems();
		if(jobProductTable != null) {
			productsForThisJob.removeAll();
		}
		productsForThisJob.setItems(getJobs());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// add back icon to back button
		backButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(Main.BACKIMAGEPATH))));
		
		// initiliaze TableView columns
		productId.setCellValueFactory(new PropertyValueFactory<>("productId"));
		productCode.setCellValueFactory(new PropertyValueFactory<>("productCode"));
		description.setCellValueFactory(new PropertyValueFactory<>("description"));
		price.setCellValueFactory(new PropertyValueFactory<>("price"));
		quantityUsed.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		stocksRemaining.setCellValueFactory(new PropertyValueFactory<>("stocksRemaining"));
		
		// populate table
		updateTableView();
	}
	
	// JobProduct contains a product object (which cannot be displayed within the TableView)
	// so displayableJobProduct is a flat version which only contains Strings, Integer etc
	// also here we use SimpleStringProperty, SimpleIntegerProperty etc to allow columns to be editable
	class DisplayableJobProduct {
		private SimpleIntegerProperty productId;
		private SimpleStringProperty productCode;
		private SimpleStringProperty description;
		private SimpleFloatProperty price;
		private SimpleIntegerProperty quantity;
		private SimpleIntegerProperty stocksRemaining;
		
		public DisplayableJobProduct(int productId, String productCode,
				String description, float price, int quantity,
				int stocksRemaining) {
			this.productId = new SimpleIntegerProperty(productId);
			this.productCode = new SimpleStringProperty(productCode);
			this.description = new SimpleStringProperty(description);
			this.price = new SimpleFloatProperty(price);
			this.quantity = new SimpleIntegerProperty(quantity);
			this.stocksRemaining = new SimpleIntegerProperty(stocksRemaining);
		}

		public int getProductId() {
			return productId.get();
		}

		public void setProductId(int productId) {
			this.productId = new SimpleIntegerProperty(productId);
		}

		public String getProductCode() {
			return productCode.get();
		}

		public void setProductCode(String productCode) {
			this.productCode = new SimpleStringProperty(productCode);
		}

		public String getDescription() {
			return description.get();
		}

		public void setDescription(String description) {
			this.description = new SimpleStringProperty(description);
		}

		public float getPrice() {
			return price.get();
		}

		public void setPrice(float price) {
			this.price = new SimpleFloatProperty(price);
		}

		public int getQuantity() {
			return quantity.get();
		}

		public void setQuantity(int quantity) {
			this.quantity = new SimpleIntegerProperty(quantity);
		}

		public int getStocksRemaining() {
			return stocksRemaining.get();
		}

		public void setStocksRemaining(int stocksRemaining) {
			this.stocksRemaining = new SimpleIntegerProperty(stocksRemaining);
		}
	}
}
