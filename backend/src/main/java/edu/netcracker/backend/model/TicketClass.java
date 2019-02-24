package edu.netcracker.backend.model;

import edu.netcracker.backend.dao.annotations.Attribute;
import edu.netcracker.backend.dao.annotations.PrimaryKey;
import edu.netcracker.backend.dao.annotations.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table("ticket_class")
public class TicketClass {

    @PrimaryKey("class_id")
    @EqualsAndHashCode.Include
    private Integer classId;

    @Attribute("trip_id")
    private Integer tripId;

    @Attribute("ticket_price")
    private Integer ticketPrice;

}
