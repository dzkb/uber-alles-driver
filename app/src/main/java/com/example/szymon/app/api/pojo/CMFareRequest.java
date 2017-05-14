package com.example.szymon.app.api.pojo;

/**
 * Created by dzaku_000 on 2017-05-15.
 */

public class CMFareRequest {
    public CMFareRequest(String fareID, String clientPhone, Point startingPoint, Point endingPoint, String startingDate) {
        this.fareID = fareID;
        this.clientPhone = clientPhone;
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.startingDate = startingDate;
    }

    public String getFareID() {
        return fareID;
    }

    public void setFareID(String fareID) {
        this.fareID = fareID;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
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

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    String fareID;
    String clientPhone;
    Point startingPoint;
    Point endingPoint;
    String startingDate;
}
