package org.example.listview;

import javafx.scene.control.ListView;
import org.example.classes.Product;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class ProductListView extends ListView<Product> {

    public ProductListView() {
        setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Product item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Label nameLabel = new Label(item.getName());
                    Label quantityLabel = new Label(String.valueOf(item.getPrice()));
                    Label priceLabel = new Label(String.valueOf(item.getPrice()));

                    Button addButton = new Button("Add");
                    addButton.setOnAction(event -> {
                        // Add button action
                    });

                    Button deleteButton = new Button("Delete");
                    deleteButton.setOnAction(event -> {
                        // Delete button action
                    });

                    HBox hBox = new HBox(nameLabel, quantityLabel, priceLabel, addButton, deleteButton);
                    HBox.setHgrow(new Pane(), Priority.ALWAYS); // This will make the buttons stay on the right

                    setGraphic(hBox);
                }
            }
        });
    }
}
