package com.liveguard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

    private Long id;
    private String headline;
    private String comment;
    private Double rating;

    @JsonProperty("review_time")
    @JsonFormat(pattern="dd MMMM yyyy")
    private LocalDateTime reviewTime;

    @JsonProperty("chip_version_id")
    private Long chipVersionId;

    private SimpleUserDTO user;

    @JsonProperty("chip_version")
    private SimpleChipVersion chipVersion;
}
