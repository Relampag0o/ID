package org.example;

import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CustomFilmCell extends ListCell<Film> {

    protected void updateItem(Film item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null && !empty) {
            ImageView imageView = new ImageView(new Image(item.getUrl()));
            imageView.setFitWidth(100);
            imageView.setPreserveRatio(true);

            setText(item.getTitle() + " - " + item.getDirector());
            setGraphic(imageView);
        } else {
            setText(null);
            setGraphic(null);
        }
    }
}

