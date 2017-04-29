package com.example.tomek.uberallesdriver.api.pojo;

import com.google.gson.annotations.SerializedName;


public class Fare {

    @SerializedName("startingPoint")
    private Point startingPoint;
    @SerializedName("endingPoint")
    private Point endingPoint;
    @SerializedName("clientName")
    private String clientName;
    @SerializedName("clientPhone")
    private Integer clientPhone;
    @SerializedName("startingDate")
    private String startingDate;

    public Fare(Point startingPoint, Point endingPoint, String clientName, Integer clientPhone, String startingDate) {
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.startingDate = startingDate;
    }

    public Fare() {
    }

    public void setStartingPoint(Point startingPoint) {
        this.startingPoint = startingPoint;
    }

    public void setEndingPoint(Point endingPoint) {
        this.endingPoint = endingPoint;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setClientPhone(Integer clientPhone) {
        this.clientPhone = clientPhone;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public Point getStartingPoint() {
        return startingPoint;
    }

    public Point getEndingPoint() {
        return endingPoint;
    }

    public String getClientName() {
        return clientName;
    }

    public Integer getClientPhone() {
        return clientPhone;
    }

    public String getStartingDate() {
        return startingDate;
    }
}
