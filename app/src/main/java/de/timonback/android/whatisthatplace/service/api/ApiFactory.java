package de.timonback.android.whatisthatplace.service.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {
    private static final String URL_APP = "http://vision.timonback.de:8888";

    private static final ApiFactory instance = new ApiFactory();

    private final KnowledgeApi knowledgeApi;
    private final VisionServiceApi visionServiceApi;

    private ApiFactory() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader(ApiParams.AUTHORIZATION, ApiParams.AUTH_TOKEN)
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_APP)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        knowledgeApi = retrofit.create(KnowledgeApi.class);
        visionServiceApi = retrofit.create(VisionServiceApi.class);
    }

    public static KnowledgeApi getKnowledgeApi() {
        return instance.knowledgeApi;
    }
    public static VisionServiceApi getVisionServiceApi() {
        return instance.visionServiceApi;
    }
}
