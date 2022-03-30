package com.liveguard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleChipVersion {
    private Long id;
    private String name;

    @JsonProperty("main_image")
    private String mainImage;

    @JsonProperty("average_rating")
    private float averageRating;

}
