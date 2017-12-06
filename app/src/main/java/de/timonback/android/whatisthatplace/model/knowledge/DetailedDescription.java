package de.timonback.android.whatisthatplace.model.knowledge;

public class DetailedDescription {
    private String articleBody;
    private String license;
    private String url;

    public String getArticleBody() {
        return articleBody;
    }

    public String getLicense() {
        return license;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "DetailedDescription [articleBody = " + articleBody + ", license = " + license + ", url = " + url + "]";
    }
}
