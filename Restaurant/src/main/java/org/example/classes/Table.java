package org.example.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Table {
    private String id;
    private String name;
    private double total;

    public HashMap<Product,Integer> products;


    public Table(String id, String name) {
        this.id = id;
        this.name = name;
        this.total = 0;
        this.products = new HashMap<Product,Integer>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        this.products.put(product,1);
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
