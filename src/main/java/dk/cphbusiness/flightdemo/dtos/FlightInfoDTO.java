package dk.cphbusiness.flightdemo.dtos;

import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class FlightInfoDTO {
    private String name;
    private String iata;
    private String airline;
    private Duration duration;
    private LocalDateTime departure;
    private LocalDateTime arrival;
    private String origin;
    private String destination;


}
