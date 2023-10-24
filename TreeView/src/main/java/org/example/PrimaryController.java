package org.example;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class PrimaryController {

    @FXML
    private TreeView<String> treeV;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    public void initialize() {
        create(new File("C:\\Users\\Jose\\"));
    }


    public TreeItem<String> create(File f) {
        File[] dirs = f.listFiles();
        TreeItem<String> treeItem = new TreeItem<String>(f.getName());
        for (File file : dirs) {
            if (file.isDirectory())
                treeItem.getChildren().add(create(file));
            else
                treeItem.getChildren().add(treeItem);
        }

        return treeItem;
    }
    
}
