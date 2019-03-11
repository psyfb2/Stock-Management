package com.g52grp.views;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.controlsfx.control.textfield.TextFields;

import com.g52grp.database.JobProduct;
import com.g52grp.database.Product;
import com.g52grp.main.Main;
import com.g52grp.stockout.ConcreteJobManager;
import com.g52grp.stockout.ConcreteProductManager;
import com.g52grp.stockout.JobManager;
import com.g52grp.stockout.ProductManager;

import javafx.application.Platform;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author psyfb2
 * View information about a selected job including
 * 		Products registered with this job and there quantity
 * 		Add new product and quantity used to this job (will decrease stocks for that product)
 * 		Do the above however allow products to be inputted into the system with barcode scanner (default quantity used is 1)
 * 		change quantity used for a product (needs to be validated with current stocks of that product, then stocks updated by difference between old quantity used and new quantity used)
 * 		Delete the job
 */
public class SingleJobController implements Initializable, TableViewUpdate {
	private int jobId;
	private ProductManager pm;
	private JobManager jm;
	@FXML Label jobTitle;
	@FXML Label totalPrice;
	@FXML TableView<DisplayableJobProduct> jobProductTable;
	@FXML TableColumn<DisplayableJobProduct, String> productCode;
	@FXML TableColumn<DisplayableJobProduct, String> description;
	@FXML TableColumn<DisplayableJobProduct, Float> price;
	@FXML TableColumn<DisplayableJobProduct, String> quantityUsed; // String because needs to be editable
	@FXML TableColumn<DisplayableJobProduct, Integer> stocksRemaining;
	@FXML TableColumn<DisplayableJobProduct, Integer> productId; // hidden from the user
	@FXML TableColumn<DisplayableJobProduct, String> barcode; // hidden from the user
	@FXML Button deleteJobButton;
	@FXML Button backButton;
	@FXML Label errorMessage;
	@FXML TextField searchProductToAdd;
	@FXML RadioButton scanInRadioButton; // selected by default
	@FXML RadioButton scanOutRadioButton;
	
	// barcode scanner is recognised as a keyboard
	// send its input to this text hidden text field
	@FXML TextField barcodeHiddenInput;
	@FXML AnchorPane root;
	
	@FXML public void focusBarcodeHiddenInput(MouseEvent e) {
		barcodeHiddenInput.requestFocus();
	}
	
	@FXML public void barcodeScanned(ActionEvent e) {
		errorMessage.setText("");
		int minLength = 4;
		String barcode = barcodeHiddenInput.getText();
		
		if(!barcode.matches("[0-9]+")) {
			return;
		}
		
		if(barcode.length() < minLength) {
			return;
		}
		
		if(scanInRadioButton.isSelected()) {
			// register scanned product to this job with quantity used 1
			// if the product is already registered then just reduce quantity used by 1 if possible
			Product p = pm.getProductFromBarcode(barcode);
			if(p == null) {
				errorMessage.setText("Product with barcode '" + barcode + "' was not found");
				return;
			}
			if(p.getStock() < 1) {
				errorMessage.setText("The Product '" + p.toString() + "' does not have enough stocks");
				return;
			}
			if(!pm.decreaseStocks(new JobProduct(jobId, p, 1))) {
				errorMessage.setText("Failed to modify quantity: error accessing database");
			}
			
			
		} else {
			
		}
		barcodeHiddenInput.setText("");
	}

