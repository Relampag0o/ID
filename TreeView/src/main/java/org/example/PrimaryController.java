package org.example;

import java.io.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PrimaryController {

    @FXML
    public TabPane tabP;
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
                    String p = "";
                    while ((line = bfr.readLine()) != null) {
                        textArea.appendText(line + '\n');
                        p += line + '\n';
                    }
                    tabP.getTabs().add(tabP.getTabs().size() - 1, new CustomTab(cti.getFile(), new TextArea(p)));
                    saveData(cti.getFile());
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

    public void saveData(File f) {
        BufferedWriter bfw = null;
        try {
            bfw = new BufferedWriter(new FileWriter(f));
            bfw.write(textArea.getText());
            bfw.close();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}
