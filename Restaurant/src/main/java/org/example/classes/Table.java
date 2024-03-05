package org.example.classes;

import org.example.database.Connector;

import java.time.LocalDateTime;
import java.util.*;

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
        int quantity;
        if (this.products.containsKey(product)) {
            // If the product is already in the list, increment the quantity by 1
            quantity = this.products.get(product) + 1;
            this.products.put(product, quantity);
        } else {
            // If the product is not in the list, add it with a quantity of 1
            quantity = 1;
            this.products.put(product, quantity);
        }

        // Update the total price
        this.total += product.getPrice();
        connector.insertTableProduct(this, product, quantity);
        connector.updateTableTotal(this);

        Report report = new Report(UUID.randomUUID().toString(), this.id, product.getId(), quantity, product.getPrice() * quantity, LocalDateTime.now());
        connector.insertReport(report);
    }

    public void removeProduct(Product product) {
        this.total -= product.getPrice();
        connector.updateTableTotal(this);

        Report report = new Report(UUID.randomUUID().toString(), this.id, product.getId(), -1, -product.getPrice(), LocalDateTime.now());
        connector.insertReport(report);
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
