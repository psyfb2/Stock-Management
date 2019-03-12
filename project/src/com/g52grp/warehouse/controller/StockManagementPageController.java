package com.g52grp.warehouse.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import org.controlsfx.control.textfield.TextFields;

import com.g52grp.database.Product;
import com.g52grp.stockout.ConcreteProductManager;
import com.g52grp.warehouse.model.BasicParameter;
import com.g52grp.warehouse.model.DisplayableProduct;
import com.g52grp.warehouse.model.HomePage;
import com.g52grp.warehouse.model.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * 
 * @author psyzh1 psyys4
 * Operation of StockManagementPage
 *
 */
public class StockManagementPageController {
	
    @FXML
    private Pane stockManagementPane;

    @FXML
    private Button homePageButton;

    @FXML
    private Button addButton;

    @FXML
    private ImageView addImage;

    @FXML
    private Button deleteButton;

    @FXML
    private ImageView deleteImage;

    @FXML
    private Button refreshButton;

    @FXML
    private ImageView refreshImage;

    @FXML
    private Button stockButton;

    @FXML
    private Button saveButton;

    @FXML
    private ImageView saveImage;

    @FXML
    private Label errorMessage;
    
	@FXML 
	private Text totalValue;
	@FXML 
	private Text mostUsedProduct;
	
	@FXML 
	private TextField searchProduct;	
	
	@FXML
	private Label totalValueLabel;
	
	@FXML
	private Label mostUsedProductLabel;

	@FXML private TableView<DisplayableProduct> stockTable;
	@FXML private TableColumn<DisplayableProduct, Integer>  idCol;
	@FXML private TableColumn<DisplayableProduct, String> codeCol;
	@FXML private TableColumn<DisplayableProduct, String> descriptionCol;
	@FXML private TableColumn<DisplayableProduct, Long> barcodeCol;
	//@FXML private TableColumn<DisplayableProduct, Integer> bayNoCol;
	//@FXML private TableColumn<DisplayableProduct, Integer> rowNoCol;
	@FXML private TableColumn<DisplayableProduct, Float> pricePerUnitCol;
	@FXML private TableColumn<DisplayableProduct, Integer> quantityCol;
	@FXML private TableColumn<DisplayableProduct, Integer> minQuantityCol;
	@FXML private TableColumn<DisplayableProduct, Boolean> deleteCol;
	
	private static ObservableList<DisplayableProduct> stocks =  FXCollections.observableArrayList();
	private ConcreteProductManager pm = new ConcreteProductManager(Main.con);
	
