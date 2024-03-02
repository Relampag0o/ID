package org.example.classes;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private int id;
    private String name;
    private double total;

    public List<Product> products;


    public Table(int id, String name) {
        this.id = id;
        this.name = name;
        this.total = 0;
        this.products = new ArrayList<Product>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void addProduct(Product product){
        this.products.add(product);
        this.total += product.getPrice();
    }

    public void removeProduct(Product product){
        this.products.remove(product);
        this.total -= product.getPrice();
    }

    @Override
    public String toString() {
        return "Table{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", total=" + total +
                '}';
    }
}
