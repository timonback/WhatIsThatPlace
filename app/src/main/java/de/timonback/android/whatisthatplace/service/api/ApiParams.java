package de.timonback.android.whatisthatplace.service.api;

import de.timonback.android.whatisthatplace.BuildConfig;

interface ApiParams {
    static final String AUTHORIZATION = "Authorization";
    static final String AUTH_TOKEN = BuildConfig.VISION_API_KEY;

    static final String PATH_NAME = "name";

    static final String URL_IMAGE = "/image";
    static final String URL_IMAGE_NAME = URL_IMAGE + "/{" + PATH_NAME + "}";
    static final String URL_IMAGE_NAME_LANDMARK = URL_IMAGE_NAME + "/landmark";

    static final String GOOGLE_KEY = BuildConfig.MAPS_API_KEY;
    static final String QUERY_KEY = "key";
    static final String QUERY_MID = "ids";
    static final String URL_KNOWLEDGE = "https://kgsearch.googleapis.com/v1/entities:search?limit=1"
            + "&" + QUERY_KEY + "=" + GOOGLE_KEY;
}
