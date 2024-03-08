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
        System.out.println("List before adding product: ");


        int quantity=0;
        if (this.products.containsKey(product)) {
             quantity = this.products.get(product) + 1;
            this.products.put(product, quantity);
        } else {
            this.products.put(product, 1);
        }

        // Update the total price
        this.total += product.getPrice();
        connector.insertTableProduct(this, product);
        connector.updateTableTotal(this);

        System.out.println("List after adding product: " );
        showProducts();
        Report report = new Report(UUID.randomUUID().toString(), this.id, product.getId(), quantity, product.getPrice() * quantity, LocalDateTime.now());
        connector.insertReport(report);
    }

    public void removeProduct(Product product) {
        System.out.println("List before removing product: ");
        for (Map.Entry<Product, Integer> entry : this.products.entrySet()) {
            System.out.println(entry.getKey().getName() + " " + entry.getValue());
        }
        if (this.products.containsKey(product)) {
            int quantity = this.products.get(product);
            if (quantity > 1) {
                // If the quantity is more than 1, decrement it by 1
                this.products.put(product, quantity - 1);
                // Update the quantity of the product in the table_product table in the database
                connector.updateTableProduct(this, product);
            } else {
                // If the quantity is 1, remove the product from the list
                this.products.remove(product);
                // Remove the product from the table_product table in the database
                connector.removeTableProduct(this, product);
            }
        }
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

    public void showProducts() {
        for (Map.Entry<Product, Integer> entry : this.products.entrySet()) {
            System.out.println(entry.getKey().getName() + " " + entry.getValue() + " " + entry.getKey().getPrice() * entry.getValue() + "€");
        }
    }


    @Override
    public String toString() {
        return "Table{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", total=" + total +
                ", connector=" + connector +
                ", products=" + products +
                '}';
    }
}
