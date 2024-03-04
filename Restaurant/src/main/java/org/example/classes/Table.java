package org.example.classes;

import org.example.database.Connector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {
    private String id;
    private String name;
    private double total;

    private Connector connector;
    public HashMap<Product, Integer> products;


    public Table(String id, String name) {
        this.id = id;
        this.name = name;
        this.total = 0;
        this.products = new HashMap<Product, Integer>();
        this.connector = new Connector();
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

    public void addProduct(Product product) {
        if (this.products.containsKey(product)) {
            // If the product is already in the list, increment the quantity by 1
            this.products.put(product, this.products.get(product) + 1);
        } else {
            // If the product is not in the list, add it with a quantity of 1
            this.products.put(product, 1);
        }

        // Update the total price
        this.total += product.getPrice();
        connector.insertTableProduct(this, product);
        connector.updateTableTotal(this);
    }

    public void removeProduct(Product product) {
        this.total -= product.getPrice();
        connector.updateTableTotal(this);
    }

    public double calculateTotal() {
        double total = 0.0;
        for (Map.Entry<Product, Integer> entry : this.products.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    public void clearTable() {
        this.products.clear();
        this.total = 0;
        connector.clearTableProducts(this);
        connector.updateTableTotal(this);
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
