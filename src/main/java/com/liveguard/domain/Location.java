package com.liveguard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    @Enumerated(EnumType.STRING)
    private LocationStatus status;

    @ManyToOne
    @JoinColumn(name = "chip_id")
    private Chip chip;

    @Override
    public String toString() {
        return "Location{" +
                "id=" + super.getId() +
                ", lng=" + lng +
                ", lat=" + lat +
                ", date=" + date +
                ", status=" + status +
                ", chip=" + chip.getId() +
                '}';
    }
}
