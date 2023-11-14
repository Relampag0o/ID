module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires okhttp3;
    requires com.fasterxml.jackson.databind;

    opens org.example.apimodels to com.fasterxml.jackson.databind;
    opens org.example to javafx.fxml;
    exports org.example;
}
