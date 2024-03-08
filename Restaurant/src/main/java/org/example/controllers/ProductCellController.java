package org.example.controllers;

import io.github.palexdev.mfxcore.controls.Label;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import org.example.classes.Product;
import org.example.classes.Table;
import org.example.database.Connector;

public class ProductCellController {
    public Label nameLabel;
    public Label quantityLabel;
    public Label totalLabel;
    public ImageView addButton;
    public ImageView removeButton;

    private Connector connector;
    private Table table;
    private Product product;

    public ProductCellController(){

    }

    @FXML
    public void initialize() {
        addButton.setOnMouseClicked(event -> addProduct());
        removeButton.setOnMouseClicked(event -> removeProduct());
    }

    public ProductCellController(Connector connector, Table table, Product product) {
        this.connector = connector;
        this.table = table;
        this.product = product;
    }

    public void setProductDetails(String name, String quantity, String total) {
        nameLabel.setText(name);
        quantityLabel.setText(quantity);
        totalLabel.setText(total);
    }

    @FXML
    public void addProduct() {
        System.out.println("Adding product" + product.getName() + " to table " + table.getName());
        product.setQuantity(product.getQuantity() + 1); // Update the quantity of the product
        table.addProduct(product);
        connector.updateTableTotal(table);
        //connector.insertTableProduct(table, product, product.getQuantity());
        setProductDetails(product.getName(), String.valueOf(product.getQuantity()), String.valueOf(product.getPrice() * product.getQuantity()));
    }

    @FXML
    public void removeProduct() {
        System.out.println("Removing product" + product.getName() + " from table " + table.getName());
        if (product.getQuantity() > 0) {
            product.setQuantity(product.getQuantity() - 1); // Decrement the quantity of the product
            table.removeProduct(product);
            setProductDetails(product.getName(), String.valueOf(product.getQuantity()), String.valueOf(product.getPrice() * product.getQuantity()));
        } else {
            System.out.println("No more units of product " + product.getName() + " to remove from table " + table.getName());
        }
    }
}
