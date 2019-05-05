package com.g52grp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import com.g52grp.backend.ConcreteJobManager;
import com.g52grp.backend.ConcreteProductManager;
import com.g52grp.backend.JobManager;
import com.g52grp.backend.ProductManager;
import com.g52grp.database.JobProduct;
import com.g52grp.database.Product;
import com.g52grp.main.Main;
import com.g52grp.pageloaders.HomePage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

/**
 * 
 * View information about a selected job including
 * 		Products registered with this job and there quantity
 * 		Delete the job
 * @author psyzh1 psyfb2
 */
public class ArchivedJobController implements Initializable, TableViewUpdate {
	private int jobId;
	private ProductManager pm;
	private JobManager jm;
	private boolean isArchived;
	
	@FXML Label jobTitle;
	@FXML Label totalPrice;
	
	@FXML TableView<DisplayableJobProduct> jobProductTable;
	@FXML TableColumn<DisplayableJobProduct, String> productCode;
	@FXML TableColumn<DisplayableJobProduct, String> description;
	@FXML TableColumn<DisplayableJobProduct, Float> price;
	@FXML TableColumn<DisplayableJobProduct, String> quantityUsed; // String because needs to be editable
	@FXML TableColumn<DisplayableJobProduct, Integer> stocksRemaining;
	@FXML TableColumn<DisplayableJobProduct, Float> priceOfRow;
	@FXML TableColumn<DisplayableJobProduct, Integer> productId; // hidden from the user
	@FXML TableColumn<DisplayableJobProduct, String> barcode; // hidden from the user
	
	@FXML Button deleteJobButton;
	@FXML Button unArchiveJobButton;
	@FXML Label errorMessage;
	@FXML Button homePageButton;
	
	public ArchivedJobController() {
		jobId = -1;
		pm = new ConcreteProductManager(Main.con);
		jm = new ConcreteJobManager(Main.con);
	}
	
	/**
	 * Label this job as archived and move to the job menu.
	 * @param e ActionEvent
	 */
	@FXML public void archiveJob(ActionEvent e) {
		errorMessage.setText("");
		
		if(isArchived) {
			if(!jm.unarchiveJob(jobId)) {
				errorMessage.setText("Failed to archive job: error accessing database");
				return;
			}
		} else {
			if (!jm.archiveJob(jobId)){
				errorMessage.setText("Failed to archive job: error accessing database");
				return;
			}
		}
		
		back(e);
	}
	

	/**
	 * Called when the back button is clicked (to go back to the Job Menu)
	 * @param e ActionEvent
	 */
	@FXML public void back(ActionEvent e) {
		try {
			FXMLLoader loader = Main.getFXMLFile(getClass(), Main.JOBMENUPATH_FXML);
			Parent root = loader.load();
	        Stage theStage = (Stage) (((Node) e.getSource()).getScene().getWindow());
			JobMenuController c = loader.<JobMenuController>getController();
			c.ChangeText();
	        theStage.setTitle("Job Menu");
	        theStage.getScene().setRoot(root);
	        theStage.show();
		} catch(Exception ex) {
			errorMessage.setText("Failed to load " + Main.JOBMENUPATH_FXML);
		}
	}
	
	/**
	 * Called when the delete job button is clicked, A delete job modal will be loaded to confirm this with the user
	 * @param e ActionEvent
	 */
	@FXML public void deleteJob(ActionEvent e) {
		Alert confirmation = new Alert(AlertType.CONFIRMATION);
		confirmation.setTitle("Delete Job?");
		confirmation.setHeaderText(null);
		confirmation.setContentText("Are you sure you want to permenantly delete this job?");
		// add the RJB logo to the dialog box
		Stage stage = (Stage) confirmation.getDialogPane().getScene().getWindow();
		stage.getIcons().add(Main.getImageResource(Main.LOGOPATH));
		Optional <ButtonType> okButton = confirmation.showAndWait();
		
		if(okButton.get() == ButtonType.OK) {
			// ok button was clicked, delete this job from the database
			jm.deleteJob(jobId);
			back(e);
		}
	}
	
	@FXML private void goToHomePage() throws IOException {
			Stage theStage = (Stage) homePageButton.getScene().getWindow();
			new HomePage(theStage);
	}
	
	/**
	 * Call this from other controllers to pass information to this controller when switching scenes
	 * @param jobId jobID within the mysql database of the job to view
	 * @param jobTitle title of the job to be displayed at the top of the page
	 */
	public void initData(int jobId, String jobTitle) {
		this.jobId = jobId;
		this.jobTitle.setText(jobTitle);
		isArchived = jm.isArchived(jobId);
		
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
		float totalPrice = 0;
		for(JobProduct jp : productsForThisJobArr) {
			Product p = jp.getProduct();
			productsForThisJobList.add(new DisplayableJobProduct(p.getProductId(),
					p.getProductCode(), p.getDescription(), p.getPricePerUnit(), Integer.toString(jp.getQuantityUsed()), p.getStock(), p.getBarCode(), p.getMinQuantity()) );
			totalPrice += p.getPricePerUnit() * jp.getQuantityUsed();
		}
		this.totalPrice.setText("Price Of Job: £" + Float.toString(totalPrice));
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
		ArrayList<Product> allProducts = pm.getAllProductsArrayList();
		
		// initiliaze TableView columns
		productId.setCellValueFactory(new PropertyValueFactory<>("productId"));
		productCode.setCellValueFactory(new PropertyValueFactory<>("productCode"));
		description.setCellValueFactory(new PropertyValueFactory<>("description"));
		price.setCellValueFactory(new PropertyValueFactory<>("price"));
		quantityUsed.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		stocksRemaining.setCellValueFactory(new PropertyValueFactory<>("stocksRemaining"));
		priceOfRow.setCellValueFactory(new PropertyValueFactory<>("priceOfRow"));
		barcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));

		// make the quantity column editable
		jobProductTable.setEditable(true);
		quantityUsed.setCellFactory(TextFieldTableCell.forTableColumn());
		
	}
	
}
