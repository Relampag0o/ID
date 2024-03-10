package org.example.classes;

import java.util.Objects;

// THE CLASS PRODUCT IS USED TO CREATE OBJECTS THAT REPRESENT PRODUCTS IN THE RESTAURANT
public class Product {

    private String id;
    private String name;
    private double price;

    private int quantity;

    //    THE CONSTRUCTOR IS USED TO CREATE A PRODUCT OBJECT
    public Product() {

    }


    //   THE CONSTRUCTOR IS USED TO CREATE A PRODUCT OBJECT
    public Product(String id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price + ", quantity=" + quantity +
                '}';
    }

    // THE EQUALS METHOD IS OVERRIDDEN TO COMPARE THE ID OF THE PRODUCTS
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }

    // THE HASHCODE METHOD IS OVERRIDDEN TO RETURN THE HASHCODE OF THE ID
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
