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


    public Fare(Point startingPoint, Point endingPoint, String clientName, Integer clientPhone, String startingDate) {
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.startingDate = startingDate;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
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

    public Integer getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(Integer clientPhone) {
        this.clientPhone = clientPhone;
    }

    public Integer getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(Integer driverPhone) {
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
