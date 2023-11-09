package org.example;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class PrimaryController {

    public ListView<New> listV;
    public ObservableList<New> news;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    public void initialize() throws ParseException {
        this.news = FXCollections.observableArrayList();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        news.add(new New("Lanzamiento de Nuevo Producto",
                "La empresa XYZ ha lanzado su producto estrella esta semana, que promete revolucionar el mercado.",
                dateFormat.parse("01/10/2023")));
        news.add(new New("Deportes: Victoria del Equipo en el Campeonato",
                "Nuestro equipo local ganó la final del campeonato en una emocionante competencia el pasado fin de semana.",
                dateFormat.parse("15/09/2023")));
        news.add(new New("Descubrimiento Científico en el Espacio",
                "Los científicos han anunciado un importante descubrimiento en el espacio que podría cambiar nuestra comprensión del universo.",
                dateFormat.parse("05/08/2023")));
        news.add(new New("Economía: Crecimiento Sostenido del Mercado",
                "Los indicadores económicos muestran un crecimiento sostenido del mercado durante el último trimestre.",
                dateFormat.parse("20/07/2023")));
        news.add(new New("Entrevista con Autor Destacado",
                "Hemos tenido la oportunidad de entrevistar al autor más vendido del año, quien comparte sus ideas y experiencias en su último libro.",
                dateFormat.parse("10/06/2023")));
        news.add(new New("Política: Acuerdo Histórico en el Congreso",
                "El Congreso aprobó un acuerdo histórico que podría tener un impacto significativo en el país en los próximos años.",
                dateFormat.parse("25/05/2023")));
        news.add(new New("Tecnología: Lanzamiento de Nuevo Teléfono Inteligente",
                "La compañía ABC ha anunciado el lanzamiento de su último teléfono inteligente con características innovadoras.",
                dateFormat.parse("07/04/2023")));
        news.add(new New("Arte y Cultura: Exposición de Arte Contemporáneo",
                "Una nueva exposición de arte contemporáneo ha abierto sus puertas en el museo local, presentando obras de artistas reconocidos internacionalmente.",
                dateFormat.parse("15/03/2023")));
        news.add(new New("Medio Ambiente: Iniciativa para la Conservación de la Fauna",
                "Se ha lanzado una iniciativa para la conservación de la fauna que busca proteger especies en peligro de extinción.",
                dateFormat.parse("01/02/2023")));
        news.add(new New("Salud: Descubrimiento en la Investigación Médica",
                "Los investigadores médicos han anunciado un importante avance en la investigación de una enfermedad crónica común.",
                dateFormat.parse("10/01/2023")));

        listV.setItems(this.news);
        listV.setCellFactory(
                new Callback<ListView<New>, ListCell<New>>() {
                    @Override
                    public ListCell<New> call(ListView<New> param) {
                        return new ListCell<New>() {
                            @Override
                            protected void updateItem(New item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item != null) {
                                    setText(item.getTitle() +" - " + item.getBody().length());
                                } else {
                                    setText("");
                                }
                            }
                        };
                    }
                });

    }


}
