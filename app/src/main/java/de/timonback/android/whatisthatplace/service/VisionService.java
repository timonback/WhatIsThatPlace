package de.timonback.android.whatisthatplace.service;


import android.content.Context;
import android.net.Uri;

import java.io.File;
import java.net.MalformedURLException;

import de.timonback.android.whatisthatplace.service.api.ApiFactory;
import de.timonback.android.whatisthatplace.util.FileUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class VisionService {
    private static final String LOG_NAME = VisionService.class.getName();

    private final Context context;

    VisionService(Context c) {
        context = c;
    }

    public void analyse(Uri fileUri) throws MalformedURLException {
        String type = context.getContentResolver().getType(fileUri);
        File file = FileUtils.getFile(context, fileUri);

        RequestBody requestFile = RequestBody.create(
                MediaType.parse(type),
                file
        );

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("picture", file.getName(), requestFile);

        //ApiFactory.getVisionServiceApi().analyze();
    }
}
