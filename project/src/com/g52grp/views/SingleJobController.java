package com.g52grp.views;

import java.net.URL;
import java.util.ResourceBundle;

import com.g52grp.database.JobProduct;
import com.g52grp.database.Product;
import com.g52grp.main.Main;
import com.g52grp.stockout.ConcreteProductManager;
import com.g52grp.stockout.ProductManager;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

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
	
	/**
	 * Called when the back button is clicked (to go back to the Job Menu)
	 * @param e
	 */
	@FXML public void back(ActionEvent e) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(Main.JOBMENUPATH_FXML));
			Parent root = loader.load();
			
	        Scene jobMenuView = new Scene( root );
	        
	        Stage theStage = (Stage) (((Node) e.getSource()).getScene().getWindow());
	        
	        theStage.setScene( jobMenuView );
	        theStage.show();
		} catch(Exception ex) {
			
		}
	}
	
	public SingleJobController() {
		jobId = -1;
		pm = new ConcreteProductManager(Main.con);
	}
	
	/**
	 * Call this from other controllers to pass information to this controller when switching scenes
	 * @param jobId jobID within the mysql database of the job to view
	 */
	public void initData(int jobId) {
		this.jobId = jobId;
	}
	
	
	/**
	 * Loads products associated with the jobId given in the initData() method
	 * @return List of DisplayableJobProduct objects
	 */
	public ObservableList<DisplayableJobProduct> getJobProducts() {
		ObservableList<DisplayableJobProduct> productsForThisJobList = FXCollections.observableArrayList();
		
		if(jobId == -1) {
			return productsForThisJobList;
		}
		
		JobProduct[] productsForThisJobArr = pm.getProductsFromJobId(jobId);
		for(JobProduct jp : productsForThisJobArr) {
			Product p = jp.getProduct();
			productsForThisJobList.add(new DisplayableJobProduct(p.getProductId(),
					p.getProductCode(), p.getDescription(), p.getPricePerUnit(), jp.getQuantityUsed(), p.getStock()) );
		}
		
		return productsForThisJobList;
	}
	
	@Override
	public void updateTableView() {
		ObservableList<DisplayableJobProduct> productsForThisJob = jobProductTable.getItems();
		if(productsForThisJob != null) {
			productsForThisJob.removeAll();
		}
		jobProductTable.setItems(getJobProducts());
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
