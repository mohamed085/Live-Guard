package com.liveguard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDTO {
    private Long id;

    @NotNull(message = "lng date must not null")
    @NotEmpty(message = "lng date not empty")
    private Double lng;

    @NotNull(message = "lat date must not null")
    @NotEmpty(message = "lat date not empty")
    private Double lat;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    private ChipSimpleDataDTO chip;

    @NotNull(message = "chip_id date must not null")
    @NotEmpty(message = "chip_id date not empty")
    @JsonProperty("chip_id")
    private Long chipId;
}
