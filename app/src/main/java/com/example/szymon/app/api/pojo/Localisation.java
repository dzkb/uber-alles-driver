package com.example.szymon.app.api.pojo;


import com.google.gson.annotations.SerializedName;

public class Localisation {

    @SerializedName("latitude")
    private Point latitude;
    @SerializedName("longitude")
    private Point longitude;

    @Override
    public String toString() {
        return "Latitude " + latitude + "\tLongitude " + longitude;
    }
}
