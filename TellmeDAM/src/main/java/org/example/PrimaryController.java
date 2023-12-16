package org.example;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.api.APICallback;
import org.example.api.UserAPIClient;

public class PrimaryController {

    public TextField passwordField;
    public TextField emailField;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void switchToLogged() throws IOException {
        App.setRoot("logged");
        App.stage.setWidth(1065);
        App.stage.setHeight(780);
    }

    @FXML
    public void login() {
        UserAPIClient user = new UserAPIClient();
        try {
            user.login(emailField.getText(), passwordField.getText(), new APICallback() {
                @Override
                public void onSuccess(Object response) throws IOException {
                    System.out.println("Success logging.");
                    App.userLogged = (User) response;
                    switchToLogged();
                }

                @Override
                public void onError(Object error) {
                    System.out.println("Error logging");
                    showErrorAlert("Error logging");
                }
            });
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR LOGGING");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        emailField.clear();
        passwordField.clear();
    }
}
