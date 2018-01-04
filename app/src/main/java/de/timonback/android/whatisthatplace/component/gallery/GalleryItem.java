package de.timonback.android.whatisthatplace.component.gallery;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GalleryItem {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private final String title;
    private final File file;

    public GalleryItem(String title, File file) {
        this.title = title;
        this.file = file;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        Date modificationDate = new Date(getImageFile().lastModified());
        return sdf.format(modificationDate);
    }

    public File getImageFile() {
        return file;
    }
}
