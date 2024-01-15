package org.example;

public class Book {

    public String title;
    public int sales;

    public Book(String title, int sales) {
        this.title = title;
        this.sales = sales;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", sales=" + sales +
                '}';
    }
}
