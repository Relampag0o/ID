package org.example;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import org.example.classes.Product;
import org.example.classes.Table;
import org.example.controllers.ProductCellController;

import java.util.Map;

public class CustomProductCell extends ListCell<Product> {

    private final Table table;

    public CustomProductCell(Table table) {
        this.table = table;
    }
    @Override
    protected void updateItem(Product product, boolean empty) {
        super.updateItem(product, empty);
        if (empty || product == null) {
            setText(null);
            setGraphic(null);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/productCell.fxml"));
                Parent root = loader.load();
                ProductCellController controller = loader.getController();


                int quantity = product.getQuantity();

                controller.setProductDetails(product.getName(), String.valueOf(quantity), String.valueOf(product.getPrice() * quantity));
                setGraphic(root);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
