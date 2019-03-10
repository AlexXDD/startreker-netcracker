package edu.netcracker.backend.message.response.trips;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.netcracker.backend.model.TicketClass;
import edu.netcracker.backend.model.Trip;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class AllCarriersTripsDTO {
    @JsonProperty("trip_id")
    private Long tripId;

    @JsonProperty("departure_spaceport_name")
    private String departureSpaceportName;

    @JsonProperty("arrival_spaceport_name")
    private String arrivalSpaceportName;

    @JsonProperty("departure_planet")
    private String departurePlanet;

    @JsonProperty("arrival_planet")
    private String arrivalPlanet;

    @JsonProperty("departure_date")
    private LocalDateTime departureDate;

    @JsonProperty("arrival_date")
    private LocalDateTime arrivalDate;

    @JsonProperty("ticket_classes")
    private List<Map<String, String>> ticketClasses;

    @JsonProperty("carrier_id")
    private Long carrierId;

    @JsonProperty("carrier_name")
    private String carrierName;

    public static AllCarriersTripsDTO from(Trip trip){
        AllCarriersTripsDTO dto = new AllCarriersTripsDTO();
        dto.tripId = trip.getTripId();
        dto.departureSpaceportName = trip.getDepartureSpaceport().getSpaceportName();
        dto.arrivalSpaceportName = trip.getArrivalSpaceport().getSpaceportName();
        dto.departurePlanet = trip.getDeparturePlanet().getPlanetName();
        dto.arrivalPlanet = trip.getArrivalPlanet().getPlanetName();
        dto.carrierId = trip.getOwner().getUserId();
        dto.carrierName = trip.getOwner().getUserName();
        dto.departureDate = trip.getDepartureDate();
        dto.arrivalDate = trip.getArrivalDate();
        for (TicketClass ticketClass: trip.getTicketClasses()) {
            Map<String, String> tcProperties = new HashMap<>();
            tcProperties.put("class_id", ticketClass.getClassId());
            tcProperties.put("class_name", ticketClass.getClassName());
            tcProperties.put("ticket_price", ticketClass.getTicketPrice());
            dto.ticketClasses.add(tcProperties);
        }
        return dto;
    }
}
