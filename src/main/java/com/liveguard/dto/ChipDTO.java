package com.liveguard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChipDTO {

    private Long id;

    private String key;
    private String password;
    private Boolean used;

    @NotNull(message = "Chip version id must not null")
    @JsonProperty("chip_version_id")
    private Long chipVersionId;

    @JsonProperty("chip_version")
    private SimpleChipVersion chipVersion;



}

