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

    private PrimaryController primaryController;


    public ProductCellController(){

    }

    @FXML
    public void initialize() {
        addButton.setOnMouseClicked(event -> addProduct());
        removeButton.setOnMouseClicked(event -> removeProduct());
    }

    public ProductCellController(Connector connector, Table table, Product product, PrimaryController primaryController) {
        this.connector = connector;
        this.table = table;
        this.product = product;
        this.primaryController = primaryController;
    }

    public void setProductDetails(String name, String quantity, String total) {
        nameLabel.setText(name);
        quantityLabel.setText(quantity);
        totalLabel.setText(total);
    }

    @FXML
    public void addProduct() {
        System.out.println("Adding product" + product.getName() + " to table " + table.getName());
        table.addProduct(product);
        product.setQuantity(product.getQuantity() + 1); // Update the quantity of the product
        connector.updateTableTotal(table);
        setProductDetails(product.getName(), String.valueOf(product.getQuantity()), String.valueOf(product.getPrice() * product.getQuantity()));
        primaryController.updateTotalLabel();
        primaryController.updateTableNumbersColor();
    }


    @FXML
    public void removeProduct() {
        System.out.println("Removing product" + product.getName() + " from table " + table.getName());
        if (product.getQuantity() > 1) {
            product.setQuantity(product.getQuantity() - 1);
            table.removeProduct(product);
            setProductDetails(product.getName(), String.valueOf(product.getQuantity()), String.valueOf(product.getPrice() * product.getQuantity()));
            primaryController.updateTotalLabel();
            primaryController.updateTableNumbersColor();
        } else if (product.getQuantity() == 1) {
            table.removeProduct(product);
            connector.removeTableProduct(table, product); // Remove the product from the database
            primaryController.updateProductList();
            primaryController.updateTotalLabel();
            primaryController.updateTableNumbersColor();
        } else {
            System.out.println("No more units of product " + product.getName() + " to remove from table " + table.getName());
        }
    }
}
