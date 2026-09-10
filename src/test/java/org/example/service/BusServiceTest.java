package org.example.service;

import org.example.model.*;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BusServiceTest {

    @Test
    void shouldFindStopById() {
        Stop stop = new Stop(123, "Test Stop");

        BusService busService = new BusService(
                List.of(stop),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                Clock.systemDefaultZone()
        );

        Stop result = busService.findStopById(123);

        assertNotNull(result);
        assertEquals(123, result.getStopId());
        assertEquals("Test Stop", result.getStopName());
    }

    @Test
    void shouldReturnNullWhenStopDoesNotExist() {

        Stop stop = new Stop(123, "Test Stop");

        BusService busService = new BusService(
                List.of(stop),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                Clock.systemDefaultZone()
        );

        Stop result = busService.findStopById(999);

        assertNull(result);
    }

    @Test
    void shouldFindOnlyArrivalsWithinNextTwoHours() {

        StopTime before = new StopTime(
                "trip1",
                "09:50:00",
                123,
                1
        );

        StopTime withinOneHour = new StopTime(
                "trip2",
                "10:30:00",
                123,
                1
        );

        StopTime exactlyTwoHours = new StopTime(
                "trip3",
                "12:00:00",
                123,
                1
        );

        StopTime afterTwoHours = new StopTime(
                "trip4",
                "12:01:00",
                123,
                1
        );

        Clock fixedClock = Clock.fixed(
                Instant.parse("2026-09-10T10:00:00Z"),
                ZoneId.of("UTC")
        );

        BusService busService = new BusService(
                List.of(),
                List.of(before, withinOneHour, exactlyTwoHours, afterTwoHours),
                List.of(),
                List.of(),
                List.of(),
                fixedClock
        );

        List<StopTime> result =
                busService.findUpcomingStopTimes(123);

        assertEquals(2, result.size());

        assertEquals("10:30:00", result.get(0).getArrivalTime());
        assertEquals("12:00:00", result.get(1).getArrivalTime());
    }

    @Test
    void shouldLimitNumberOfArrivalsPerRoute() {

        StopTime route101First = new StopTime(
                "trip101a",
                "10:10:00",
                123,
                1
        );

        StopTime route101Second = new StopTime(
                "trip101b",
                "10:20:00",
                123,
                1
        );

        StopTime route101Third = new StopTime(
                "trip101c",
                "10:30:00",
                123,
                1
        );

        StopTime route105First = new StopTime(
                "trip105a",
                "10:15:00",
                123,
                1
        );

        StopTime route105Second = new StopTime(
                "trip105b",
                "10:25:00",
                123,
                1
        );

        Trip trip101a = new Trip(101, 1, "trip101a");
        Trip trip101b = new Trip(101, 1, "trip101b");
        Trip trip101c = new Trip(101, 1, "trip101c");

        Trip trip105a = new Trip(105, 1, "trip105a");
        Trip trip105b = new Trip(105, 1, "trip105b");

        Route route101 = new Route(101, "101");
        Route route105 = new Route(105, "105");

        Clock fixedClock = Clock.fixed(
                Instant.parse("2026-09-10T10:00:00Z"),
                ZoneId.of("UTC")
        );

        BusService busService = new BusService(
                List.of(),
                List.of(
                        route101First,
                        route101Second,
                        route101Third,
                        route105First,
                        route105Second
                ),
                List.of(
                        trip101a,
                        trip101b,
                        trip101c,
                        trip105a,
                        trip105b
                ),
                List.of(route101, route105),
                List.of(),
                fixedClock
        );

        Map<String, List<BusArrival>> result =
                busService.findUpcomingArrivalsByRoute(123, 2);

        assertEquals(2, result.get("101").size());
        assertEquals(2, result.get("105").size());

        assertEquals("10:10:00", result.get("101").get(0).getArrivalTime());
        assertEquals("10:20:00", result.get("101").get(1).getArrivalTime());
    }
}
