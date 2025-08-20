package dk.cphbusiness.flightdemo.dtos;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class FlightServices {

    public static Duration getTotalFlightTimeForAirline(List<FlightInfoDTO> flights, String airline) {

        return Duration.ofSeconds(
                flights.stream()
                        .filter(f -> f.getAirline() != null && f.getAirline().equalsIgnoreCase(airline))
                        .mapToLong(f -> f.getDuration().getSeconds())
                        .sum()
        );
    }


    public static List<FlightInfoDTO> getFlightsBetweenAirports(List<FlightInfoDTO> flights,
                                                                String origin, String destination) {
        return flights.stream()
                .filter(f -> f.getOrigin() != null && f.getDestination() != null)
                .filter(f -> f.getOrigin().equalsIgnoreCase(origin)
                        && f.getDestination().equalsIgnoreCase(destination) ||f.getOrigin().equalsIgnoreCase(destination)
                        && f.getDestination().equalsIgnoreCase(origin) )
                .distinct()
                .toList();
    }

    }


