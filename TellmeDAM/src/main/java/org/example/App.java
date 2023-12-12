package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.api.APICallback;
import org.example.api.UserAPIClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * JavaFX App
 */
public class App extends Application {


    public static User userLogged;
    public static List<User> allUsers = new ArrayList<>();


    private static Scene scene;
    static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        loadUsers();
        App.stage = stage;
        scene = new Scene(loadFXML("primary"), 600, 460);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));

    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public void loadUsers() {
        UserAPIClient userAPIClient = new UserAPIClient();

        try {
            userAPIClient.getAllUsers(new APICallback() {
                @Override
                public void onSuccess(Object response) throws IOException {
                    allUsers.addAll((List<User>) response);
                }

                @Override
                public void onError(Object error) {
                    System.out.println("No users found");
                }
            });
        } catch (IOException | InterruptedException  e) {
            throw new RuntimeException(e);
        }

    }
}
