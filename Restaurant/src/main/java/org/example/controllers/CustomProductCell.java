package org.example.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import org.example.classes.Product;
import org.example.classes.Table;
import org.example.controllers.PrimaryController;
import org.example.controllers.ProductCellController;
import org.example.database.Connector;

import java.util.Map;
// THE CLASS CUSTOMPRODUCTCELL IS USED TO CREATE CUSTOM CELLS FOR THE PRODUCT LIST

public class CustomProductCell extends ListCell<Product> {

    // The table object is used to store the table that the product is in
    private Table table;
    // The primaryController object is used to store the primary controller
    private PrimaryController primaryController;


    // THE CONSTRUCTOR IS USED TO CREATE A CUSTOMPRODUCTCELL OBJECT
    public CustomProductCell() {

    }

    public CustomProductCell(Table table, PrimaryController primaryController) {
        this.table = table;
        this.primaryController = primaryController;
    }

    // THE METHOD UPDATEITEM IS USED TO UPDATE THE ITEM IN THE CELL
    @Override
    protected void updateItem(Product product, boolean empty) {
        super.updateItem(product, empty);
        if (empty || product == null) {
            setText(null);
            setGraphic(null);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/productCell.fxml"));
                ProductCellController controller = new ProductCellController(new Connector(), table, product, primaryController);
                loader.setController(controller);
                Parent root = loader.load();
                controller.setProductDetails(product.getName(), String.valueOf(product.getQuantity()), String.valueOf(product.getPrice() * product.getQuantity()));
                setGraphic(root);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
