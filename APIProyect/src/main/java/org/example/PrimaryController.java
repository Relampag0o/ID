package org.example;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.example.apimodels.AnimeData;
import org.example.apimodels.Datum;

import java.io.IOException;

public class PrimaryController {

    public ListView<Datum> listView;

    public void initialize() {

        KitsuAPIClient client = new KitsuAPIClient();
        try {
            AnimeData animes = client.getAnimes(10, 10);
            listView.setItems(FXCollections.observableList(animes.data));

            listView.setCellFactory(new Callback<>() {
                @Override
                public ListCell<Datum> call(ListView<Datum> datumListView) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(Datum datum, boolean empty) {
                            super.updateItem(datum, empty);
                            if (datum == null || empty) {
                                setText(null);
                                setGraphic(null);
                            }

                        }
                    };
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        listView.setCellFactory(new Callback<ListView<Datum>, ListCell<Datum>>() {
            @Override
            public CustomCell call(ListView<Datum> param) {
                return new CustomCell();
            }
        });

    }

}
