package org.example;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CustomFilmCell extends ListCell<Film> {

    protected void updateItem(Film f, boolean empty) {
        super.updateItem(f, empty);

        if (empty || f == null) {
            setGraphic(null);
        } else
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("customxml.fxml"));
                Parent root = loader.load();
                CustomCellController controller = loader.getController();
                controller.setData(f);
                setGraphic(root);
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

}

