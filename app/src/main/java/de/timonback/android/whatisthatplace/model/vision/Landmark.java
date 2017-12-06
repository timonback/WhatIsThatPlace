package de.timonback.android.whatisthatplace.model.vision;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Landmark {
    @SerializedName("bounding_poly")
    private BoundingPoly boundingPoly;
    private List<Location> locations;
    private String description;
    private String score;
    private String mid;

    public BoundingPoly getBoundingPoly() {
        return boundingPoly;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public String getDescription() {
        return description;
    }

    public String getScore() {
        return score;
    }

    public String getMid() {
        return mid;
    }

    @Override
    public String toString() {
        return "Landmark [bounding_poly = " + boundingPoly.toString() + ", locations = " + locations.toString() + ", description = " + description + ", score = " + score + ", mid = " + mid + "]";
    }
}