    @FXML
    private void initialize() {
    	
    	//initialize homePane
    	stockManagementPane.setPrefSize(BasicParameter.getScrSize().getWidth(), BasicParameter.getScrSize().getHeight());
    	stockManagementPane.setLayoutX(0);
    	 stockManagementPane.setLayoutY(0);
    	 
    	 
     	//initialize homePageButton
    	 homePageButton.setPrefSize(BasicParameter.getButton2Width(), BasicParameter.getButton2Height());
    	 homePageButton.setLayoutX(0);
    	 homePageButton.setLayoutY(0);

    	 //initialize stockButton
    	 stockButton.setPrefSize(BasicParameter.getButton2Width(), BasicParameter.getButton2Height());
    	 stockButton.setLayoutX(homePageButton.getPrefWidth());
    	 stockButton.setLayoutY(0);
    	 
     	//initialize addButton
    	 addButton.setPrefSize(BasicParameter.getButton3Width(), BasicParameter.getButton3Height());
    	 addButton.setLayoutX(BasicParameter.getScrSize().getWidth()/15);
    	 addButton.setLayoutY(homePageButton.getPrefHeight() + BasicParameter.getScrSize().getHeight()/100 + 2);
    	 
    	 //initialize addImage
    	 addImage.setFitWidth(BasicParameter.getButton3Width());
    	 addImage.setFitHeight(BasicParameter.getButton3Height());
    	 
     	// initialize deleteButton
    	 deleteButton.setPrefSize(BasicParameter.getButton3Width(), BasicParameter.getButton3Height());
       	 deleteButton.setLayoutX(addButton.getLayoutX() + addButton.getPrefWidth() + BasicParameter.getScrSize().getWidth()/40);
    	 deleteButton.setLayoutY(homePageButton.getPrefHeight() + BasicParameter.getScrSize().getHeight()/100 + 2);
    	 
    	 //initialize deleteImage
    	 deleteImage.setFitWidth(BasicParameter.getButton3Width());
    	 deleteImage.setFitHeight(BasicParameter.getButton3Height());
    	 
     	//initialize refreshButton
    	 refreshButton.setPrefSize(BasicParameter.getButton3Width(), BasicParameter.getButton3Height());
       	 refreshButton.setLayoutX(deleteButton.getLayoutX() + deleteButton.getPrefWidth() + BasicParameter.getScrSize().getWidth()/40);
    	 refreshButton.setLayoutY(homePageButton.getPrefHeight() + BasicParameter.getScrSize().getHeight()/100 + 2);
    	 
    	 //initialize refreshImage
    	 refreshImage.setFitWidth(BasicParameter.getButton3Width());
    	 refreshImage.setFitHeight(BasicParameter.getButton3Height());
    	 
    	 //initialize saveButton
    	 saveButton.setPrefSize(BasicParameter.getButton3Width(), BasicParameter.getButton3Height());
       	 saveButton.setLayoutX(refreshButton.getLayoutX() + deleteButton.getPrefWidth() + BasicParameter.getScrSize().getWidth()/40);
    	 saveButton.setLayoutY(homePageButton.getPrefHeight() + BasicParameter.getScrSize().getHeight()/100);
    	 saveButton.setVisible(false);
    	 
    	 //initialize saveImage
    	 saveImage.setFitWidth(BasicParameter.getButton3Width());
    	 saveImage.setFitHeight(BasicParameter.getButton3Height());
    	 
    	 //initialize stockTable
    	 
    	stockTable.setLayoutX(10);
    	stockTable.setLayoutY(addButton.getLayoutY() + addButton.getPrefHeight()+ 10);
    	stockTable.setPrefSize(BasicParameter.getScrSize().getWidth()-20, (BasicParameter.getScrSize().getHeight() - stockTable.getLayoutY())/4*3);
    	
 		idCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
 		idCol.setPrefWidth(stockTable.getPrefWidth()/30 *2);
 		
 		codeCol.setCellValueFactory(new PropertyValueFactory<>("productCode"));
 		codeCol.setPrefWidth(stockTable.getPrefWidth()/30 * 4);
 		
 		descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
 		descriptionCol.setPrefWidth(stockTable.getPrefWidth()/30 * 8);
 		
 		barcodeCol.setCellValueFactory(new PropertyValueFactory<>("barCode"));
 		barcodeCol.setPrefWidth(stockTable.getPrefWidth()/30 * 5);
 		
 		/*bayNoCol.setCellValueFactory(new PropertyValueFactory<>("bayNumber"));
 		bayNoCol.setPrefWidth(stockTable.getPrefWidth()/30 * 2);
 		
 		rowNoCol.setCellValueFactory(new PropertyValueFactory<>("rowNumber"));
 		rowNoCol.setPrefWidth(stockTable.getPrefWidth()/30*2);*/
 		
 		pricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("pricePerUnit"));
 		pricePerUnitCol.setPrefWidth(stockTable.getPrefWidth()/30*4);
 		
 		quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
 		quantityCol.setPrefWidth(stockTable.getPrefWidth()/30 *4);
 		
 		minQuantityCol.setCellValueFactory(new PropertyValueFactory<>("minQuantity"));
 		minQuantityCol.setPrefWidth(stockTable.getPrefWidth()/30 * 2.95);
 		
		deleteCol.setCellFactory(CheckBoxTableCell.forTableColumn(deleteCol));
	
		deleteCol.setCellValueFactory(new PropertyValueFactory<>("delete"));
		deleteCol.setVisible(false);
 		
