package org.example.database;

import org.example.classes.Product;
import org.example.classes.Table;

import java.sql.*;

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

    public void closeConnection() {
        try {
            this.connection.close();
            System.out.println("Connection closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
