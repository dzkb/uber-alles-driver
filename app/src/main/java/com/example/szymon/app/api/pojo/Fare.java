package com.example.szymon.app.api.pojo;

import com.google.gson.annotations.SerializedName;


public class Fare {

    @SerializedName("id")
    private String id;
    @SerializedName("startingPoint")
    private Point startingPoint;
    @SerializedName("endingPoint")
    private Point endingPoint;
    @SerializedName("clientName")
    private String clientName;
    @SerializedName("clientPhone")
    private String clientPhone;
    @SerializedName("driverPhone")
    private String driverPhone;
    @SerializedName("startingDate")
    private String startingDate;
    @SerializedName("placedDate")
    private String placedDate;
    @SerializedName("status")
    private String status;

    public Fare(String id, Point startingPoint, Point endingPoint, String clientName, String clientPhone, String driverPhone, String startingDate, String placedDate, String status) {
        this.id = id;
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.driverPhone = driverPhone;
        this.startingDate = startingDate;
        this.placedDate = placedDate;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Point getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(Point startingPoint) {
        this.startingPoint = startingPoint;
    }

    public Point getEndingPoint() {
        return endingPoint;
    }

    public void setEndingPoint(Point endingPoint) {
        this.endingPoint = endingPoint;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public String getPlacedDate() {
        return placedDate;
    }

    public void setPlacedDate(String placedDate) {
        this.placedDate = placedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
