package edu.netcracker.backend.model;

import edu.netcracker.backend.dao.annotations.Attribute;
import edu.netcracker.backend.dao.annotations.PrimaryKey;
import edu.netcracker.backend.dao.annotations.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table("bundle")
public class Bundle {

    @PrimaryKey("bundle_id")
    @EqualsAndHashCode.Include
    private Integer bundleId;

    @Attribute("start_date")
    private LocalDate startDate;

    @Attribute("finish_date")
    private LocalDate finishDate;

    @Attribute("bundle_price")
    private Integer bundlePrice;

    @Attribute("start_date")
    private String bundleDescription;

    @Attribute("start_date")
    private String bundlePhotoUri;

    private List<TicketClass> bundleTickets;

    private List<Service> bundleServices;

}
