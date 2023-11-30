package org.example;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.api.APICallback;
import org.example.api.RootAPIClient;
import org.example.api.UserAPIClient;

public class SecondaryController {

    public TextField emailField;
    public TextField passwordField;
    public TextField nameField;
    public TextField addressField;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    public void registerUser() {
        UserAPIClient user = new UserAPIClient();
        try {
            try {
                user.register(emailField.getText(), nameField.getText(), passwordField.getText(), new APICallback() {
                    @Override
                    public void onSuccess(Object response) throws IOException {
                        System.out.println("Success creating the user.");
                    }

                    @Override
                    public void onError(Object error) {
                        System.out.println("Error creating the user.");
                    }
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);

        }

    }


}