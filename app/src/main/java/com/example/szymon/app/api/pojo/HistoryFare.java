package com.example.szymon.app.api.pojo;

public class HistoryFare {

    String fareId;
    String startingDate;
    String startingPoint;
    String endingPoint;

    public HistoryFare(String fareId, String startingDate, String startingPoint, String endingPoint) {
        this.fareId = fareId;
        this.startingDate = startingDate;
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
    }

    public String getFareId() {
        return fareId;
    }

    public void setFareId(String fareId) {
        this.fareId = fareId;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getEndingPoint() {
        return endingPoint;
    }

    public void setEndingPoint(String endingPoint) {
        this.endingPoint = endingPoint;
    }
}
