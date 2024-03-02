package org.example.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import org.example.App;
import org.example.classes.Product;
import org.example.classes.Table;

public class PrimaryController {

    private Table selectedTable;
    private Product selectedProduct;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }


}
