package org.example;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
            Tab tab = new Tab(selectedPerson.getName() + (tabPane.getTabs().size() + 1));
            Button closeButton = new Button();
            closeButton.setText("X");
            tab.setClosable(true);
            closeButton.setOnAction(event1 -> tabPane.getTabs().remove(tab));
            tab.setGraphic(closeButton);
            tabPane.getTabs().add(tab);
            tab.setContent(new TextArea(selectedPerson.getPersonalInfo()));

        });
    }
}
