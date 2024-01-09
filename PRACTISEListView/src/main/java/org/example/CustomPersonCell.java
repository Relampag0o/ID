package org.example;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CustomPersonCell extends ListCell<Person> {

    public Label nameField;
    public Label addressField;
    public Label ageField;
    public TextField infoField;

    @Override
    protected void updateItem(Person person, boolean empty) {
        super.updateItem(person, empty);
        if (empty || person == null) {
            setText(null);
            setGraphic(null);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("personlistcell.fxml"));
                Parent root = loader.load();
                CustomPersonCell controller = loader.getController();
                controller.setData(person);
                setGraphic(root);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    public void setData(Person person) {
        nameField.setText(person.getName());
        addressField.setText(person.getAddress());
        ageField.setText(Integer.toString(person.getAge()));
        infoField.setText(person.getPersonalInfo());


    }

}
