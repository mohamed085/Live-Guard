package com.liveguard.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateChipVersionDescriptionRequest {

    @NotNull(message = "Short description must not null")
    @NotEmpty(message = "Short description must not empty")
    @JsonProperty("short_description")
    private String shortDescription;

    @NotNull(message = "Full description must not null")
    @NotEmpty(message = "Full description must not empty")
    @JsonProperty("full_description")
    private String fullDescription;

}
