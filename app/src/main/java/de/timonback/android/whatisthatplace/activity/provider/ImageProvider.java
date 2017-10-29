package de.timonback.android.whatisthatplace.activity.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class ImageProvider {
    private static final Comparator<File> modifiedComparator = new Comparator<File>() {
        @Override
        public int compare(File o1, File o2) {
            if(o1.lastModified() > o2.lastModified()) {
                return -1;
            }
            return 1;
        }
    };

    public static List<File> getFilePaths(Context context) {
        List<File> resultIAV = new ArrayList<>();

        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor c = null;
        Uri u = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        if (u != null)
        {
            c = context.getContentResolver().query(u, projection, null, null, null);
        }

        Set<String> directories = new TreeSet<>();
        if ((c != null) && (c.moveToFirst())) {
            do {
                String tempDir = c.getString(0);
                tempDir = tempDir.substring(0, tempDir.lastIndexOf("/"));
                try {
                    directories.add(tempDir);
                } catch (Exception e) {

                }
            }
            while (c.moveToNext());

            c.close();
        }

        for (String directory: directories) {
            File imageDir = new File(directory);
            File[] imageList = imageDir.listFiles();
            if (imageList == null)
                continue;
            for (File imagePath : imageList) {
                try {

                    if (imagePath.isDirectory()) {
                        imageList = imagePath.listFiles();
                    }
                    if (imagePath.getName().contains(".jpg") || imagePath.getName().contains(".JPG")
                            || imagePath.getName().contains(".jpeg") || imagePath.getName().contains(".JPEG")
                            || imagePath.getName().contains(".png") || imagePath.getName().contains(".PNG")
                            || imagePath.getName().contains(".gif") || imagePath.getName().contains(".GIF")
                            || imagePath.getName().contains(".bmp") || imagePath.getName().contains(".BMP")
                            ) {

                        resultIAV.add(imagePath);

                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        Collections.sort(resultIAV, modifiedComparator);

        return resultIAV;
    }
}
