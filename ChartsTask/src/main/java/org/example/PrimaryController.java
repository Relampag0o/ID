package org.example;

import java.io.IOException;
import java.util.LinkedList;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class PrimaryController {
    public BarChart barChart;
    private LinkedList<Book> books = new LinkedList<>();

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    public void initialize() {
        books.add(new Book("Book 1", 10));
        books.add(new Book("Book 2", 20));
        books.add(new Book("Book 3", 30));
        books.add(new Book("Book 4", 40));
        books.add(new Book("Book 5", 50));
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        for (Book book : books) {
            dataSeries.getData().add(new XYChart.Data<>(book.getTitle(), book.getSales()));
        }
        barChart.getData().add(dataSeries);
        barChart.setTitle("Book Sales");

    }
}
