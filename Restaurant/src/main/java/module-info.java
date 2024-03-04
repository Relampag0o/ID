module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mfx.core;
    requires jasperreports;
    requires org.slf4j;

    opens org.example to javafx.fxml;
    exports org.example;
    exports org.example.controllers;
    opens org.example.controllers to javafx.fxml;
    exports org.example.classes;
    opens org.example.classes to javafx.fxml;
}
