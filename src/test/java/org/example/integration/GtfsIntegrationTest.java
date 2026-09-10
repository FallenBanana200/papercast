package org.example.integration;

import org.example.model.Route;
import org.example.model.Stop;
import org.example.model.StopTime;
import org.example.model.Trip;
import org.example.reader.GtfsReader;
import org.example.service.BusService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GtfsIntegrationTest {

    @Test
    void shouldReadGtfsDataAndFindUpcomingArrivals() throws IOException {

        GtfsReader reader = new GtfsReader();

        List<Stop> stops = reader.readStops();
        List<Trip> trips = reader.readTrips();
        List<Route> routes = reader.readRoutes();
        List<org.example.model.Calendar> calendars = reader.readCalendars();

        List<StopTime> stopTimes =
                reader.readStopTimesForStop(2);

        Clock fixedClock = Clock.fixed(
                Instant.parse("2026-09-10T22:00:00Z"),
                ZoneId.of("UTC")
        );

        BusService busService = new BusService(
                stops,
                stopTimes,
                trips,
                routes,
                calendars,
                fixedClock
        );

        Stop stop = busService.findStopById(2);

        assertNotNull(stop);
        assertEquals(
                "AL Masjid Al-nabawi (Clock Roundabout)",
                stop.getStopName()
        );

        assertFalse(stopTimes.isEmpty());

        Map<String, List<org.example.model.BusArrival>> arrivals =
                busService.findUpcomingArrivalsByRoute(2, 5);

        assertFalse(arrivals.isEmpty());
        assertTrue(arrivals.containsKey("101"));
        assertFalse(arrivals.get("101").isEmpty());
    }
}