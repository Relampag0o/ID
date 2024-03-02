package org.example.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.example.App;
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


    private List<Table> tables;

    private Table selectedTablee;
    private Product selectedProduct;
    private Connector connector;

    public void initialize() {
        this.tables = new ArrayList<Table>();
        this.connector = new Connector();
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
        System.out.println("selectTable event");
        ImageView clickedImageView = (ImageView) event.getSource();
        String id = clickedImageView.getId();
        Table table = connector.findTable(id);
        if (table != null) {
            System.out.println("Table selected: " + table.getName());
            selectedTablee = table;
        } else {
            System.out.println("No table found with id: " + id);
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
