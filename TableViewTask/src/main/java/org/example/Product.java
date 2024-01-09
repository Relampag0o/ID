package org.example;

import java.util.Date;

public class Product {
    private String name;
    private double Price;
    private int stock;
    private double weight;

    private Date expirationDate;

    private String color;
    private String material;
    private String modelId;

    public Product(String name, double price, int stock, double weight, Date expirationDate, String color, String material, String modelId) {
        this.name = name;
        Price = price;
        this.stock = stock;
        this.weight = weight;
        this.expirationDate = expirationDate;
        this.color = color;
        this.material = material;
        this.modelId = modelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    @Override
    public String toString() {
        return "org.example.Product{" +
                "name='" + name + '\'' +
                ", Price=" + Price +
                ", stock=" + stock +
                ", weight=" + weight +
                ", expirationDate=" + expirationDate +
                ", color='" + color + '\'' +
                ", material='" + material + '\'' +
                ", modelId=" + modelId +
                '}';
    }
}
