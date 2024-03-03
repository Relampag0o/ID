package org.example.controllers;

import io.github.palexdev.mfxcore.controls.Label;
import javafx.scene.control.Button;

public class ProductCellController {
    public Label nameLabel;
    public Label quantityLabel;
    public Label totalLabel;
    public Button addButton;
    public Button removeButton;


    public void setProductDetails(String name, String quantity, String total) {
        nameLabel.setText(name);
        quantityLabel.setText(quantity);
        totalLabel.setText(total);
    }
}
