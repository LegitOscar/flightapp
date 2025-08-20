package dk.cphbusiness.flightdemo.dtos;

import java.time.Duration;
import java.util.List;

public class FlightServices {

    public static Duration getTotalFlightTimeForAirline(List<FlightInfoDTO> flights, String airline){

        return Duration.ofSeconds(
                flights.stream()
                        .filter(f -> f.getAirline() != null && f.getAirline().equalsIgnoreCase(airline))
                        .mapToLong(f -> f.getDuration().getSeconds())
                        .sum()
        );
    }
}
