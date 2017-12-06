package de.timonback.android.whatisthatplace.service.api;

import de.timonback.android.whatisthatplace.model.vision.VisionResult;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

import static de.timonback.android.whatisthatplace.service.api.ApiParams.PATH_NAME;

public interface VisionServiceApi {
    @GET(ApiParams.URL_IMAGE_NAME_LANDMARK)
    Call<VisionResult> analyze(@Path(PATH_NAME) String name);

    @HEAD(ApiParams.URL_IMAGE_NAME)
    Call<Void> isUploaded(@Path(PATH_NAME) String name);

    @Multipart
    @POST(ApiParams.URL_IMAGE)
    Call<ResponseBody> upload(@Part MultipartBody.Part file);
}
