package com.liveguard.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.liveguard.dto.ChipTypeDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateChipTypeDetailsRequest {

    @NotEmpty(message = "name must not empty")
    @JsonProperty("details")
    List<ChipTypeDetailDTO> chipTypeDetailDTOs;
}
