package org.example;

import java.io.*;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class PrimaryController {

    @FXML
    public TreeView<String> treeV;

    @FXML
    public TextArea textArea;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    public void initialize() {
        treeV.setRoot(create(new File(System.getProperty("user.dir"))));
        treeV.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            textArea.setText("");
            if (newValue != null) {
                try {
                    CustomTreeItem cti = (CustomTreeItem) newValue;
                    BufferedReader bfr = new BufferedReader(new FileReader(cti.getFile()));
                    String line = "";
                    while ((line = bfr.readLine()) != null) {
                        textArea.appendText(line + '\n');
                    }
                    bfr.close();
                } catch (Exception e) {

                }

            }

        }));

    }


    public TreeItem<String> create(File f) {
        File[] dirs = f.listFiles();
        CustomTreeItem treeItem = new CustomTreeItem(f);
        for (File file : dirs) {
            if (file.isDirectory())
                treeItem.getChildren().add(create(file));

            else {
                CustomTreeItem treeIt = new CustomTreeItem(file);
                treeItem.getChildren().add(treeIt);
            }
        }

        return treeItem;
    }

}
