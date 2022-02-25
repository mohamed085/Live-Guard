package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "locations")
@Setter
@Getter
@NoArgsConstructor
public class Location extends BaseEntity {
    private Double lng;
    private Double lat;
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "chip_id")
    private Chip chip;
}
