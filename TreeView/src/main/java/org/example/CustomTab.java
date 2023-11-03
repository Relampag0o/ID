package org.example;

import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;

import java.io.File;


public class CustomTab extends Tab {
    public File f;

    public CustomTab(File f, TextArea t) {
        super(f.getName(),t);
        this.f = f;
    }

}
