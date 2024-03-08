package org.example.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.github.palexdev.mfxcore.controls.Label;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.example.App;
import org.example.CustomProductCell;
import org.example.classes.Product;
import org.example.classes.Table;
import org.example.database.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrimaryController {

    // PRODUCTS:

    @FXML
    public ImageView sanMiguel1;
    @FXML
    public ImageView alhambra1;
    public Button clearButton;
    public Label number1;
    public Label number2;
    public ImageView table3;
    public Label number3;
    public ImageView table4;
    public Label number4;
    public ImageView table5;
    public Label number5;
    public ImageView table6;
    public Label number6;
    public ImageView table7;
    public Label number7;
    public ImageView table8;
    public Label number8;
    public ImageView table9;
    public Label number9;
    public ImageView table10;
    public Label number10;
    public ImageView table11;
    public Label number11;
    public ImageView table12;
    public Label number12;
    public ImageView paella1;
    public ImageView fabada1;
    public ImageView bacalao1;
    public ImageView tortilla1;
    public ImageView salmorejo1;
    public ImageView almejas1;
    public ImageView pulpo1;
    public ImageView albondigas1;
    public ImageView cheristoff1;


    // TABLES

    private ImageView previouslySelectedTable;

    @FXML
    public ImageView table1;

    @FXML
    public ImageView table2;


    // LABELS:
    @FXML
    public Label selectTableMsg;

    @FXML
    public Label totalLabel;

    public ListView productsListView;


    private List<Table> tables;

    public Table selectedTablee;
    private Product selectedProduct;
    private Connector connector;

    public void initialize() {
        this.tables = new ArrayList<Table>();
        this.connector = new Connector();
        productsListView.setCellFactory(param -> new CustomProductCell(selectedTablee));
        clearButton.setVisible(false);


    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }


    @FXML
    public void selectProduct(MouseEvent event) {
        ImageView clickedImageView = (ImageView) event.getSource();
        String id = clickedImageView.getId();
        Product product = connector.findProduct(id);
        if (product != null) {
            System.out.println("Product selected: " + product.getName() + " - " + product.getPrice());
            selectedProduct = product;
            addProductToTable();
            updateProductList();
            updateTotalLabel();
        } else {
            System.out.println("No product found with id: " + id);
        }
    }

    @FXML
    public void selectTable(MouseEvent event) {
        ImageView clickedImageView = (ImageView) event.getSource();
        String id = clickedImageView.getId();
        Table table = connector.findTable(id);
        if (table != null) {
            System.out.println("Table selected: " + table.getName());
            selectedTablee = table;
            selectTableMsg.setText("Table selected: " + table.getName());
            selectTableMsg.setStyle("-fx-background-color: #F08080; -fx-border-color: #000000;");
            clearButton.setVisible(true);
            List<Product> products = connector.getProducts(selectedTablee.getId());
            productsListView.setCellFactory(param -> new CustomProductCell(selectedTablee));
            productsListView.setItems(FXCollections.observableArrayList(products));

            if (previouslySelectedTable != null) {
                previouslySelectedTable.setEffect(null);
            }

            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(0.5);
            clickedImageView.setEffect(colorAdjust);

            previouslySelectedTable = clickedImageView;
            updateTotalLabel();

        } else {
            System.out.println("No table found with id: " + id);
            selectTableMsg.setText("No table found with id: " + id);
            selectTableMsg.setStyle("-fx-background-color: #FFFFE0; -fx-border-color: #000000;");
        }
    }

    public void addProductToTable() {
        if (selectedTablee != null && selectedProduct != null) {
            selectedTablee.addProduct(selectedProduct);
            selectedProduct.setQuantity(selectedProduct.getQuantity() + 1);
            System.out.println("Product added to table: " + selectedTablee.getName());
        } else {
            System.out.println("No table or product selected");
            updateTotalLabel();
        }
    }

    public void updateProductList() {
        if (selectedTablee != null) {
            List<Product> products = connector.getProducts(selectedTablee.getId());
            productsListView.setItems(FXCollections.observableArrayList(products));
        }
    }

    public void updateTotalLabel() {
        if (selectedTablee != null) {
            double total = selectedTablee.getTotal();
            totalLabel.setText(total + "€");
        } else {
            totalLabel.setText("No table selected");
        }
    }

    public void generateReport() {
        if (selectedTablee != null) {
            connector.generateReport(selectedTablee);
        } else {
            System.out.println("No table selected");
        }
    }

    @FXML
    public void clearTable() {
        if (selectedTablee != null) {
            selectedTablee.clearTable();
            updateProductList();
            updateTotalLabel();
        }
    }



}
