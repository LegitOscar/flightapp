package dk.cphbusiness.flightdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.cphbusiness.flightdemo.dtos.FlightDTO;
import dk.cphbusiness.flightdemo.dtos.FlightInfoDTO;
import dk.cphbusiness.flightdemo.dtos.FlightServices;
import dk.cphbusiness.utils.Utils;


import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Purpose:
 *
 * @author: Thomas Hartmann
 */
public class FlightReader {

    public static void main(String[] args) {



        try {
            List<FlightDTO> flightList = getFlightsFromFile("flights.json");
            List<FlightInfoDTO> flightInfoDTOList = getFlightInfoDetails(flightList);


            flightInfoDTOList.forEach(System.out::println);


            Duration totalRoyal = FlightServices.getTotalFlightTimeForAirline(flightInfoDTOList, "Aeroflot");
            System.out.println("Total flight time for Royal Jordanian " + totalRoyal.toHours() + "Hours");

            List<FlightInfoDTO> airportbetween = FlightServices.getFlightsBetweenAirports(flightInfoDTOList, "Queen Alia International","King Hussein International");
            System.out.println("\nFlights between Heathrow and Pulkovo " + airportbetween.size());
            airportbetween.forEach(System.out::println);


//            Duration totalRoyal = flightInfoDTOList.stream()
//                    .filter(f -> "Royal Jordanian".equalsIgnoreCase(f.getAirline()))
//                    .map(FlightInfoDTO::getDuration)
//                    .reduce(Duration.ZERO, Duration::plus);
//
//            System.out.println("Total flight time for Royal Jordanian " + totalRoyal.toHours() + "Hours");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<FlightDTO> getFlightsFromFile(String filename) throws IOException {

        ObjectMapper objectMapper = Utils.getObjectMapper();

        // Deserialize JSON from a file into FlightDTO[]
        FlightDTO[] flightsArray = objectMapper.readValue(Paths.get("flights.json").toFile(), FlightDTO[].class);

        // Convert to a list
        List<FlightDTO> flightsList = List.of(flightsArray);
        return flightsList;
    }

    public static List<FlightInfoDTO> getFlightInfoDetails(List<FlightDTO> flightList) {
        List<FlightInfoDTO> flightInfoList = flightList.stream()
           .map(flight -> {
                LocalDateTime departure = flight.getDeparture().getScheduled();
                LocalDateTime arrival = flight.getArrival().getScheduled();
                Duration duration = Duration.between(departure, arrival);
                FlightInfoDTO flightInfo =
                        FlightInfoDTO.builder()
                            .name(flight.getFlight().getNumber())
                            .iata(flight.getFlight().getIata())
                            .airline(flight.getAirline().getName())
                            .duration(duration)
                            .departure(departure)
                            .arrival(arrival)
                            .origin(flight.getDeparture().getAirport())
                            .destination(flight.getArrival().getAirport())
                            .build();

                return flightInfo;
            })
        .toList();
        return flightInfoList;
    }

}
