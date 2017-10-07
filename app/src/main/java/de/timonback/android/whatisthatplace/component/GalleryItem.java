package de.timonback.android.whatisthatplace.component;

import java.io.File;

public class GalleryItem {
    private final String title;
    private final File file;

    public GalleryItem(String title, File file) {
        this.title = title;
        this.file = file;
    }

    public String getTitle() {
        return title;
    }

    public File getImageFile() {
        return file;
    }
}
