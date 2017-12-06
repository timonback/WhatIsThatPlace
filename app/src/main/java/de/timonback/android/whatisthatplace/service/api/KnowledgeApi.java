package de.timonback.android.whatisthatplace.service.api;

import de.timonback.android.whatisthatplace.model.knowledge.KnowledgeResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static de.timonback.android.whatisthatplace.service.api.ApiParams.QUERY_MID;

public interface KnowledgeApi {
    @GET(ApiParams.URL_KNOWLEDGE)
    Call<KnowledgeResult> query(@Query(QUERY_MID) String mid);
}