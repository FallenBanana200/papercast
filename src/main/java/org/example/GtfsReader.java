package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        return List.of();
    }

    public List<Trip> readTrips() throws IOException {
        List<Trip> trips = new ArrayList<>();

        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("gtfs/trips.txt");

        if (inputStream == null) {
            throw new IOException("trips.txt not found");
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream))) {

            String line;

            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                int routeId = Integer.parseInt(fields[0]);
                int serviceId = Integer.parseInt(fields[1]);
                String tripId = fields[2];

                trips.add(new Trip(routeId, serviceId, tripId));
            }
        }

        return trips;
    }

    public List<Route> readRoutes() throws IOException {
        List<Route> routes = new ArrayList<>();

        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("gtfs/routes.txt");

        if (inputStream == null) {
            throw new IOException("routes.txt not found");
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream))) {

            String line;

            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                int routeId = Integer.parseInt(fields[0]);
                String routeShortName = fields[2];

                routes.add(new Route(routeId, routeShortName));
            }
        }

        return routes;
    }

    public List<Calendar> readCalendars() throws IOException {
        List<Calendar> calendars = new ArrayList<>();

        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("gtfs/calendar.txt");

        if (inputStream == null) {
            throw new IOException("calendar.txt not found");
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream))) {

            String line;

            reader.readLine();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                int serviceId = Integer.parseInt(fields[0]);

                boolean monday = fields[1].equals("1");
                boolean tuesday = fields[2].equals("1");
                boolean wednesday = fields[3].equals("1");
                boolean thursday = fields[4].equals("1");
                boolean friday = fields[5].equals("1");
                boolean saturday = fields[6].equals("1");
                boolean sunday = fields[7].equals("1");

                LocalDate startDate = LocalDate.parse(fields[8], formatter);
                LocalDate endDate = LocalDate.parse(fields[9], formatter);

                calendars.add(new Calendar(
                        serviceId,
                        monday,
                        tuesday,
                        wednesday,
                        thursday,
                        friday,
                        saturday,
                        sunday,
                        startDate,
                        endDate
                ));
            }
        }

        return calendars;
    }
}
