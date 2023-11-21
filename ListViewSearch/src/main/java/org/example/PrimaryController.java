package org.example;

import java.io.IOException;
import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

public class PrimaryController {

    public ListView<Product> listV;
    public ObservableList<Product> products;


    @FXML
    public TextField searchField;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    public void initialize() {
        this.products = FXCollections.observableArrayList();

        products.add(new Product("Laptop", 999.99, 5));
        products.add(new Product("Teléfono", 299.99, 10));
        products.add(new Product("Tablet", 199.99, 8));
        products.add(new Product("Cámara", 149.99, 15));
        products.add(new Product("Auriculares", 49.99, 20));
        products.add(new Product("Teclado", 29.99, 30));
        products.add(new Product("Mouse", 19.99, 25));
        products.add(new Product("Monitor", 199.99, 12));
        products.add(new Product("Impresora", 149.99, 8));
        products.add(new Product("Altavoces", 79.99, 18));
        products.add(new Product("Micrófono", 39.99, 15));
        products.add(new Product("Disco Duro", 79.99, 10));
        products.add(new Product("Memoria RAM", 49.99, 22));
        products.add(new Product("Router", 39.99, 17));
        products.add(new Product("Tarjeta Gráfica", 129.99, 7));
        products.add(new Product("Batería Externa", 29.99, 25));
        products.add(new Product("Mochila para Laptop", 34.99, 20));
        products.add(new Product("Funda para Teléfono", 14.99, 30));
        products.add(new Product("Cable USB", 9.99, 40));
        products.add(new Product("Adaptador de Corriente", 19.99,
                15));
        listV.setItems(products);

        listV.setCellFactory(
                new Callback<ListView<Product>, ListCell<Product>>() {
                    @Override
                    public ListCell<Product> call(ListView<Product> param) {
                        return new ListCell<Product>() {
                            @Override
                            protected void updateItem(Product p, boolean empty) {
                                super.updateItem(p, empty);
                                if (p != null) {
                                    setText(p.getName() + ", "
                                            + p.getPrice() + "€ " + " - "
                                            + p.getStock() + " units");
                                } else {
                                    setText("");
                                }
                            }
                        };
                    }
                });


    }

    public void search() {
        String pattern = this.searchField.getText();
        ObservableList<Product> filteredList = products.filtered(product -> product.find(pattern));

        if (!filteredList.isEmpty()) {
            // ask about how to sort this list withouth doing so myself.
            // it looks like it doesn't accept the sort operation.
            //filteredList.sort(Comparator.comparing(Product::getStock));
            listV.setItems(filteredList);
        }

    }


}
