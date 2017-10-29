package de.timonback.android.whatisthatplace.service.api;


interface ApiParams {
    static final String AUTHORIZATION = "Authorization";
    static final String AUTH_TOKEN = "bcb58dcc-b346-11e7-abc4-cec278b6b50a";

    static final String PATH_NAME = "name";

    static final String URL_IMAGE = "/image";
    static final String URL_VISION = "/vision";
    static final String URL_VISION_NAME = URL_VISION+"/{name}";
}