		quantityCol.setCellFactory(new Callback<TableColumn<DisplayableProduct, Integer>, TableCell<DisplayableProduct, Integer>>() {
			@Override
			public TableCell<DisplayableProduct, Integer> call(TableColumn<DisplayableProduct, Integer> param) {
				return new TableCell<DisplayableProduct, Integer>(){
					@Override
					public void updateItem(Integer quantity, boolean empty) {
						super.updateItem(quantity, empty);
						if(!empty) {
							if(quantity < 5) {
								this.setStyle("-fx-background-color: FF3333;");															
							}else {
								this.setStyle("-fx-background-color: null;");
							}
							setText(Integer.toString(quantity));
						}else {
							setText(null);
							this.setStyle("-fx-background-color: null;");
						}
					}
				};
			}
		});
		//initialize searchProduct
		searchProduct.setPrefWidth(200);
		searchProduct.setLayoutX(BasicParameter.getScrSize().getWidth()/30 * 25 -200);
		searchProduct.setLayoutY(homePageButton.getPrefHeight() + addButton.getPrefHeight()/2);
		
		//initialize  mostUsedProduct
		 mostUsedProduct.setLayoutX(BasicParameter.getScrSize().getWidth()/30*7);
		 mostUsedProduct.setLayoutY(stockTable.getLayoutY() + stockTable.getPrefHeight() + (BasicParameter.getScrSize().getHeight() - stockTable.getLayoutY() + stockTable.getPrefHeight())/100*3);

		 // initialize totalValue
		 totalValue.setLayoutX(BasicParameter.getScrSize().getWidth()/30*7);
		 totalValue.setLayoutY(mostUsedProduct.getLayoutY() + (BasicParameter.getScrSize().getHeight() - stockTable.getLayoutY() + stockTable.getPrefHeight())/100 *3);
		

		 
		showProducts();
		showTotalValue();
		showMostUsedProduct();
		

		// auto complete text field
		ArrayList<Product> allProducts = pm.getAllProductsArrayList();		
		TextFields.bindAutoCompletion(searchProduct, input -> {
			if(input.getUserText().isEmpty() || allProducts == null) {
				return Collections.emptyList();
			}
			// search allProducts for a match with the input and return this list
			return allProducts.stream().filter(i -> {
				return i.toString().toLowerCase().contains(input.getUserText().toLowerCase());
			}).collect(Collectors.toList());
		});
		
