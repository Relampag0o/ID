package org.example;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;

public class CellController extends ListCell<Digimon> {

    public Label nameField;
    public Label idField;

    @Override
    protected void updateItem(Digimon digimon, boolean empty) {
        super.updateItem(digimon, empty);
        if (empty || digimon == null) {
            setText(null);
            setGraphic(null);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("digimonlistcell.fxml"));
                Parent root = loader.load();
                CellController controller = loader.getController();
                controller.setData(digimon);
                setGraphic(root);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    public void setData(Digimon digimon) {

        idField.setText("Id:" + digimon.getId());
        nameField.setText("Name: " + digimon.getName());


    }

}
