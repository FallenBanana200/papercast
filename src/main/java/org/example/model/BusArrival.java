package org.example.model;

public class BusArrival {
    private String routeName;
    private String arrivalTime;
    private int arrivalTimeInSeconds;

    public BusArrival(
            String routeName,
            String arrivalTime,
            int arrivalTimeInSeconds) {

        this.routeName = routeName;
        this.arrivalTime = arrivalTime;
        this.arrivalTimeInSeconds = arrivalTimeInSeconds;
    }

    public String getRouteName() {
        return routeName;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public int getArrivalTimeInSeconds() {
        return arrivalTimeInSeconds;
    }
}
