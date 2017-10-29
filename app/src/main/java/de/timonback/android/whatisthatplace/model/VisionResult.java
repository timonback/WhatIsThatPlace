package de.timonback.android.whatisthatplace.model;

import org.json.JSONObject;

public class VisionResult {
    private final String identifier;
    private final JSONObject response;

    public VisionResult(String identifier, JSONObject response) {
        this.identifier = identifier;
        this.response = response;
    }

    public String getIdentifier() {
        return identifier;
    }

    public JSONObject getResponse() {
        return response;
    }
}
