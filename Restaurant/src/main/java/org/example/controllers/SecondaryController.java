package org.example.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import org.example.App;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}