package org.example;

import java.io.IOException;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PrimaryController {

    public ImageView imageView;
    public Label idField;
    public Label nameField;
    public MFXTextField searchField;
    @FXML
    private ListView listView;

    Digimon selectedDigimon;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }


    public void initialize() {
        ObservableList<Digimon> digimons = FXCollections.observableArrayList();

        digimons.add(new Digimon(1, "Agumon", "https://digimon.shadowsmith.com/img/agumon.jpg"));
        digimons.add(new Digimon(2, "Gabumon", "https://digimon.shadowsmith.com/img/gabumon.jpg"));
        digimons.add(new Digimon(3, "Patamon", "https://digimon.shadowsmith.com/img/patamon.jpg"));
        digimons.add(new Digimon(4, "Togemon", "https://digimon.shadowsmith.com/img/togemon.jpg"));
        digimons.add(new Digimon(5, "Kabuterimon", "https://digimon.shadowsmith.com/img/kabuterimon.jpg"));
        digimons.add(new Digimon(6, "Devimon", "https://digimon.shadowsmith.com/img/devimon.jpg"));
        digimons.add(new Digimon(7, "Greymon", "https://digimon.shadowsmith.com/img/greymon.jpg"));
        digimons.add(new Digimon(8, "Ogremon", "https://digimon.shadowsmith.com/img/ogremon.jpg"));
        digimons.add(new Digimon(9, "Seadramon", "https://digimon.shadowsmith.com/img/seadramon.jpg"));
        digimons.add(new Digimon(10, "WarGreymon", "https://digimon.shadowsmith.com/img/wargreymon.jpg"));
        System.out.println(digimons.size());

        listView.setItems(digimons);
        listView.setCellFactory(param -> new CellController());

        listView.setOnMouseClicked(event -> {
                    selectedDigimon = (Digimon) listView.getSelectionModel().getSelectedItem();
                    if (selectedDigimon.getUrl() != "") {
                        Image img = new Image(selectedDigimon.getUrl());
                        imageView.setImage(img);

                    } else {
                        imageView.setImage(null);
                    }
                    idField.setText("Id: " + selectedDigimon.getId());
                    nameField.setText("Name: " + selectedDigimon.getName());

                }

        );
    }

}
