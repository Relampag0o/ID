package org.example.database;

import org.example.classes.Product;
import org.example.classes.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Connector {

    private Connection connection;

    public Connector() {
        openConnection();
    }


    public void openConnection() {
        String db = "restaurant";
        String host = "localhost";
        String port = "3306";
        String urlConnection = "jdbc:mariadb://" + host + ":" + port + "/" + db;
        String user = "root";
        String password = "5856101097";
        try {
            this.connection = DriverManager.getConnection(urlConnection, user, password);
            System.out.println("Connected");

        } catch (SQLException e) {
            //showSQLError(e);
            e.printStackTrace();
        }

    }

    public Product findProduct(String id) {
        Product product = null;
        String query = "SELECT * FROM product WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                product = new Product(id, name, price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    public Table findTable(String id) {
        Table table = null;
        String query = "SELECT * FROM tablee WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                table = new Table(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return table;
    }

    public void insertTableProduct(Table table, Product product) {
        String query = "INSERT INTO table_product (tablee_id, product_id) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, table.getId());
            preparedStatement.setString(2, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getProducts( String tableId) {
        List<Product> products = new ArrayList<Product>();
        String query = "SELECT * FROM product INNER JOIN table_product ON product.id = table_product.product_id WHERE table_product.tablee_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tableId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                Product product = new Product(id, name, price);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public void closeConnection() {
        try {
            this.connection.close();
            System.out.println("Connection closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
