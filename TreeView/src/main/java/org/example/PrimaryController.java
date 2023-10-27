package org.example;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class PrimaryController {

    @FXML
    public TreeView<String> treeV;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    public void initialize() {
        treeV.setRoot(create(new File(System.getProperty("user.dir"))));
    }


    public TreeItem<String> create(File f) {
        File[] dirs = f.listFiles();
        TreeItem<String> treeItem = new TreeItem<String>(f.getName());
        for (File file : dirs) {
            if (file.isDirectory())
                treeItem.getChildren().add(create(file));
            else {
                TreeItem<String> treeIt = new TreeItem<String>(file.getName());
                treeItem.getChildren().add(treeIt);
            }
        }

        return treeItem;
    }

}
