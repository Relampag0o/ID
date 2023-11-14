package org.example;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import org.example.apimodels.Datum;

public class CustomCell extends ListCell<Datum> {
    protected void updateItem(Datum d, boolean empty) {
        super.updateItem(d, empty);

        if (empty || d == null) {
            setGraphic(null);
        } else
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("customxml.fxml"));
                Parent root = loader.load();
                CustomCellController controller = loader.getController();
                controller.setData(d);
                setGraphic(root);
            } catch (Exception e) {
                e.printStackTrace();
            }

    }


}
