package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CustomCellController {

    @FXML
    public ImageView imgV;
    @FXML
    public Label movieName;
    @FXML
    public Label year;
    @FXML
    public Label name;
    @FXML
    public Label author;


    public void setData(Film film) {
        imgV.setImage(new Image(film.getUrl()));
        //imgV.setFitWidth(200);
        //imgV.setPreserveRatio(true);
        movieName.setText(film.getTitle());
        year.setText("(" + film.getYear() + ")" + "- ");
        author.setText("directed by " + film.getDirector());
    }
}
