package com.g52grp.views;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import com.g52grp.database.JobProduct;
import com.g52grp.database.Product;
import com.g52grp.main.Main;
import com.g52grp.stockout.ConcreteProductManager;
import com.g52grp.stockout.ProductManager;

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
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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
	@FXML TableColumn<DisplayableJobProduct, String> quantityUsed; // String because needs to be editable
	@FXML TableColumn<DisplayableJobProduct, Integer> stocksRemaining;
	@FXML TableColumn<DisplayableJobProduct, Integer> productId; // hidden from the user
	@FXML TableColumn<DisplayableJobProduct, Long> barcode; // hidden from the user
	@FXML Button deleteJob;
	@FXML Button backButton;
	@FXML Label errorMessage;
	
	/**
	 * Called when the back button is clicked (to go back to the Job Menu)
	 * @param e
	 */
	@FXML public void back(ActionEvent e) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource(Main.JOBMENUPATH_FXML));
			Parent root = loader.load();
			
	        Scene jobMenuView = new Scene( root );
	        
	        Stage theStage = (Stage) (((Node) e.getSource()).getScene().getWindow());
	        
	        theStage.setScene( jobMenuView );
	        theStage.show();
		} catch(Exception ex) {
			errorMessage.setText("Failed to load " + Main.JOBMENUPATH_FXML);
		}
	}
	
	/**
	 * Called when the user attempts to change the quantity used for a product within the TableView (no other column is editable) 
	 * @param e
	 */
	@FXML public void changeQuantityUsedCellEvent(@SuppressWarnings("rawtypes") CellEditEvent edittedCell) {
		errorMessage.setText("");
		DisplayableJobProduct jobProductSelected = jobProductTable.getSelectionModel().getSelectedItem();
		int newQuantityUsed;
		int oldQuantityUsed;
		try {
			newQuantityUsed = Integer.parseInt(edittedCell.getNewValue().toString());
			oldQuantityUsed = Integer.parseInt(jobProductSelected.getQuantity());
		} catch(NumberFormatException e) {
			errorMessage.setText("Only enter numerical values for quantity");
			jobProductTable.refresh();
			return;
		}
		
		if(newQuantityUsed < 1) {
			errorMessage.setText("Products must have a minimum quantity used of 1");
			jobProductTable.refresh();
			return;
		}
		if(newQuantityUsed == oldQuantityUsed ) {
			return;
		}
		
		// need to confirm that there is enough stocks
		int stockReduction = newQuantityUsed - oldQuantityUsed;
		if( stockReduction <= jobProductSelected.getStocksRemaining() ) {
			// convert displayable job product to normal job product, also pass stockReduction to decreaseStocks()
			// because this is the number we would like to decrease the stocks by
			JobProduct jobProduct = new JobProduct(jobId, new Product(jobProductSelected.getProductId(), 
					jobProductSelected.getProductCode(), jobProductSelected.getDescription(),
					0, 0, jobProductSelected.getPrice(), jobProductSelected.getStocksRemaining(), 
					jobProductSelected.getBarcode()), newQuantityUsed);
			if(!pm.decreaseStocks(jobProduct)) {
				errorMessage.setText("Failed to modify quantity: error accessing database");
				jobProductTable.refresh();
				return;
			}
			
			// update table so it matches the database, could call updateTableView() but this is costly
			jobProductSelected.setQuantity(Integer.toString(newQuantityUsed));
			jobProductSelected.setStocksRemaining(jobProductSelected.getStocksRemaining() - stockReduction);
			jobProductTable.refresh();
		} else {
			errorMessage.setText("You cannot change the quantity used from " + jobProductSelected.getQuantity() + " to " + newQuantityUsed + " because you do not have enough stocks");
			jobProductTable.refresh();
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
	public void initData(int jobId, String jobTitle) {
		this.jobId = jobId;
		this.jobTitle.setText(jobTitle);
		updateTableView();
	}
	
	/**
	 * Loads products associated with the jobId given in the initData() method
	 * @return List of DisplayableJobProduct objects
	 */
	public ObservableList<DisplayableJobProduct> getJobProducts() {
		ObservableList<DisplayableJobProduct> productsForThisJobList = FXCollections.observableArrayList();
		if(jobId == -1) {
			errorMessage.setText("Failed to load associated products: jobID was not passed to this controller");
			return productsForThisJobList;
		}
		
		JobProduct[] productsForThisJobArr = pm.getProductsFromJobId(jobId);
		if(productsForThisJobArr == null) {
			errorMessage.setText("Failed to load associated products: error accessing database");
			return productsForThisJobList;
		}
		for(JobProduct jp : productsForThisJobArr) {
			Product p = jp.getProduct();
			productsForThisJobList.add(new DisplayableJobProduct(p.getProductId(),
					p.getProductCode(), p.getDescription(), p.getPricePerUnit(), Integer.toString(jp.getQuantityUsed()), p.getStock(), p.getBarCode()) );
		}
		
		return productsForThisJobList;
	}
	
	@Override
	public void updateTableView() {
		ObservableList<DisplayableJobProduct> productsForThisJob = jobProductTable.getItems();
		productsForThisJob.removeAll();
		jobProductTable.setItems(getJobProducts());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// add back icon to back button
		try {
			ImageView buttonIcon = new ImageView(new Image(new FileInputStream(Main.BACKIMAGEPATH)));
			buttonIcon.setFitWidth(100);
			buttonIcon.setFitHeight(50);
			backButton.setGraphic(buttonIcon);
		} catch(Exception e) {
			errorMessage.setText("Failed to load " + Main.BACKIMAGEPATH);
		}
		
		// initiliaze TableView columns
		productId.setCellValueFactory(new PropertyValueFactory<>("productId"));
		productCode.setCellValueFactory(new PropertyValueFactory<>("productCode"));
		description.setCellValueFactory(new PropertyValueFactory<>("description"));
		price.setCellValueFactory(new PropertyValueFactory<>("price"));
		quantityUsed.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		stocksRemaining.setCellValueFactory(new PropertyValueFactory<>("stocksRemaining"));
		barcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));

		
		// make the quantity column editable
		jobProductTable.setEditable(true);
		quantityUsed.setCellFactory(TextFieldTableCell.forTableColumn());
		
		// population of the table initially is done in initData()
		// reason for this is initialize (this method) is called before jobId can be passed to this class using initData() 
	}
}
