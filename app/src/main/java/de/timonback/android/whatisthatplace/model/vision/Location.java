package de.timonback.android.whatisthatplace.model.vision;

public class Location {
    private String longitude;
    private String latitude;

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    @Override
    public String toString() {
        return "Location [longitude = " + longitude + ", latitude = " + latitude + "]";
    }
}

