package org.example.model;

public class StopTime {
    private String tripId;
    private String arrivalTime;
    private int stopId;
    private int stopSequence;

    public StopTime(String tripId, String arrivalTime, int stopId, int stopSequence) {
        this.tripId = tripId;
        this.arrivalTime = arrivalTime;
        this.stopId = stopId;
        this.stopSequence = stopSequence;
    }

    public String getTripId() {
        return tripId;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public int getStopId() {
        return stopId;
    }

    public int getStopSequence() {
        return stopSequence;
    }
}