	/**
	 * Called when the back button is clicked (to go back to the Job Menu)
	 * @param e
	 */
	@FXML public void back(ActionEvent e) {
		errorMessage.setText("");
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
	 * Called when the delete job button is clicked, A delete job modal will be loaded to confirm this with the user
	 * @param e
	 */
	@FXML public void deleteJob(ActionEvent e) {
		Alert confirmation = new Alert(AlertType.CONFIRMATION);
		confirmation.setTitle("Delete Job?");
		confirmation.setHeaderText(null);
		confirmation.setContentText("Are you sure you want to permenantly delete this job?");
		// add the RJB logo to the dialog box
		Stage stage = (Stage) confirmation.getDialogPane().getScene().getWindow();
		try {
			stage.getIcons().add(new Image(new FileInputStream(Main.LOGOPATH)));
		} catch (FileNotFoundException ex) {
		}
		Optional <ButtonType> okButton = confirmation.showAndWait();
		
		if(okButton.get() == ButtonType.OK) {
			jm.deleteJob(jobId);
			back(e);
		}
	}
	
	/**
	 * Called when the user hits enter on the searchproductToAdd text field
	 * This will add the product to the job if the product is not already within the job
	 * @param e
	 */
	@FXML public void addProductToJobManual(ActionEvent e) {
		// get the product code and description (separated by a space) from the user input
		// Note that although the productCode and description are not part of the PRIMARY KEY
		// they are marked as unique together, so no two rows can have the same product code and description
		errorMessage.setText("");
		if(jobId == -1) {
			return;
		}
		String input = searchProductToAdd.getText();
		String productcode;
		String description;
		try {
			int splitter = input.indexOf(' ');
			productcode = input.substring(0, splitter);
			description = input.substring(splitter + 1, input.length());
		} catch(IndexOutOfBoundsException ex) {
			errorMessage.setText("'" + input + "' was not found. Please enter the exact product code and description");
			return;
		}
		Product matchedProduct = pm.getProductFromProductcodeAndDescription(productcode, description);
		if(matchedProduct == null) {
			errorMessage.setText("'" + input + "' was not found. Please enter the exact product code and description");
			return;
		}
		
		// add this product to the job with quantity used 1
		// if the product already exists for the job then do nothing
		if(!pm.addProductToJob(jobId, matchedProduct.getProductId())) {
			errorMessage.setText("Failed to add product: '" + input + "' has 0 stock remaining");
		} else {
			updateTableView();
			searchProductToAdd.setText("");
		}
	}
	
	/**
	 * Called when the user attempts to change the quantity used for a product within the TableView (no other column is editable) 
	 * @param e
	 */
	@FXML public void changeQuantityUsedCellEvent(@SuppressWarnings("rawtypes") CellEditEvent edittedCell) {
		errorMessage.setText("");
		DisplayableJobProduct jobProductSelected = jobProductTable.getSelectionModel().getSelectedItem();
		barcodeHiddenInput.requestFocus();
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
		
		boolean removeProduct = false;
		if(newQuantityUsed == 0) {
			// allow the user to delete a product from a job if they set quantity used to 0
			// display a confirmation modal to confirm this with the user
			removeProduct = removeProductFromJob(jobProductSelected.getProductCode() + jobProductSelected.getDescription());
			if(!removeProduct) {
				jobProductTable.refresh();
				return;
			}
		}
		
		if(newQuantityUsed < 0) {
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
					jobProductSelected.getPrice(), jobProductSelected.getStocksRemaining(), 
					jobProductSelected.getBarcode()), stockReduction);
			
			if(!pm.decreaseStocks(jobProduct)) {
				errorMessage.setText("Failed to modify quantity: error accessing database");
				jobProductTable.refresh();
				return;
			}
			
			if(removeProduct) {
				if(!pm.removeProductFromJob(jobId, jobProductSelected.getProductId())) {
					errorMessage.setText("Failed to modify quantity: error accessing database");
					jobProductTable.refresh();
					return;
				}
				updateTableView();
				return;
			}
			
			// update table so it matches the database, could call updateTableView() but this is costly
			jobProductSelected.setQuantity(Integer.toString(newQuantityUsed));
			jobProductSelected.setStocksRemaining(jobProductSelected.getStocksRemaining() - stockReduction);
			totalPrice.setText(Float.toString(Float.parseFloat(totalPrice.getText()) + stockReduction * jobProductSelected.getPrice()));
			jobProductTable.refresh();
		} else {
			errorMessage.setText("You cannot change the quantity used from " + jobProductSelected.getQuantity() + " to " + newQuantityUsed + " because you do not have enough stocks");
			jobProductTable.refresh();
		}
	}
	
	public SingleJobController() {
		jobId = -1;
		pm = new ConcreteProductManager(Main.con);
		jm = new ConcreteJobManager(Main.con);
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
	 * Also loads the initial total price of job into totalPrice label
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
		
		float currentTotalPrice = 0;
		for(JobProduct jp : productsForThisJobArr) {
			Product p = jp.getProduct();
			currentTotalPrice += p.getPricePerUnit() * jp.getQuantityUsed();
			productsForThisJobList.add(new DisplayableJobProduct(p.getProductId(),
					p.getProductCode(), p.getDescription(), p.getPricePerUnit(), Integer.toString(jp.getQuantityUsed()), p.getStock(), p.getBarCode()) );
		}
		
		totalPrice.setText(Float.toString(currentTotalPrice));
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
			buttonIcon.setFitWidth(50);
			buttonIcon.setFitHeight(50);
			buttonIcon.setPreserveRatio(true);
			backButton.setGraphic(buttonIcon);
		} catch(Exception e) {
			errorMessage.setText("Failed to load: " + Main.BACKIMAGEPATH);
		}
		
		// add delete icon to delete button
		try {
			ImageView buttonIcon = new ImageView(new Image(new FileInputStream(Main.DELETEIMAGEPATH)));
			buttonIcon.setFitWidth(35);
			buttonIcon.setFitHeight(35);
			buttonIcon.setPreserveRatio(true);
			deleteJobButton.setGraphic(buttonIcon);
		} catch (Exception e) {
			errorMessage.setText("Failed to load: " + Main.DELETEIMAGEPATH);
		}
		
		// add event handlers for barcode scanner
		Platform.runLater(() -> {
			barcodeHiddenInput.requestFocus();
			barcodeHiddenInput.getScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
				if(key.getCode() == KeyCode.ENTER) {
					barcodeScanned(new ActionEvent());
				}
				else if(!key.getText().matches("[0-9]+")) {
					barcodeHiddenInput.setText("");
				}
				else {
					barcodeHiddenInput.setText(barcodeHiddenInput.getText() + key.getText());
				}
	      });
		});
		
		// auto complete text field
		ArrayList<Product> allProducts = pm.getAllProductsArrayList();
		
		TextFields.bindAutoCompletion(searchProductToAdd, input -> {
			if(input.getUserText().isEmpty() || allProducts == null) {
				return Collections.emptyList();
			}
			// search allProducts for a match with the input and return this list
			return allProducts.stream().filter(i -> {
				return i.toString().toLowerCase().contains(input.getUserText().toLowerCase());
			}).collect(Collectors.toList());
		});
		
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
	
	/**
	 * Displays confirmation to the user if they would like to delete the given product from this job
	 * @param productName The product to remove (this is displayed to the user)
	 * @return true if user clicked ok button, false if they cancelled
	 */
	private boolean removeProductFromJob(String productName) {
		Alert confirmation = new Alert(AlertType.CONFIRMATION);
		confirmation.setTitle("Remove Product?");
		confirmation.setHeaderText(null);
		confirmation.setContentText("Setting the quantity to 0 will remove " + productName + " from this job. Are you sure you would like to do this?");
		// add the RJB logo to the dialog box
		Stage stage = (Stage) confirmation.getDialogPane().getScene().getWindow();
		try {
			stage.getIcons().add(new Image(new FileInputStream(Main.LOGOPATH)));
		} catch (FileNotFoundException ex) {
		}
		Optional <ButtonType> okButton = confirmation.showAndWait();
		
		if(okButton.get() == ButtonType.OK) {
			return true;
		}
		return false;
	}
}
