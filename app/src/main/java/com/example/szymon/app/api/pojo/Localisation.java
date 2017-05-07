package com.example.szymon.app.api.pojo;


import com.google.gson.annotations.SerializedName;

public class Localisation {

    @SerializedName("latitude")
    private Point latitude;
    @SerializedName("longitude")
    private Point longitude;

    public Localisation(Point latitude, Point longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Latitude " + latitude + "\tLongitude " + longitude;
    }
}
