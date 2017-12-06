package de.timonback.android.whatisthatplace.service;

import android.content.Context;
import android.util.Log;

import de.timonback.android.whatisthatplace.model.knowledge.KnowledgeResult;
import de.timonback.android.whatisthatplace.service.api.ApiFactory;
import de.timonback.android.whatisthatplace.util.MyParamCallable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KnowledgeService {
    private static final String LOG_NAME = VisionService.class.getName();

    private final Context context;

    KnowledgeService(Context c) {
        context = c;
    }

    public void getKnowledgeInfo(String mid, final MyParamCallable<KnowledgeResult> callback) {
        ApiFactory.getKnowledgeApi().query(mid).enqueue(new Callback<KnowledgeResult>() {
            @Override
            public void onResponse(Call<KnowledgeResult> call, Response<KnowledgeResult> response) {
                Log.d(LOG_NAME, "success " + response.body().toString());

                callback.call(response.body());
            }

            @Override
            public void onFailure(Call<KnowledgeResult> call, Throwable t) {
                Log.d(LOG_NAME, "failure", t);
            }
        });
    }
}
