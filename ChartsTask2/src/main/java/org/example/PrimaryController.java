package org.example;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

public class PrimaryController {

    public ComboBox comboBox;
    public AnchorPane anchorPane;

    public LineChart<String, Number> lineChart;
    public AreaChart areaChart;
    public ScatterChart scatterCharts;

    public List<WeatherData> weatherData;
    public BarChart barChart;


    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    public void initialize() {
        weatherData = WeatherData.getData();
        fillLineChart();
        fillBarChart();
        fillAreaChart();
        fillScatterChart();
        setGraphic();

    }

    public void fillLineChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Temperature Over Time");
        for (WeatherData data : weatherData) {
            series.getData().add(new XYChart.Data<>(data.getDate().toString(), data.getMaxDegrees()));
        }
        lineChart.getData().add(series);
        lineChart.setTitle("Temperature Over Time");

    }

    public void fillBarChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Precipitation by month");
        for (WeatherData data : weatherData) {
            series.getData().add(new XYChart.Data<>(data.getDate().toString(), data.getPrecipitation()));
        }
        barChart.getData().add(series);
        barChart.setTitle("Precipitation");


    }

    public void fillAreaChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Humidity over time");
        for (WeatherData data : weatherData) {
            series.getData().add(new XYChart.Data<>(data.getDate().toString(), data.getHumidityPercentage()));
        }
        areaChart.getData().add(series);
        areaChart.setTitle("Humidity");
    }

    public void fillScatterChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Wind speed Over time");

        for (WeatherData data : weatherData) {
            series.getData().add(new XYChart.Data<>(data.getDate().toString(), data.getWindSpeed()));
        }
        scatterCharts.getData().add(series);
        scatterCharts.setTitle("Wind Speed");

    }

    public void setGraphic() {

        comboBox.getItems().addAll("Line Chart", "Bar Chart", "Area Chart", "Scatter Chart");
        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue.toString()) {
                case "Line Chart":
                    hideItems();
                    lineChart.setVisible(true);
                    break;
                case "Bar Chart":
                    hideItems();
                    barChart.setVisible(true);
                    break;
                case "Area Chart":
                    hideItems();
                    areaChart.setVisible(true);
                    break;
                case "Scatter Chart":
                    hideItems();
                    scatterCharts.setVisible(true);
                        break;
            }
        });


    }

    public void hideItems() {
        lineChart.setVisible(false);
        barChart.setVisible(false);
        areaChart.setVisible(false);
        scatterCharts.setVisible(false);





    }
}
