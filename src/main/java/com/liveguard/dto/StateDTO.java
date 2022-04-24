package com.liveguard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.liveguard.domain.Country;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StateDTO {
    private Long id;
    private String name;

    @JsonProperty("country_id")
    private Long countryId;

    private Country country;
}
