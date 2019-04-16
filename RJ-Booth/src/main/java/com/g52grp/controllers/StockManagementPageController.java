package com.g52grp.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import org.controlsfx.control.textfield.TextFields;

import com.csvreader.CsvReader;
import com.g52grp.backend.ConcreteProductManager;
import com.g52grp.database.Product;
import com.g52grp.main.Main;
import com.g52grp.pageloaders.AddProductPage;
import com.g52grp.pageloaders.DisplayableProduct;
import com.g52grp.pageloaders.HomePage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * 
 * @author psyzh1 psyys4
 * Operation of StockManagementPage
 *
 */
public class StockManagementPageController implements TableViewUpdate{
	
    @FXML
    private GridPane pane;

    @FXML
    private Button stockButton;

    @FXML
    private Button homePageButton;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button importButton;

    @FXML
    private Button cancelButton;
    
    @FXML
    private Label errorSearchMessage;

    @FXML
    private TextField searchProduct;

    @FXML
    private Label errorMinQuantityMessage;

    @FXML
    private Text totalValue;

    @FXML
    private Text mostUsedProduct;

    @FXML
    private Label errorMessage;

    @FXML
    private Label saveLabel;

    @FXML
    private Label cancelLabel;
    
    @FXML
    private Label deleteLabel;
    
	@FXML private TableView<DisplayableProduct> stockTable;
	@FXML private TableColumn<DisplayableProduct, String> codeCol;
	@FXML private TableColumn<DisplayableProduct, String> descriptionCol;
	@FXML private TableColumn<DisplayableProduct, String> barcodeCol;
	@FXML private TableColumn<DisplayableProduct, Float> pricePerUnitCol;
	@FXML private TableColumn<DisplayableProduct, Integer> quantityCol;
	@FXML private TableColumn<DisplayableProduct, String> minQuantityCol;
	@FXML private TableColumn<DisplayableProduct, Boolean> deleteCol;
	
	private double[] tableWidth = new double[7];
	private static ObservableList<DisplayableProduct> stocks =  FXCollections.observableArrayList();
	private ConcreteProductManager pm = new ConcreteProductManager(Main.con);
	private ArrayList<Product> allProducts;
	
    @FXML
    private void initialize() {
 		
 		codeCol.setCellValueFactory(new PropertyValueFactory<>("productCode"));
 		tableWidth[0] = codeCol.getPrefWidth();
 		
 		descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
 		tableWidth[1] = descriptionCol.getPrefWidth();
 		
 		barcodeCol.setCellValueFactory(new PropertyValueFactory<>("barCode"));
 		barcodeCol.setCellFactory(TextFieldTableCell.forTableColumn());
 		tableWidth[2] = barcodeCol.getPrefWidth();
 		
 		pricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("pricePerUnit"));
 		tableWidth[3] = pricePerUnitCol.getPrefWidth();
 		
 		quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity")); 		
 		tableWidth[4] = quantityCol.getPrefWidth();
 		
 		minQuantityCol.setCellValueFactory(new PropertyValueFactory<>("minQuantity"));	
 		minQuantityCol.setCellFactory(TextFieldTableCell.forTableColumn());
 		tableWidth[5] = minQuantityCol.getPrefWidth();
 		
		deleteCol.setCellFactory(CheckBoxTableCell.forTableColumn(deleteCol));
 		tableWidth[6] = deleteCol.getPrefWidth();
 		
		deleteCol.setCellValueFactory(new PropertyValueFactory<>("delete"));
 		
		quantityCol.setCellFactory(new Callback<TableColumn<DisplayableProduct, Integer>, TableCell<DisplayableProduct, Integer>>() {
			@Override
			public TableCell<DisplayableProduct, Integer> call(TableColumn<DisplayableProduct, Integer> param) {
				return new TableCell<DisplayableProduct, Integer>(){
					@Override
					public void updateItem(Integer quantity, boolean empty) {
						super.updateItem(quantity, empty);
						if(this.getTableRow().getItem() != null) {
							setText(Integer.toString(quantity));
							if(quantity < Integer.parseInt(this.getTableRow().getItem().getMinQuantity())) {
								this.setStyle("-fx-font-weight: bold;" + "-fx-text-fill: FF3333;");
							}else {
								this.setStyle("-fx-background-color: null;");
							}
							
						}else {
							setText(null);
							this.setStyle("-fx-background-color: null;");
						}
					}
				};
			}
		});
		showProducts();
		showTotalValue();
		showMostUsedProduct();
		
