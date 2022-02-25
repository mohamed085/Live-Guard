package com.liveguard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDTO {
    private Long id;
    private Double lng;
    private Double lat;
    private LocalDateTime date;
    private ChipSimpleDataDTO chip;

    @JsonProperty("chip_id")
    private Long chipId;
}
