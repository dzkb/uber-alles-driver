package com.example.szymon.app.api.pojo;

import com.google.gson.annotations.SerializedName;


public class Fare {

    @SerializedName("id")
    private String id;
    @SerializedName("reserved")
    private Boolean reserved;
    @SerializedName("startingPoint")
    private Point startingPoint;
    @SerializedName("endingPoint")
    private Point endingPoint;
    @SerializedName("clientName")
    private String clientName;
    @SerializedName("clientPhone")
    private Integer clientPhone;
    @SerializedName("driverPhone")
    private Integer driverPhone;
    @SerializedName("startingDate")
    private String startingDate;
    @SerializedName("placedDate")
    private String placedDate;
    @SerializedName("status")
    private String status;
}
