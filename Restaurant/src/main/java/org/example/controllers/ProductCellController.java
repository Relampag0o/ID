package org.example.controllers;

import io.github.palexdev.mfxcore.controls.Label;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class ProductCellController {
    public Label nameLabel;
    public Label quantityLabel;
    public Label totalLabel;
    public ImageView addButton;
    public ImageView removeButton;


    public void setProductDetails(String name, String quantity, String total) {
        nameLabel.setText(name);
        quantityLabel.setText(quantity);
        totalLabel.setText(total);
    }
}
