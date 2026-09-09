package org.example.model;

public class Trip {
    private int routeId;
    private int serviceId;
    private String tripId;

    public Trip(int routeId, int serviceId, String tripId) {
        this.routeId = routeId;
        this.serviceId = serviceId;
        this.tripId = tripId;
    }

    public int getRouteId() {
        return routeId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public String getTripId() {
        return tripId;
    }
}
