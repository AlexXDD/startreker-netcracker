package edu.netcracker.backend.message.response.trips;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.netcracker.backend.model.Discount;
import edu.netcracker.backend.model.TicketClass;
import edu.netcracker.backend.model.Trip;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class ReadTripsDTO {

    @JsonProperty("trip_id")
    private Long tripId;

    @JsonProperty("trip_status")
    private String tripStatus;

    @JsonProperty("trip_status_id")
    private Integer tripStatusId;

    @JsonProperty("departure_spaceport_name")
    private String departureSpaceportName;

    @JsonProperty("arrival_spaceport_name")
    private String arrivalSpaceportName;

    @JsonProperty("departure_planet_name")
    private String departurePlanet;

    @JsonProperty("arrival_planet_name")
    private String arrivalPlanet;

    @JsonProperty("departure_date")
    private String departureDate;

    @JsonProperty("arrival_date")
    private String arrivalDate;

    @JsonProperty("creation_date")
    private String creationDate;

    @JsonProperty("ticket_classes")
    private List<Map<String, Object>> ticketClasses;

    @JsonProperty("trip_reply")
    private String reply;

    public static ReadTripsDTO from(Trip trip) {
        ReadTripsDTO dto = new ReadTripsDTO();
        dto.tripId = trip.getTripId();
        dto.tripStatus = trip.getTripState()
                             .getName();
        dto.tripStatusId = trip.getTripState()
                               .getDatabaseValue();
        dto.departureSpaceportName = capitalize(trip.getDepartureSpaceport()
                                                    .getSpaceportName());
        dto.arrivalSpaceportName = capitalize(trip.getArrivalSpaceport()
                                                  .getSpaceportName());
        dto.departurePlanet = capitalize(trip.getDepartureSpaceport()
                                             .getPlanet()
                                             .getPlanetName());
        dto.arrivalPlanet = capitalize(trip.getArrivalSpaceport()
                                           .getPlanet()
                                           .getPlanetName());
        dto.departureDate = getStringFromDate(trip.getDepartureDate());
        dto.arrivalDate = getStringFromDate(trip.getArrivalDate());
        dto.creationDate = getStringFromDate(trip.getCreationDate());
        dto.reply = "";
        if (trip.getReplies() != null && !trip.getReplies()
                                              .isEmpty()) {
            dto.reply = trip.getReplies()
                            .get(trip.getReplies()
                                     .size() - 1)
                            .getReportText();
        }
        dto.ticketClasses = new ArrayList<>();

        Discount discount;

        for (TicketClass ticketClass : trip.getTicketClasses()) {
            Map<String, Object> tcProperties = new HashMap<>();

            tcProperties.put("class_id", ticketClass.getClassId());
            tcProperties.put("class_name", ticketClass.getClassName());
            tcProperties.put("ticket_price", ticketClass.getTicketPrice());
            tcProperties.put("class_seats", ticketClass.getClassSeats());
            tcProperties.put("remaining_seats", ticketClass.getRemainingSeats());

            discount = ticketClass.getDiscount();

            if (discount != null) {
                tcProperties.put("discount_rate", discount.getDiscountRate());
                tcProperties.put("is_percent", discount.getIsPercent());
            }

            dto.ticketClasses.add(tcProperties);
        }

        return dto;
    }

    private static String getStringFromDate(LocalDateTime date) {
        return date.getYear()
               + "-"
               + String.format("%02d", date.getMonthValue())
               + "-"
               + String.format("%02d",
                               date.getDayOfMonth())
               + " "
               + String.format("%02d", date.getHour())
               + ":"
               + String.format("%02d", date.getMinute())
               + ":"
               + String.format("%02d", date.getSecond());
    }

    private static String capitalize(String toCapitalize) {
        String capitalized = toCapitalize.substring(0, 1)
                                         .toUpperCase() + toCapitalize.substring(1)
                                                                      .toLowerCase();

        return capitalized;
    }
}
