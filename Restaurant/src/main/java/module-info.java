module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mfx.core;

    opens org.example to javafx.fxml;
    exports org.example;
    exports org.example.controllers;
    opens org.example.controllers to javafx.fxml;
}
