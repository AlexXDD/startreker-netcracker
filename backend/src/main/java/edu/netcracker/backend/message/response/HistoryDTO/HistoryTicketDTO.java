package edu.netcracker.backend.message.response.HistoryDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.netcracker.backend.model.history.HistoryTicket;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class HistoryTicketDTO {
    private static final String datePattern = "yyyy-MM-dd HH:mm";

    @JsonProperty("seat")
    private Integer seat;

    @JsonProperty("end_price")
    private Float endPrice;

    @JsonProperty("purchase_date")
    private String purchaseDate;

    @JsonProperty("class_name")
    private String className;

    @JsonProperty("trip")
    private HistoryTripDTO trip;

    @JsonProperty("bought_services_names")
    private List<String> boughtServicesNames;

    public static HistoryTicketDTO from(HistoryTicket historyTicket){
        HistoryTicketDTO htd = new HistoryTicketDTO();
        htd.seat = historyTicket.getSeat();
        htd.endPrice = historyTicket.getEndPrice();
        htd.purchaseDate = historyTicket.getPurchaseDate().format(DateTimeFormatter.ofPattern(datePattern));
        htd.className = historyTicket.getClassName();
        htd.trip = HistoryTripDTO.from(historyTicket.getTrip());
        htd.boughtServicesNames = historyTicket.getBoughtServicesNames();
        return htd;
    }
}
