package org.example;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import org.example.classes.Product;
import org.example.classes.Table;
import org.example.controllers.ProductCellController;
import org.example.database.Connector;

import java.util.Map;

public class CustomProductCell extends ListCell<Product> {

    private Table table;

    public CustomProductCell() {

    }

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
                ProductCellController controller = new ProductCellController(new Connector(), table, product);
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
