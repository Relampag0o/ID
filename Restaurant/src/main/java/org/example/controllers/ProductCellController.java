package org.example.controllers;

import io.github.palexdev.mfxcore.controls.Label;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import org.example.classes.Product;
import org.example.classes.Table;
import org.example.database.Connector;

// THE CLASS PRODUCTCELLCONTROLLER IS USED TO CREATE THE PRODUCT CELL CONTROLLER
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

    // THE CONSTRUCTOR IS USED TO CREATE A PRODUCTCELLCONTROLLER OBJECT

    public ProductCellController(Connector connector, Table table, Product product, PrimaryController primaryController) {
        this.connector = connector;
        this.table = table;
        this.product = product;
        this.primaryController = primaryController;
    }

    // THE METHOD SETPRODUCTDETAILS IS USED TO SET THE DETAILS OF THE PRODUCT

    public void setProductDetails(String name, String quantity, String total) {
        nameLabel.setText(name);
        quantityLabel.setText(quantity);
        totalLabel.setText(total);
    }

    //  THE METHOD ADDPRODUCT IS USED TO ADD A PRODUCT TO THE TABLE

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



    // THE METHOD REMOVEPRODUCT IS USED TO REMOVE A PRODUCT FROM THE TABLE
    @FXML
    public void removeProduct() {
        System.out.println("Removing product" + product.getName() + " from table " + table.getName());
        if (product.getQuantity() > 1) {
            product.setQuantity(product.getQuantity() - 1);
            table.removeProduct(product);
            connector.updateTableProduct(table, product);
            setProductDetails(product.getName(), String.valueOf(product.getQuantity()), String.valueOf(product.getPrice() * product.getQuantity()));
            primaryController.updateTotalLabel();
            primaryController.updateTableNumbersColor();
        } else if (product.getQuantity() == 1) {
            table.removeProduct(product);
            connector.removeTableProduct(table, product);
            primaryController.updateProductList();
            primaryController.updateTotalLabel();
            primaryController.updateTableNumbersColor();
        } else {
            System.out.println("No more units of product " + product.getName() + " to remove from table " + table.getName());
        }
    }
}
