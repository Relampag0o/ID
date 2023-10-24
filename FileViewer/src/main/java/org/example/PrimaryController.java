package org.example;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class PrimaryController {

    public int row = 0;
    public int column = 0;
    public File parentFolder = null;
    public File currentFolder = null;
    @FXML
    public GridPane gridPane;

    @FXML
    public Button clearButton;

    public void initialize() {
        createWindow(new File(System.getProperty("user.home")));
    }


    public void createWindow(File fi) {
        File[] dirs = fi.listFiles();
        currentFolder = fi;
        if (dirs != null) {
            for (File file : dirs) {
                if (file.isDirectory()) {
                    createDirectoryButton(file);
                } else {
                    createFileElement(file);
                }
            }
        }
    }

    public void createDirectoryButton(File directory) {
        Button b = new Button(directory.getName());
        b.setMinHeight(20);
        b.setMinWidth(100);
        b.setOnAction(actionEvent -> {
            gridPane.getChildren().clear();
            column = 0;
            row = 0;
            createWindow(directory);

        });
        addToGridPane(b);
    }

    public void createFileElement(File file) {
        String[] filename = file.getName().split("\\.");
        if (filename.length > 1 && (filename[1].contains("png") || filename[1].contains("jpg"))) {
            createImageElement(file);
        } else {
            createLabelElement(file);
        }
    }

    public void createImageElement(File file) {
        ImageView imgView = new ImageView();
        Image img = new Image("file:" + file.getAbsolutePath());
        imgView.setImage(img);
        imgView.setFitHeight(150);
        imgView.setFitWidth(150);
        addToGridPane(imgView);
    }

    public void createLabelElement(File file) {
        Label l = new Label(file.getName());
        addToGridPane(l);
    }

    public void addToGridPane(Node node) {
        gridPane.add(node, column, row);
        column++;
        if (column >= 3) {
            column = 0;
            row++;
        }
    }

    public void goBack() {
        parentFolder = currentFolder.getParentFile();
        if (parentFolder != null) {
            gridPane.getChildren().clear();
            column = 0;
            row = 0;
            createWindow(parentFolder);

        }
    }


}


