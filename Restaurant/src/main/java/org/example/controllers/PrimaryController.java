package org.example.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.github.palexdev.mfxcore.controls.Label;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.example.App;
import org.example.CustomProductCell;
import org.example.classes.Product;
import org.example.classes.Table;
import org.example.database.Connector;

public class PrimaryController {

    // PRODUCTS:

    @FXML
    public ImageView sanMiguel1;


    // TABLES

    @FXML
    public ImageView table1;
    public Label selectTableMsg;
    public ListView productsListView;


    private List<Table> tables;

    public Table selectedTablee;
    private Product selectedProduct;
    private Connector connector;

    public void initialize() {
        this.tables = new ArrayList<Table>();
        this.connector = new Connector();
        productsListView.setCellFactory(param -> new CustomProductCell(selectedTablee));

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
            selectTableMsg.setStyle("-fx-background-color: #00000;");
            List<Product> products = connector.getProducts(selectedTablee.getId());
            productsListView.setCellFactory(param -> new CustomProductCell(selectedTablee));
            productsListView.setItems(FXCollections.observableArrayList(products));

        } else {
            System.out.println("No table found with id: " + id);
            selectTableMsg.setText("No table found with id: " + id);
            selectTableMsg.setStyle("-fx-background-color: #00000;");
        }
    }

    public void addProductToTable() {
        if (selectedTablee != null && selectedProduct != null) {
            selectedTablee.addProduct(selectedProduct);
            System.out.println("Product added to table: " + selectedTablee.getName());
        } else {
            System.out.println("No table or product selected");
        }
    }


}
