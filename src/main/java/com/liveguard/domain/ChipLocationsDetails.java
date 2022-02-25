package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "chip_location_details")
@Setter
@Getter
@NoArgsConstructor
public class ChipLocationsDetails extends BaseEntity {
    private Double lng;
    private Double lat;
    private Date date;
    private ChipLocationsDetailsStatus status;

    @ManyToOne
    @JoinColumn(name = "chip_id")
    private Chip chip;
}
