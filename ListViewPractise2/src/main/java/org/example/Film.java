package org.example;

import java.net.URL;

public class Film {
    private String title;
    private String director;
    private String year;

    private String url;

    public Film(String title, String director, String year, String url) {
        this.title = title;
        this.director = director;
        this.year = year;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return title + director + year + url;
    }
}
