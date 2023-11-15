package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.apimodels.Datum;

public class CustomCellController {

    @FXML
    public Label name;

    @FXML
        public Label description;

    @FXML
    public Label details;
    @FXML
    public ImageView imgV;

    public void setData(Datum d) {
        this.name.setText(d.attributes.canonicalTitle);
        this.description.setText(d.attributes.description);
        this.details.setText(d.attributes.startDate + d.attributes.ageRating + d.attributes.episodeCount + d.attributes.showType);

        Image img = new Image(d.attributes.posterImage.medium);
        if (img.isError()) {
            System.out.println("ERROR ...");
            System.out.println("Error Message: " + img.getException().getMessage());

        }
        this.imgV.setImage(new Image(d.attributes.posterImage.original));
        this.imgV.setFitWidth(200);
        this.imgV.setPreserveRatio(true);

    }


}
