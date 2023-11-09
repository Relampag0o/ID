package org.example;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

public class PrimaryController {
    public ListView<Film> listV;
    public ObservableList<Film> films;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");

    }


    public void initialize() {
        this.films = FXCollections.observableArrayList();

        films.add(new Film("Forrest Gump", "Robert Zemeckis", "1994", "https://s2.abcstatics.com/media/play/2020/05/29/comunidad-k3z--620x349@abc.jpg"));
//        films.add(new Film("Pulp Fiction", "Quentin Tarantino", "1994", ""));
//        films.add(new Film("El Señor de los Anillos: La Comunidad del Anillo", "Peter Jackson", "2001", "https://play-lh.googleusercontent.com/imeAs3_Nb9fyoj56LgLzSRBs3UXTZTH_TLg2xMkg6J90ZPzxscAXPvtsR9Q9azxe-WCy5A"));
//        films.add(new Film("Matrix", "Lana Wachowski, Lilly Wachowski", "1999", ""));
//        films.add(new Film("Star Wars: Episodio IV - Una Nueva Esperanza", "George Lucas", "1977", "https://play-lh.googleusercontent.com/imeAs3_Nb9fyoj56LgLzSRBs3UXTZTH_TLg2xMkg6J90ZPzxscAXPvtsR9Q9azxe-WCy5A"));
//        films.add(new Film("Jurassic Park", "Steven Spielberg", "1993", ""));
//        films.add(new Film("Gladiador", "Ridley Scott", "2000", "https://play-lh.googleusercontent.com/imeAs3_Nb9fyoj56LgLzSRBs3UXTZTH_TLg2xMkg6J90ZPzxscAXPvtsR9Q9azxe-WCy5A"));

        listV.setItems(this.films);

        listV.setCellFactory(new Callback<ListView<Film>, ListCell<Film>>() {
            @Override
            public CustomFilmCell call(ListView<Film> param) {
                return new CustomFilmCell();
            }
        });
    }
}
