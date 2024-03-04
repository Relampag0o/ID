package org.example.database;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.example.classes.Product;
import org.example.classes.Report;
import org.example.classes.Table;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                product = new Product(id, name, price,1);
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
                double total = resultSet.getDouble("total"); // Retrieve the total from the database
                table = new Table(id, name);
                table.setTotal(total); // Set the total
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

    public List<Product> getProducts(String tableId) {
        List<Product> products = new ArrayList<Product>();
        String query = "SELECT product.*, COUNT(product_id) as quantity FROM product INNER JOIN table_product ON product.id = table_product.product_id WHERE table_product.tablee_id = ? GROUP BY product.id";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tableId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getString("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setQuantity(resultSet.getInt("quantity")); // Add this line
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public void updateTableTotal(Table table) {
        String query = "UPDATE tablee SET total = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, table.getTotal());
            preparedStatement.setString(2, table.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearTableProducts(Table table) {
        String query = "DELETE FROM table_product WHERE tablee_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, table.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertReport(Report report) {
        String query = "INSERT INTO Report (id, tableId, productId, quantity, totalPrice, transactionTime) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, report.getId());
            preparedStatement.setString(2, report.getTableId());
            preparedStatement.setString(3, report.getProductId());
            preparedStatement.setInt(4, report.getQuantity());
            preparedStatement.setDouble(5, report.getPrice());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(report.getTransactionTime()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void generateReport(Table table) {
        try {
            // Load the JasperReport template from the resources folder
            InputStream reportStream = getClass().getResourceAsStream("/report_template.jrxml");
            JasperReport report = JasperCompileManager.compileReport(reportStream);

            // Fetch the data from the Report table in the database for the selected table
            List<Report> reports = fetchReports(table);

            // Fill the report with data
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("tableId", table.getId());
            JasperPrint print = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(reports));

            // Export the report to a PDF file
            JasperExportManager.exportReportToPdfFile(print, "report_" + table.getId() + ".pdf");
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
    public List<Report> fetchReports(Table table) {
        List<Report> reports = new ArrayList<>();
        String query = "SELECT * FROM Report WHERE tableId = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, table.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String tableId = resultSet.getString("tableId");
                String productId = resultSet.getString("productId");
                int quantity = resultSet.getInt("quantity");
                double price = resultSet.getDouble("totalPrice");
                LocalDateTime transactionTime = resultSet.getTimestamp("transactionTime").toLocalDateTime();

                Report report = new Report(id, tableId, productId, quantity, price, transactionTime);
                reports.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reports;
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
