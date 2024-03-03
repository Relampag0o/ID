package org.example;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import org.example.classes.Product;
import org.example.controllers.ProductCellController;

import java.util.Map;

public class CustomProductCell extends ListCell<Map.Entry<Product, Integer>> {
    @Override
    protected void updateItem(Map.Entry<Product, Integer> item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/productCell.fxml"));
                Parent root = loader.load();
                ProductCellController controller = loader.getController();
                Product product = item.getKey();
                Integer quantity = item.getValue();
                controller.setProductDetails(product.getName(), quantity.toString(), String.valueOf(product.getPrice() * quantity));
                setGraphic(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
