module org.example {
    requires javafx.controls;
    requires javafx.fxml;

    requires jasperreports;
    requires java.sql;
    requires org.slf4j;
    opens org.example to javafx.fxml;
    exports org.example;
}
