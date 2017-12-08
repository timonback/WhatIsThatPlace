package de.timonback.android.whatisthatplace.model.vision;

public class Location {
    private Double longitude;
    private Double latitude;

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    @Override
    public String toString() {
        return "Location [longitude = " + longitude + ", latitude = " + latitude + "]";
    }
}

