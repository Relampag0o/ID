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

        films.add(new Film("Forrest Gump", "Robert Zemeckis", "1994", "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/i/fca87258-3ee2-47f6-b0ed-41b39ce1b1c2/ddz41f7-d60bd57e-ac4c-4909-a33f-3a216d0a2074.png"));
        films.add(new Film("Pulp Fiction", "Quentin Tarantino", "1994", "https://www.ambiance-sticker.com/images/Image/sticker-personnages-pulp-fiction-ambiance-sticker-KC3621.png"));
        films.add(new Film("El Señor de los Anillos: La Comunidad del Anillo", "Peter Jackson", "2001", "https://static.wikia.nocookie.net/doblaje/images/a/a5/SDACACartel.png/revision/latest?cb=20161004062709&path-prefix=es"));
        films.add(new Film("Matrix", "Lana Wachowski, Lilly Wachowski", "1999", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9b/The.Matrix.glmatrix.2.png/857px-The.Matrix.glmatrix.2.png"));
        films.add(new Film("Star Wars: Episodio IV - Una Nueva Esperanza", "George Lucas", "1977", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6c/Star_Wars_Logo.svg/1280px-Star_Wars_Logo.svg.png"));
        films.add(new Film("Jurassic Park", "Steven Spielberg", "1993", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6c/Star_Wars_Logo.svg/1280px-Star_Wars_Logo.svg.png"));
        films.add(new Film("Gladiador", "Ridley Scott", "2000", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6c/Star_Wars_Logo.svg/1280px-Star_Wars_Logo.svg.png"));

        listV.setItems(this.films);

        listV.setCellFactory(new Callback<ListView<Film>, ListCell<Film>>() {
            @Override
            public CustomFilmCell call(ListView<Film> param) {
                return new CustomFilmCell();
            }
        });
    }
}
