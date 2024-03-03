package org.example;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import org.example.classes.Product;
import org.example.controllers.ProductCellController;

import java.util.Map;

public class CustomProductCell extends ListCell<Product> {
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
                controller.setProductDetails(product.getName(), "1", String.valueOf(product.getPrice()));
                setGraphic(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
