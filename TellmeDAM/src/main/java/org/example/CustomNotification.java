package org.example;

import io.github.palexdev.materialfx.controls.MFXSimpleNotification;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class CustomNotification extends MFXSimpleNotification {

    private final StringProperty headerText = new SimpleStringProperty("Notification Header");
    private final StringProperty contentText = new SimpleStringProperty();

    public CustomNotification() {
        Label headerLabel = new Label();
        headerLabel.textProperty().bind(headerText);

        HBox header = new HBox(10, headerLabel);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setSpacing(10);

        Label contentLabel = new Label();
        contentLabel.getStyleClass().add("content");
        contentLabel.textProperty().bind(contentText);
        contentLabel.setWrapText(true);
        contentLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        contentLabel.setAlignment(Pos.TOP_LEFT);

        BorderPane container = new BorderPane();
        container.getStyleClass().add("notification");
        container.setTop(header);
        container.setCenter(contentLabel);
        container.setMaxWidth(400);

        setContent(container);
    }

    public void setHeaderText(String text) {
        headerText.set(text);
    }

    public void setContentText(String text) {
        contentText.set(text);
    }
}
