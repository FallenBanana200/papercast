package org.example.model;

public class Route {
    private int routeId;
    private String routeShortName;

    public Route(int routeId, String routeShortName) {
        this.routeId = routeId;
        this.routeShortName = routeShortName;
    }

    public int getRouteId() {
        return routeId;
    }

    public String getRouteShortName() {
        return routeShortName;
    }
}
