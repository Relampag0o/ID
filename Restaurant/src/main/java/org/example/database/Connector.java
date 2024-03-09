package org.example.database;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.example.classes.Product;
import org.example.classes.Report;
import org.example.classes.Table;
import org.example.controllers.PrimaryController;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
                product = new Product(id, name, price, 1);
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
        String query = "INSERT INTO table_product (tablee_id, product_id, quantity) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, table.getId());
            preparedStatement.setString(2, product.getId());
            preparedStatement.setInt(3, product.getQuantity()); // Set the quantity to the actual quantity of the product
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getProducts(String tableId) {
        List<Product> products = new ArrayList<Product>();
        String query = "SELECT product.*, COUNT(product_id) as quantity FROM product INNER JOIN table_product ON product.id = table_product.product_id WHERE table_product.tablee_id = ? GROUP BY product.id, table_product.tablee_id";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tableId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getString("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setQuantity(resultSet.getInt("quantity"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public void updateTableTotal(Table table) {
        System.out.println("Updating table total for table id: " + table.getId() + " with total: " + table.getTotal());
        String query = "UPDATE tablee SET total = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, table.getTotal());
            preparedStatement.setString(2, table.getId());
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Table total updated successfully.");
                System.out.println("Total: " + table.getTotal() + " for table: " + table.getName() + " with id: " + table.getId());
            } else {
                System.out.println("No table found with id: " + table.getId());
            }
        } catch (SQLException e) {
            System.out.println("SQLException occurred while updating table total.");
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

            InputStream reportStream = getClass().getResourceAsStream("/tableReport.jrxml");
            JasperReport report = JasperCompileManager.compileReport(reportStream);


            Map<String, Object> parameters = new HashMap<>();
            parameters.put("id_table", table.getId());
            parameters.put("total", table.getTotal() + "€");
            parameters.put("REPORT_DATE", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", new Locale("es", "ES"))));


            JasperPrint print = JasperFillManager.fillReport(report, parameters, this.connection);
            JasperViewer.viewReport(print, false);

            // Export the report to a PDF file
            JasperExportManager.exportReportToPdfFile(print, "report_" + table.getId() + ".pdf");
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void generateHistoricOfAllTables() {
        try {
            InputStream reportStream = getClass().getResourceAsStream("/historicTables.jrxml");
            JasperReport report = JasperCompileManager.compileReport(reportStream);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("REPORT_DATE", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", new Locale("es", "ES"))));
            parameters.put("total", getTotal());

            JasperPrint print = JasperFillManager.fillReport(report, parameters, this.connection);
            JasperViewer.viewReport(print, false);

            JasperExportManager.exportReportToPdfFile(print, "report_all_tables.pdf");
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void removeTableProduct(Table table, Product product) {
        String query = "DELETE FROM table_product WHERE tablee_id = ? AND product_id = ? LIMIT 1";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, table.getId());
            preparedStatement.setString(2, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTableProduct(Table table, Product product) {
        String query = "DELETE FROM table_product WHERE tablee_id = ? AND product_id = ? LIMIT 1";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, table.getId());
            preparedStatement.setString(2, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getTotal() {
        String query = "SELECT SUM(total) FROM tablee";
        double total=0 ;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                 total = resultSet.getDouble(1);
                System.out.println("Total: " + total);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total+"€";
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
