package org.example;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WeatherData {
    private Date date;
    private int minDegrees;
    private int maxDegrees;
    private int humidityPercentage;
    private int windSpeed;
    private int precipitation;

    public WeatherData(Date date, int minDegrees, int maxDegrees, int humidityPercentage, int windSpeed, int precipitation) {
        this.date = date;
        this.minDegrees = minDegrees;
        this.maxDegrees = maxDegrees;
        this.humidityPercentage = humidityPercentage;
        this.windSpeed = windSpeed;
        this.precipitation = precipitation;
    }

    public static List<WeatherData> getData() {
        List<WeatherData> data = new ArrayList<>();
        //simulates data for every month of the year
        Calendar calendar = Calendar.getInstance();

        // Simular datos para cada mes del año
        for (int month = Calendar.JANUARY; month <= Calendar.DECEMBER; month++) {
            calendar.set(Calendar.MONTH, month);

            // Se asume el mismo día para todos los meses (puedes ajustar esto según tus necesidades)
            Date date = calendar.getTime();

            // Valores ficticios para la temperatura, humedad, velocidad del viento y precipitación
            int minDegrees = 20 + (int) (Math.random() * 15); // Ejemplo: entre 20 y 35 grados
            int maxDegrees = minDegrees + (int) (Math.random() * 10); // Ejemplo: entre minDegrees y minDegrees + 10
            int humidityPercentage = (int) (Math.random() * 100); // Ejemplo: entre 0 y 100
            int windSpeed = (int) (Math.random() * 20); // Ejemplo: entre 0 y 20 km/h
            int precipitation = (int) (Math.random() * 50); // Ejemplo: entre 0 y 50 mm

            WeatherData weatherDay = new WeatherData(date, minDegrees, maxDegrees, humidityPercentage, windSpeed, precipitation);
            data.add(weatherDay);
        }



        return data;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMinDegrees() {
        return minDegrees;
    }

    public void setMinDegrees(int minDegrees) {
        this.minDegrees = minDegrees;
    }

    public int getMaxDegrees() {
        return maxDegrees;
    }

    public void setMaxDegrees(int maxDegrees) {
        this.maxDegrees = maxDegrees;
    }

    public int getHumidityPercentage() {
        return humidityPercentage;
    }

    public void setHumidityPercentage(int humidityPercentage) {
        this.humidityPercentage = humidityPercentage;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(int precipitation) {
        this.precipitation = precipitation;
    }
}