		// auto complete text field
		allProducts = pm.getAllProductsArrayList();	
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
				errorSearchMessage.setVisible(false);
				if(key.getCode().equals(KeyCode.ENTER)) {
					String text = searchProduct.getText();
					int i = 0;
					boolean findKey = false;
					for(DisplayableProduct product : stockTable.getItems()) {
						if((product.getProductCode() + " " + product.getDescription()).equals(text)) {
							stockTable.getSelectionModel().select(i);
							findKey = true;
							return;
						}
						i++;
					}
					if(findKey == false) {
						errorSearchMessage.setVisible(true);
						if(text.length() < 36) {
							errorSearchMessage.setText("The Product '" + text + "' cannot be found.");
						} else {
							errorSearchMessage.setText("Product not found.");
						}
						
					}
				}
			}
			
		});
		
		minQuantityCol.setOnEditCommit(
	            new EventHandler<CellEditEvent<DisplayableProduct, String>>() {
	                @Override
	                public void handle(CellEditEvent<DisplayableProduct, String> t) {
	                	errorMessage.setText("");
	                	int newMinQuantity;
	            		int oldMinQuantity;
	            		DisplayableProduct productSelected = (DisplayableProduct) t.getTableView().getItems().get(t.getTablePosition().getRow());
	                	
	            		try {
	            			newMinQuantity = Integer.parseInt(t.getNewValue());
	            			oldMinQuantity = Integer.parseInt(productSelected.getMinQuantity());
	            		} catch(NumberFormatException e) {
	            			errorMinQuantityMessage.setText("Please enter numerical values for minimum quantity");
	            			stockTable.refresh();
	            			return;
	            		}
	            		
	            		if(newMinQuantity < 0) {
	            			errorMinQuantityMessage.setText("Products must have a minimum quantity of 0 or more");
	            			stockTable.refresh();
	            			return;
	            		}
	            		
	            		errorMinQuantityMessage.setText("");
	            		if(newMinQuantity == oldMinQuantity) {
	            			return;
	            		}
	            		
	            		//update the minQuantity into the database
	            		Alert confirmation = new Alert(AlertType.CONFIRMATION);
	            		confirmation.setTitle("Change the minimum quantity?");
	            		confirmation.setHeaderText(null);
	            		confirmation.setContentText("Are you sure you want to change product "+ productSelected.getDescription() +"'s minimum quanity as " + newMinQuantity + "?");
	            		// add the RJB logo to the dialog box
	            		Stage stage = (Stage) confirmation.getDialogPane().getScene().getWindow();
	            		stage.getIcons().add(Main.getImageResource(Main.LOGOPATH));
	            		Optional <ButtonType> button = confirmation.showAndWait();
	            		
	            		if(button.get() == ButtonType.OK) {
	            			// ok button was clicked, update
	            			if(!pm.updateMinQuantity(productSelected.getProductId(), newMinQuantity)){
	            				errorMinQuantityMessage.setText("Failed to update minimum quantity: error accessing database");
	            				return;
	            			};
	            			productSelected.setMinQuantity(t.getNewValue());	            			
	            		}
	            		stockTable.refresh();
	            		
	                }
	            }
	        );
		
		barcodeCol.setOnEditCommit( new EventHandler<CellEditEvent<DisplayableProduct, String>>(){
			@Override
			public void handle(CellEditEvent<DisplayableProduct, String> t) {
				errorMessage.setText("");
				errorMinQuantityMessage.setText("");
				DisplayableProduct productSelected = (DisplayableProduct) t.getTableView().getItems().get(t.getTablePosition().getRow());
				String newBarcode = t.getNewValue();
				String oldBarcode = productSelected.getBarCode();
				
				if(newBarcode.length() != 0) {
					if(!newBarcode.matches("[0-9]{1,}")) {
						errorMinQuantityMessage.setText("Please only enter digits for barcode");
	        			stockTable.refresh();
	        			return;
					}
					
					if(newBarcode.length() < 4) {
						errorMinQuantityMessage.setText("Barcode must have at least 4 digits");
	        			stockTable.refresh();
	        			return;
					}
					
					if(newBarcode.length() > 128) {
						errorMinQuantityMessage.setText("Barcode has a maximum of 128 digits");
	        			stockTable.refresh();
	        			return;
					}
				}
								
				errorMinQuantityMessage.setText(null);
				if(newBarcode.equals(oldBarcode)) {
					return;
				}
				
				
				Alert confirmation = new Alert(AlertType.CONFIRMATION);
        		confirmation.setTitle("Change the barcode?");
        		confirmation.setHeaderText(null);
        		if(newBarcode.length() == 0) {
    				newBarcode = null;
    				confirmation.setContentText("Are you sure you want to delete product "+ productSelected.getDescription() +"'s barcode?");
    			}else {
    				confirmation.setContentText("Are you sure you want to change product "+ productSelected.getDescription() +"'s barcode as " + newBarcode + "?");
    			}
        		
        		// add the RJB logo to the dialog box
        		Stage stage = (Stage) confirmation.getDialogPane().getScene().getWindow();
        		stage.getIcons().add(Main.getImageResource(Main.LOGOPATH));
        		Optional <ButtonType> button = confirmation.showAndWait();
        		
        		if(button.get() == ButtonType.OK) {
        			// ok button was clicked, update       			
        			if(!pm.updateBarcode(productSelected.getProductId(), newBarcode)){
        				errorMessage.setText("Failed to update the barcode: error accessing database");
        				return;
        			};
        			productSelected.setBarcode(newBarcode);	            			
        		}
        		stockTable.refresh();
			}			
		});
    }
    
    /**
     * Add new item into database.
     * @throws IOException
     */
    @FXML
    void addButtonClicked() throws IOException {		
		new AddProductPage(new Stage(), this);
    }

    /**
     * return to the HomePage
     */
    @FXML
    void homePageButtonClicked(MouseEvent e) throws IOException {
    	Stage theStage = (Stage) homePageButton.getScene().getWindow();
		new HomePage(theStage);
    }

    
    /**
     * Setting delete row and save button to be 
     * visible and allow user to delete column.
     */
	 @FXML
	 private void deleteButtonClicked() {
 		
 		codeCol.setPrefWidth(185);
 		
 		descriptionCol.setPrefWidth(531);
 		
 		barcodeCol.setPrefWidth(350);
 		
 		pricePerUnitCol.setPrefWidth(240);
 		
 		quantityCol.setPrefWidth(268);
 		
 		minQuantityCol.setPrefWidth(250);
 		
 		deleteCol.setPrefWidth(90);
 		
		deleteCol.setVisible(true);
		saveButton.setVisible(true);
		cancelButton.setVisible(true);
		saveLabel.setVisible(true);
		cancelLabel.setVisible(true);
		deleteButton.setVisible(false);
		deleteLabel.setVisible(false);
		
	}
	
	 /**
	  * Save user delete operation and set save button
	  * and delete column to be invisible.
	  */
	@FXML
	private void saveButtonClicked() {
		errorMessage.setText("");
		for(DisplayableProduct product : stockTable.getItems()) {
			if(product.getDelete()) {
				int id = product.getProductId();
				if(!pm.deleteProduct(id)) {
					errorMessage.setText("Failed to delete products: error accessing database");
					errorMessage.setVisible(true);
					return;
				}
			}
		}
		
 		codeCol.setPrefWidth(tableWidth[0]);

 		descriptionCol.setPrefWidth(tableWidth[1]);
 		
 		barcodeCol.setPrefWidth(tableWidth[2]);

 		pricePerUnitCol.setPrefWidth(tableWidth[3]);

 		quantityCol.setPrefWidth(tableWidth[4]);

 		minQuantityCol.setPrefWidth(tableWidth[5]);
 		
 		deleteCol.setVisible(false);
 		showTotalValue();
		showProducts();
		saveButton.setVisible(false);
		cancelButton.setVisible(false);
		saveLabel.setVisible(false);
		cancelLabel.setVisible(false);
		deleteButton.setVisible(true);
		deleteLabel.setVisible(true);
	}
	
	/**
	 * 
	 * Cancel the operation when click
	 * cancel button.
	 * 
	 */
	@FXML
	private void cancelButtonClicked() {
		codeCol.setPrefWidth(tableWidth[0]);

 		descriptionCol.setPrefWidth(tableWidth[1]);
 		
 		barcodeCol.setPrefWidth(tableWidth[2]);

 		pricePerUnitCol.setPrefWidth(tableWidth[3]);

 		quantityCol.setPrefWidth(tableWidth[4]);

 		minQuantityCol.setPrefWidth(tableWidth[5]);
 		
 		deleteCol.setVisible(false);
		showProducts();
		saveButton.setVisible(false);
		cancelButton.setVisible(false);
		saveLabel.setVisible(false);
		cancelLabel.setVisible(false);
		deleteButton.setVisible(true);
		deleteLabel.setVisible(true);
	}
	
	/**
	 * 
	 * Import CSV file from system.
	 */
	@FXML
	private void importButtonClicked() {
		errorMessage.setText("");
		int stockRemaining;
		FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a file");
        Stage selectFile = new Stage();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        File file = fileChooser.showOpenDialog(selectFile);
        if (file != null) {
            String filePath = file.getAbsolutePath();
            try {
				CsvReader csvReader = new CsvReader(filePath);
				csvReader.readHeaders();
				String code, description;
				Double salesPrice = 0.0, quantity = 0.0;
				
				while(csvReader.readRecord()) {
					code = csvReader.get(0);
					description = csvReader.get(1);
					try {
						salesPrice = Double.parseDouble(csvReader.get(3));
						quantity = Double.parseDouble(csvReader.get(4));
					} catch(NumberFormatException ex) {
						continue;
					}
					stockRemaining = pm.getStockForOne(code, description);
					pm.importNewProduct(code, description,salesPrice, quantity.intValue());
					pm.importRestock(code, description, quantity.intValue(), stockRemaining);
				}
				
            } catch (IOException e) {
				errorMessage.setText("Failed To load CSV File");
			}           
        }
        this.showProducts();
        this.showTotalValue();
	}
	
	/**
	 * Refresh tableview.
	 */
	@Override
	public void updateTableView() {
		showProducts();	
	}
	
	
	/**
	 * Refresh stockTable 
	 */
	private void showProducts() {
		errorMessage.setText("");
		stocks.removeAll(stocks);
		allProducts = pm.getAllProductsArrayList();
		if(allProducts == null) {
			errorMessage.setText("Failed to load products: error accessing database");
			errorMessage.setVisible(true);
			return;
		}
		
		for(Product product : allProducts) {
			stocks.add(new DisplayableProduct(product.getProductId(), product.getProductCode(),
					product.getDescription(), product.getBarCode(),  product.getPricePerUnit(), 
					product.getStock(),String.valueOf(product.getMinQuantity())));
		}		
		stockTable.setItems(stocks);
	}
	
	/**
	 * Change totalValue Text with most used product.
	 */
	private void showTotalValue() {
		double value = 0;
		String textInfo = "Total Value: �";
		for(DisplayableProduct product : stockTable.getItems()) {
			value += product.getPricePerUnit() * product.getQuantity();
		}
		totalValue.setText( textInfo + Double.toString(value));
	}
	
	/**
	 * Change mustUsedProduct Text with most used product.
	 */
	private void showMostUsedProduct() {
		errorMessage.setText("");
		String product = pm.getMostUsedProduct();
		String textInfo = "Most Used Product: ";
		if(product == null) {
			errorMessage.setText("Failed to load most used product: error accessing database");
			errorMessage.setVisible(true);
			return;
		}
		mostUsedProduct.setText( textInfo + product);
	}
}