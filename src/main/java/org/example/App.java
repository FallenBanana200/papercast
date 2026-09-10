package org.example;

import org.example.model.*;
import org.example.reader.GtfsReader;
import org.example.service.BusService;
import java.time.Clock;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {

    public void run() throws IOException {

        Scanner scanner = new Scanner(System.in);
        GtfsReader reader = new GtfsReader();

        List<Stop> stops = reader.readStops();
        List<Trip> trips = reader.readTrips();
        List<Route> routes = reader.readRoutes();
        List<Calendar> calendars = reader.readCalendars();

        while (true) {

            int stationId = readStationId(scanner, stops);
            int numBusesPerLine = readNumberOfBuses(scanner);
            String timeFormat = readTimeFormat(scanner);

            List<StopTime> stopTimes =
                    reader.readStopTimesForStop(stationId);

            BusService busService = new BusService(
                    stops,
                    stopTimes,
                    trips,
                    routes,
                    calendars,
                    Clock.systemDefaultZone()
            );

            Stop stop = busService.findStopById(stationId);

            System.out.println("Stop: " + stop.getStopName());
            System.out.println();

            Map<String, List<BusArrival>> arrivals =
                    busService.findUpcomingArrivalsByRoute(
                            stationId,
                            numBusesPerLine
                    );

            for (Map.Entry<String, List<BusArrival>> entry
                    : arrivals.entrySet()) {

                String routeName = entry.getKey();
                List<BusArrival> routeArrivals = entry.getValue();

                System.out.println("Route " + routeName + ":");

                for (BusArrival arrival : routeArrivals) {
                    System.out.println(
                            "  " + busService.formatArrivalTime(
                                    arrival,
                                    timeFormat
                            )
                    );
                }

                System.out.println();
            }

            while (true) {
                System.out.println("[N] New search");
                System.out.println("[Q] Quit");
                System.out.print("> ");

                String choice =
                        scanner.nextLine().trim().toLowerCase();

                if (choice.equals("q")) {
                    return;
                }

                if (choice.equals("n")) {
                    break;
                }

                System.out.println(
                        "Invalid input. Please enter N for new search or Q to quit."
                );
            }
        }
    }
    private static int readStationId(Scanner scanner, List<Stop> stops) {

        while (true) {
            System.out.print("Enter station ID: ");

            String input = scanner.nextLine();

            try {
                int stationId = Integer.parseInt(input);
                for (Stop stop : stops) {
                    if (stop.getStopId() == stationId) {
                        return stationId;
                    }
                }
                System.out.println( "Station with ID " + stationId + " was not found." );
            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid input. Station ID must be a whole number."
                );
            }
        }
    }

    private static int readNumberOfBuses(Scanner scanner) {

        while (true) {
            System.out.print("Enter number of upcoming buses per line: ");

            String input = scanner.nextLine();

            try {
                int numberOfBuses = Integer.parseInt(input);

                if (numberOfBuses <= 0) {
                    System.out.println(
                            "Invalid input. Number of buses must be greater than 0."
                    );
                    continue;
                }

                return numberOfBuses;

            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid input. Number of buses must be a whole number."
                );
            }
        }
    }

    private static String readTimeFormat(Scanner scanner) {

        while (true) {
            System.out.print("Choose time format [A] Absolute / [R] Relative: ");

            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("a")) {
                return "absolute";
            }

            if (input.equals("r")) {
                return "relative";
            }

            System.out.println(
                    "Invalid input. Please enter A for absolute or R for relative."
            );
        }
    }
}