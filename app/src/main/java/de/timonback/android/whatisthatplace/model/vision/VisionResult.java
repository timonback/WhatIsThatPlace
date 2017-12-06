package de.timonback.android.whatisthatplace.model.vision;

// Generated via http://pojo.sodhanalibrary.com/Convert

import java.util.ArrayList;
import java.util.List;

public class VisionResult {
    private List<Landmark> landmarks = new ArrayList<>();

    public List<Landmark> getLandmarks() {
        return landmarks;
    }

    public void setLandmarks(List<Landmark> landmarks) {
        this.landmarks = landmarks;
    }

    @Override
    public String toString() {
        return "VisionResult [landmarks = " + landmarks.toString() + "]";
    }
}
