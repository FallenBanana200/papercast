package org.example.service;

import org.example.model.*;
import org.example.model.Calendar;

import java.time.LocalTime;
import java.util.*;

public class BusService {

    private List<Stop> stops;
    private List<StopTime> stopTimes;
    private List<Trip> trips;
    private List<Route> routes;
    private List<Calendar> calendars;

    public BusService(
            List<Stop> stops,
            List<StopTime> stopTimes,
            List<Trip> trips,
            List<Route> routes,
            List<Calendar> calendars) {

        this.stops = stops;
        this.stopTimes = stopTimes;
        this.trips = trips;
        this.routes = routes;
        this.calendars = calendars;
    }

    public Stop findStopById(int stationId) {
        for (Stop stop : stops) {
            if (stop.getStopId() == stationId) {
                return stop; }
        }

        return null;
    }

    public Trip findTripById(String tripId) {
        for (Trip trip: trips) {
            if (trip.getTripId().equals(tripId)) {
                return trip;
            }
        }

        return null;
    }

    public Route findRouteById(int routeId) {
        for(Route route: routes){
            if (route.getRouteId() == routeId) {
                return route;
            }
        }

        return null;
    }

    public Route findRouteForStopTime(StopTime stopTime) {
        Trip trip = findTripById(stopTime.getTripId());

        if (trip == null) {
            return null;
        }

        return findRouteById(trip.getRouteId());
    }

    private int parseGtfsTime(String arrivalTime) {
        String[] parts = arrivalTime.split(":");

        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int seconds = Integer.parseInt(parts[2]);

        return hours * 3600 + minutes * 60 + seconds;
    }

    private int getCurrentTimeInSeconds() {
        LocalTime now = LocalTime.now();

        int hours = now.getHour();
        int minutes = now.getMinute();
        int seconds = now.getSecond();

        return hours * 3600 + minutes * 60 + seconds;
    }

    private boolean isWithinTwoHours(String arrivalTime) {
        int now = getCurrentTimeInSeconds();
        int arrivalTimeInSecs = parseGtfsTime(arrivalTime);

        int dayInSeconds = 24 * 60 * 60;
        int twoHoursInSeconds = 2 * 60 * 60;

        if (arrivalTimeInSecs < now) {
            arrivalTimeInSecs += dayInSeconds;
        }

        int difference = arrivalTimeInSecs - now;

        return difference >= 0 && difference <= twoHoursInSeconds;
    }

    public List<StopTime> findUpcomingStopTimes(int stationId) {
        List<StopTime> upcomingStopTimes = new ArrayList<>();

        for (StopTime stopTime : stopTimes) {
            if (stopTime.getStopId() == stationId
                    && isWithinTwoHours(stopTime.getArrivalTime())) {
                upcomingStopTimes.add(stopTime);
            }
        }

        return upcomingStopTimes;
    }

    public List<BusArrival> findUpcomingArrivals(int stationId) {
        List<BusArrival> arrivals = new ArrayList<>();

        List<StopTime> upcomingStopTimes = findUpcomingStopTimes(stationId);

        for (StopTime stopTime : upcomingStopTimes) {

            Route route = findRouteForStopTime(stopTime);

            if (route == null) {
                continue;
            }

            int arrivalTimeInSeconds =
                    parseGtfsTime(stopTime.getArrivalTime());

            BusArrival arrival = new BusArrival(
                    route.getRouteShortName(),
                    stopTime.getArrivalTime(),
                    arrivalTimeInSeconds
            );

            arrivals.add(arrival);
        }

        return arrivals;
    }

    public Map<String, List<BusArrival>> findUpcomingArrivalsByRoute(
            int stationId,
            int numBusesPerLine) {

        Map<String, List<BusArrival>> arrivalsByRoute = new HashMap<>();

        List<BusArrival> arrivals = findUpcomingArrivals(stationId);

        arrivals.sort(
                Comparator.comparingInt(BusArrival::getArrivalTimeInSeconds)
        );

        for (BusArrival arrival : arrivals) {

            String routeName = arrival.getRouteName();

            if (!arrivalsByRoute.containsKey(routeName)) {
                arrivalsByRoute.put(routeName, new ArrayList<>());
            }

            List<BusArrival> routeArrivals = arrivalsByRoute.get(routeName);

            if (routeArrivals.size() < numBusesPerLine) {
                routeArrivals.add(arrival);
            }
        }

        return arrivalsByRoute;
    }

    public String formatArrivalTime(BusArrival arrival, String timeFormat) {

        if (timeFormat.equals("absolute")) {
            return arrival.getArrivalTime();
        }

        if (timeFormat.equals("relative")) {
            int now = getCurrentTimeInSeconds();

            int difference =
                    arrival.getArrivalTimeInSeconds() - now;

            if (difference < 0) {
                difference += 24 * 60 * 60;
            }

            int minutes = difference / 60;

            return "in " + minutes + " min";
        }

        return arrival.getArrivalTime();
    }

}
