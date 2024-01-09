package org.example;

public class Book {
    private int id;
    private String title;
    private String author;
    private String genre;


    public Book(int id, String title, String author, String genre, Boolean borrowed) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.borrowed = borrowed;
    }

    private Boolean borrowed;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Boolean getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(Boolean borrowed) {
        this.borrowed = borrowed;
    }
}
