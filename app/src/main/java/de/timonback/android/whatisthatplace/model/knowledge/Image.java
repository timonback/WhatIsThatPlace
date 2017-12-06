package de.timonback.android.whatisthatplace.model.knowledge;


public class Image {
    private String contentUrl;
    private String url;

    public String getContentUrl() {
        return contentUrl;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Image [contentUrl = " + contentUrl + ", url = " + url + "]";
    }
}
