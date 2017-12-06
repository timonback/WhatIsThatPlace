package de.timonback.android.whatisthatplace.service.api;


interface ApiParams {
    static final String AUTHORIZATION = "Authorization";
    static final String AUTH_TOKEN = "988c4dcf-d7d2-45f1-b4ec-9123a0ab61d1";

    static final String PATH_NAME = "name";

    static final String URL_IMAGE = "/image";
    static final String URL_IMAGE_NAME = URL_IMAGE + "/{" + PATH_NAME + "}";
    static final String URL_IMAGE_NAME_LANDMARK = URL_IMAGE_NAME + "/landmark";

    static final String GOOGLE_KEY = "AIzaSyD26X-CzeqyqPbtHDgMWu-YstQTSJLps_Y";
    static final String QUERY_KEY = "key";
    static final String QUERY_MID = "ids";
    static final String URL_KNOWLEDGE = "https://kgsearch.googleapis.com/v1/entities:search?limit=1"
            + "&" + QUERY_KEY + "=" + GOOGLE_KEY;
}