		searchProduct.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if(key.getCode().equals(KeyCode.ENTER)) {
					String text = searchProduct.getText();
					int i = 0;
					for(DisplayableProduct product : stockTable.getItems()) {
						if((product.getProductCode() + " " + product.getDescription()).equals(text)) {
							stockTable.getSelectionModel().select(i);
						}
						i++;
					}
				}
			}
			
		});
		
		//initialize errorMessage
		errorMessage.setLayoutX(BasicParameter.getScrSize().getWidth()/2 - 200);
		errorMessage.setLayoutY(0);
    }
    
    
    @FXML
    void addButtonClicked() {

    }

    /**
     * return to the HomePage
     */
    @FXML
    void homePageButtonClicked() {
    	Stage theStage = (Stage)homePageButton.getScene().getWindow();
    	try {
			new HomePage(theStage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void refreshButtonClicked(MouseEvent event) {

    }
    
    /**
     * Setting delete row and save button to be 
     * visible and allow user to delete column.
     */
	 @FXML
	 private void deleteButtonClicked() {
 		idCol.setPrefWidth(stockTable.getPrefWidth()/100 *7);
 		
 		codeCol.setPrefWidth(stockTable.getPrefWidth()/100 *11);
 		
 		descriptionCol.setPrefWidth(stockTable.getPrefWidth()/100 * 26);
 		
 		barcodeCol.setPrefWidth(stockTable.getPrefWidth()/100 * 16);
 		
 		//bayNoCol.setPrefWidth(stockTable.getPrefWidth()/100 * 7);
 		
 		//rowNoCol.setPrefWidth(stockTable.getPrefWidth()/100 * 7);
 		
 		pricePerUnitCol.setPrefWidth(stockTable.getPrefWidth()/100 * 8);
 		
 		quantityCol.setPrefWidth(stockTable.getPrefWidth() / 100 * 8);
 		
 		minQuantityCol.setPrefWidth(stockTable.getPrefWidth() / 100 * 8);
 		
 		deleteCol.setPrefWidth(stockTable.getPrefWidth() / 100 * 9);
 		
		deleteCol.setVisible(true);
		stockTable.setEditable(true);	
		saveButton.setVisible(true);
	}
	
	 /**
	  * Save user delete operation and set save button
	  * and delete column to be invisible.
	  */
	@FXML
	private void saveButtonClicked() {
		for(DisplayableProduct product : stockTable.getItems()) {
			if(product.getDelete()) {
				int id = product.getProductId();
				ArrayList<Integer> jobIDs = pm.checkProductInUsed(id);
				if(jobIDs != null) {
					String warningText = product.getDescription() + " is used in job ";
					for(int i = 0; i < jobIDs.size(); i++) {
						if(i == jobIDs.size() - 1) {
							warningText += jobIDs.get(i) + ". ";
						}else {
							warningText += jobIDs.get(i) + ", ";
						}						
					}
					
					warningText += "\nPlease delete the job(s) first.";
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("DeleteWarning");
					alert.setHeaderText("");
					alert.setContentText(warningText);
					alert.showAndWait();
				}else {
					if(!pm.deleteProduct(id)) {
						errorMessage.setText("Failed to delete products: error accessing database");
						errorMessage.setVisible(true);
						return;
					}
				}
				
			}
		}
		
 		idCol.setPrefWidth(stockTable.getPrefWidth()/30 *2);

 		codeCol.setPrefWidth(stockTable.getPrefWidth()/30 * 4);

 		descriptionCol.setPrefWidth(stockTable.getPrefWidth()/30 * 8);
 		
 		barcodeCol.setPrefWidth(stockTable.getPrefWidth()/30 * 5);

 		//bayNoCol.setPrefWidth(stockTable.getPrefWidth()/30 * 2);

 		//rowNoCol.setPrefWidth(stockTable.getPrefWidth()/30*2);

 		pricePerUnitCol.setPrefWidth(stockTable.getPrefWidth()/30*4);

 		quantityCol.setPrefWidth(stockTable.getPrefWidth()/30 *4);

 		minQuantityCol.setPrefWidth(stockTable.getPrefWidth()/30 * 3);
		showProducts();
		saveButton.setVisible(false);
		deleteCol.setVisible(false);
		saveButton.setVisible(false);
	}
	
	/**
	 * Refresh stockTable 
	 */
	private void showProducts() {
		stocks.removeAll(stocks);
		ArrayList<Product> allProducts = pm.getAllProductsArrayList();
		if(allProducts == null) {
			errorMessage.setText("Failed to load products: error accessing database");
			errorMessage.setVisible(true);
			return;
		}
		
		for(Product product : allProducts) {
			stocks.add(new DisplayableProduct(product.getProductId(), product.getProductCode(),
					product.getDescription(), product.getBarCode(),  product.getPricePerUnit(), 
					product.getStock(), 5));
		}		
		stockTable.setItems(stocks);
	}
	
	/**
	 * Change totalValue Text with most used product.
	 */
	private void showTotalValue() {
		double value = 0;
		String textInfo = "total value: ";
		for(DisplayableProduct product : stockTable.getItems()) {
			value += product.getPricePerUnit() * product.getQuantity();
		}
		totalValue.setText( textInfo + Double.toString(value));
	}
	
	/**
	 * Change mustUsedProduct Text with most used product.
	 */
	private void showMostUsedProduct() {
		String product = pm.getMostUsedProduct();
		String textInfo = "most used product: ";
		if(product == null) {
			errorMessage.setText("Failed to load most used product: error accessing database");
			errorMessage.setVisible(true);
			return;
		}
		mostUsedProduct.setText( textInfo + product);
	}
}
