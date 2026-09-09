package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.example.model.*;


public class GtfsReader {
    public List<Stop> readStops() throws IOException {
        List<Stop> stops = new ArrayList<>();

        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("gtfs/stops.txt");

        if (inputStream == null) {
            throw new IOException("stops.txt not found");
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream))) {

            String line;

            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                int stopId = Integer.parseInt(fields[0]);
                String stopName = fields[2];

                stops.add(new Stop(stopId, stopName));
            }
        }

        return stops;
    }

    public List<StopTime> readStopTimesForStop(int stopId) {
        // prebere samo relevantne vrstice
        return List.of();
    }

    public List<Trip> readTrips() {
        // ...
        return List.of();
    }

    public List<Route> readRoutes() {
        // ...
        return List.of();
    }

    public List<Calendar> readCalendars() {
        // ...
        return List.of();
    }
}
