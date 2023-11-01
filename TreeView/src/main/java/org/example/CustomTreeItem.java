package org.example;

import javafx.scene.control.RadioButton;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class CustomTreeItem extends TreeItem<String> {

    public File file;

    public File getFile() {
        return file;
    }
    

    public CustomTreeItem(File f) {
        super(f.getName());
        this.file = f;
        if (f.isDirectory()) {
            ImageView folderIcon = new ImageView(new Image(getClass().getResourceAsStream("/folder.png")));
            folderIcon.setFitWidth(20);
            folderIcon.setFitHeight(20);
            this.setGraphic(folderIcon);
        } else if (f.isFile()) {
            ImageView fileIcon = new ImageView(new Image(getClass().getResourceAsStream("/file.png")));
            fileIcon.setFitWidth(20);
            fileIcon.setFitHeight(20);
            this.setGraphic(fileIcon);
        }
    }


}
