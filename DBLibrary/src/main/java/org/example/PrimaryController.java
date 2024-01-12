package org.example;

import java.io.IOException;
import java.util.Collection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class PrimaryController {

    public TableView tableView;
    public TextField genreField;
    public TextField titleField;
    public TextField authorField;
    public Button addButton;
    public Button updateButton;
    public Button deleteButton;

    private ObservableList<Book> observableBooksList;


    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    public void initialize() {
        BookRepository br = new BookRepository();
        br.getBooks();
        addColumns();
    }

    public void addColumns() {
        tableView.setEditable(true);
        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        TableColumn<Book, String> genreColumn = new TableColumn<>("Genre");
        TableColumn<Book, Boolean> borrowedColumn = new TableColumn<>("Borrowed");

        tableView.getColumns().addAll(titleColumn, authorColumn, genreColumn, borrowedColumn);

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        borrowedColumn.setCellValueFactory(new PropertyValueFactory<>("borrowed"));

        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        authorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        genreColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        importData();
    }

    public void importData() {
        BookRepository br = new BookRepository();
        observableBooksList = FXCollections.observableArrayList(br.getBooks());
        tableView.setItems(observableBooksList);
    }

    public void addBook() {
        if (titleField != null && authorField != null && genreField != null) {
            BookRepository br = new BookRepository();
            br.createBook(titleField.getText(), authorField.getText(), genreField.getText(), false);
            importData();
        }
    }

    public void updateBook() {
        BookRepository br = new BookRepository();
        if (titleField != null && authorField != null && genreField != null && tableView.getSelectionModel().getSelectedItem() != null) {
            Book selectedBook = (Book) tableView.getSelectionModel().getSelectedItem();
            br.updateBook(selectedBook.getId(), titleField.getText(), authorField.getText(), genreField.getText());
            importData();
            titleField.clear(); authorField.clear(); genreField.clear();

        }
    }

    public void deleteBook() {
        BookRepository br = new BookRepository();
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            br.deleteBook(((Book) tableView.getSelectionModel().getSelectedItem()).getId());
            importData();
        }
    }

}
