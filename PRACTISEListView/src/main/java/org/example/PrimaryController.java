package org.example;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class PrimaryController {


    public TabPane tabPane;
    @FXML
    private ListView listView;

    Person selectedPerson;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }


    public void initialize() {
        ObservableList<Person> persons = FXCollections.observableArrayList();
        for (int i = 1; i <= 20; i++) {
            String name = "Person" + i;
            int age = i + 10;
            String address = "Address" + i;
            String personalInfo = "Info about Person" + i;

            Person person = new Person(name, age, address, personalInfo);
            persons.add(person);
        }
        System.out.println(persons.size());

        listView.setItems(persons);
        listView.setCellFactory(param -> new CustomPersonCell());

        listView.setOnMouseClicked(event -> {
            Person selectedPerson = (Person) listView.getSelectionModel().getSelectedItem();

            // Crear una nueva pestaña
            Tab tab = new Tab();

            // Crear el contenido de la pestaña
            HBox tabContent = new HBox();
            Label nameLabel = new Label(selectedPerson.getName());
            Button closeButton = new Button("X");

            // Asignar estilos o propiedades según sea necesario
            nameLabel.setStyle("-fx-padding: 0 5 0 0;");
            closeButton.setStyle("-fx-background-color: transparent;");

            // Asignar el evento para cerrar la pestaña
            closeButton.setOnAction(e -> tabPane.getTabs().remove(tab));

            // Agregar el contenido a la pestaña
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            tabContent.getChildren().addAll(nameLabel, spacer, closeButton);
            tab.setGraphic(tabContent);

            // Agregar la pestaña al TabPane
            tabPane.getTabs().add(tab);
        });
    }
}

