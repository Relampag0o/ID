package org.example;

import java.io.IOException;

import javafx.fxml.FXML;
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
        App.stage.setWidth(1000);
        App.stage.setHeight(1100);



    }

    public void login() {
        UserAPIClient user = new UserAPIClient();
        try {
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
