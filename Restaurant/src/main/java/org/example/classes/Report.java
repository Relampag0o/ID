package org.example.classes;

import java.time.LocalDateTime;

// THE CLASS REPORT IS USED TO CREATE OBJECTS THAT REPRESENT REPORTS IN THE RESTAURANT
public class Report {
    private String id;
    private String tableId;
    private String productId;
    private int quantity;
    private double price;
    private LocalDateTime transactionTime;




    //   THE CONSTRUCTOR IS USED TO CREATE A REPORT OBJECT
    public Report(String id, String tableId, String productId, int quantity, double price, LocalDateTime transactionTime) {
        this.id = id;
        this.tableId = tableId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.transactionTime = transactionTime;

    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double totalPrice) {
        this.price = totalPrice;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    //   THE METHOD IS USED TO RETURN A STRING REPRESENTATION OF THE REPORT OBJECT
    @Override
    public String toString() {
        return "Report{" +
                "id='" + id + '\'' +
                ", tableId='" + tableId + '\'' +
                ", productId='" + productId + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", transactionTime=" + transactionTime +
                '}';
    }
}
