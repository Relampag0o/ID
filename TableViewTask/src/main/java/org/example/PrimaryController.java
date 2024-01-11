package org.example;

import java.io.IOException;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class PrimaryController {

    @FXML
    TableView tableV;
    private ObservableList<ObservableList<Product>> data = FXCollections.observableArrayList();


    @FXML
    TextField searchField;


    public void initialize() {

        addColumns();

    }

    // THE FOLLOWING COMMENTS ARE THERE BECAUSE I DONT WANT TO FORGET WHAT I WROTE (specially in the exam)
    // ITS NOT BECAUSE I COPIED FROM THE AI  ;) !!!!

    public void addColumns() {

        // setting the tableView as editable before doing anything:
        tableV.setEditable(true);
        // creating the columns:
        TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Product, Double> priceColumn = new TableColumn<>("Price");
        TableColumn<Product, Integer> stockColumn = new TableColumn<>("Stock");
        TableColumn<Product, Date> expirationDateColumn = new TableColumn<>("Expiration date");
        TableColumn<Product, String> colorColumn = new TableColumn<>("Color");
        TableColumn<Product, String> materialColumn = new TableColumn<>("Material");
        TableColumn<Product, String> modelIdColumn = new TableColumn<>("modelId");

        // setting those to the tableView:
        tableV.getColumns().addAll(nameColumn, priceColumn, stockColumn, expirationDateColumn, colorColumn, materialColumn, modelIdColumn);

        // setting a "reference" to those columns;
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        expirationDateColumn.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
        colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
        materialColumn.setCellValueFactory(new PropertyValueFactory<>("material"));
        modelIdColumn.setCellValueFactory(new PropertyValueFactory<>("modelId"));

        // making those columns editable:
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        stockColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        expirationDateColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
        colorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        materialColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        modelIdColumn.setCellFactory(TextFieldTableCell.forTableColumn());


        // adding products:
        addData();


    }

    public void addData() {
        Product Product1 = new Product("Laptop", 999.99, 20, 2.5, new Date(),
                "Gris", "Plástico", "ABC123");
        Product Product2 = new Product("Smartphone", 599.99, 50, 0.3, new Date(),
                "Negro", "Metal", "XYZ789");
        Product Product3 = new Product("Televisor", 799.99, 15, 12.0, new Date(),
                "Negro", "Plástico", "DEF456");
        Product Product4 = new Product("Zapatillas", 49.99, 100, 0.5, new Date(),
                "Blanco", "Tela", "123GHI");
        Product Product5 = new Product("Refrigerador", 799.99, 10, 50.0, new Date(),
                "Plateado", "Metal", "456JKL");
        Product Product6 = new Product("Cámara Digital", 299.99, 30, 0.2, new
                Date(), "Rojo", "Plástico", "MNO789");
        Product Product7 = new Product("Reloj", 199.99, 25, 0.1, new Date(), "Oro",
                "Metal", "PQR012");
        Product Product8 = new Product("Bicicleta", 349.99, 5, 12.0, new Date(),
                "Azul", "Metal", "STU345");
        Product Product9 = new Product("Libro", 19.99, 200, 0.5, new Date(), "N/A",
                "Papel", "VWX678");
        Product Product10 = new Product("Impresora", 149.99, 12, 10.0, new Date(),
                "Blanco", "Plástico", "YZA901");
        Product Product11 = new Product("Auriculares", 39.99, 30, 0.2, new Date(),
                "Negro", "Plástico", "BCD234");
        Product Product12 = new Product("Teclado inalámbrico", 29.99, 25, 0.5, new
                Date(), "Blanco", "Plástico", "EFG567");
        Product Product13 = new Product("Silla de oficina", 79.99, 15, 10.0, new
                Date(), "Negro", "Cuero", "HIJ890");
        Product Product14 = new Product("Cafetera", 49.99, 20, 3.0, new Date(),
                "Plateado", "Metal", "KLM123");
        Product Product15 = new Product("Horno de microondas", 69.99, 10, 15.0, new
                Date(), "Blanco", "Metal", "NOP456");
        Product Product16 = new Product("Pelota de fútbol", 19.99, 50, 0.7, new
                Date(), "Blanco y negro", "Piel sintética", "QRS789");
        Product Product17 = new Product("Gafas de sol", 59.99, 40, 0.1, new Date(),
                "Negro", "Plástico", "TUV012");
        Product Product18 = new Product("Mesa de comedor", 149.99, 8, 20.0, new
                Date(), "Marrón", "Madera", "WXY345");
        Product Product19 = new Product("Batería recargable", 29.99, 60, 0.3, new
                Date(), "Verde", "Metal", "YZB678");
        Product Product20 = new Product("Cepillo de dientes", 4.99, 100, 0.05, new
                Date(), "Azul", "Plástico", "CDE901");

        tableV.getItems().addAll(Product1, Product2, Product3, Product4, Product5, Product6, Product7, Product8, Product9, Product10, Product11, Product12, Product13, Product14, Product15, Product16, Product17, Product18, Product19, Product20);

        // starting to make the list able to be found by the textField sarch:

        List<Product> products = Arrays.asList(Product1, Product2, Product3, Product4, Product5, Product6, Product7, Product8, Product9, Product10, Product11, Product12, Product13, Product14, Product15, Product16, Product17, Product18, Product19, Product20);
        FilteredList<Product> filteredData =
                new FilteredList<>(FXCollections.observableList(products), p -> true);
        tableV.setItems(filteredData);


        FilteredList<Product> finalFilteredData = filteredData;
        searchField.textProperty().addListener((prop, old, text) -> {
            finalFilteredData.setPredicate(product -> {
                if (text == null || text.isEmpty()) return true;
                String name = product.getName().toLowerCase();
                return name.contains(text.toLowerCase());
            });
        });

        filteredData =
                new FilteredList<>(FXCollections.observableList(products), p -> true);
        SortedList<Product> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableV.comparatorProperty());
        tableV.setItems(sortedData);
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }


}
