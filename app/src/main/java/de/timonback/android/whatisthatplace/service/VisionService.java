package de.timonback.android.whatisthatplace.service;


import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.File;

import de.timonback.android.whatisthatplace.model.vision.VisionResult;
import de.timonback.android.whatisthatplace.service.api.ApiFactory;
import de.timonback.android.whatisthatplace.util.FileUtils;
import de.timonback.android.whatisthatplace.util.MD5;
import de.timonback.android.whatisthatplace.util.MyCallable;
import de.timonback.android.whatisthatplace.util.MyParamCallable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisionService {
    private static final String LOG_NAME = VisionService.class.getName();

    private final Context context;

    VisionService(Context c) {
        context = c;
    }

    public void analyse(final Uri fileUri, final MyParamCallable<VisionResult> callback, final MyCallable failureCallback) {
        final File file = FileUtils.getFile(context, fileUri);
        final String checksum = MD5.calculateMD5(file);

        checkForImageUpload(file, fileUri, checksum, new MyCallable() {
            @Override
            public void call() {
                analyzeImage(checksum, callback, failureCallback);
            }
        }, failureCallback);
    }

    private void checkForImageUpload(final File file, final Uri fileUri, final String checksum, final MyCallable callback, final MyCallable failureCallback) {
        ApiFactory.getVisionServiceApi().isUploaded(checksum)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (!response.isSuccessful()) {
                            uploadFile(fileUri, file).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call,
                                                       Response<ResponseBody> response) {
                                    Log.v(LOG_NAME, "file was uploaded");

                                    callback.call();
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Log.e(LOG_NAME, t.getMessage());
                                    failureCallback.call();
                                }
                            });
                        } else {
                            //Run callback directly
                            callback.call();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e(LOG_NAME, t.getMessage());
                        failureCallback.call();
                    }
                });
    }

    private Call<ResponseBody> uploadFile(Uri fileUri, File file) {
        String type = context.getContentResolver().getType(fileUri);

        MediaType mediaType = null;
        if (type != null) {
            mediaType = MediaType.parse(type);
        }

        RequestBody requestFile = RequestBody.create(
                mediaType,
                file
        );

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);


        return ApiFactory.getVisionServiceApi().upload(body);
    }

    private void analyzeImage(String checksum, final MyParamCallable<VisionResult> callable, final MyCallable failureCallback) {
        // Wait first for the other request to finish...
        ApiFactory.getVisionServiceApi().analyze(checksum).enqueue(new Callback<VisionResult>() {
            @Override
            public void onResponse(Call<VisionResult> call, Response<VisionResult> response) {
                Log.v(LOG_NAME, "analyze response " + response.body().toString());

                callable.call(response.body());
            }

            @Override
            public void onFailure(Call<VisionResult> call, Throwable t) {
                Log.e(LOG_NAME, t.getMessage());
                failureCallback.call();
            }
        });
    }
}
