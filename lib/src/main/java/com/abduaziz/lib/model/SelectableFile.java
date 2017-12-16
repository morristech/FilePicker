package com.abduaziz.lib.model;

import java.io.File;

/**
 * Created by abduaziz on 12/16/17.
 */

public class SelectableFile {

    File file;
    boolean selected;

    public SelectableFile(File file, boolean selected) {
        this.file = file;
        this.selected = selected;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
