package com.liveguard.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.liveguard.dto.ChipVersionDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateChipVersionDetailsRequest {

    @NotEmpty(message = "name must not empty")
    @JsonProperty("details")
    List<ChipVersionDetailDTO> chipTypeDetailDTOs;
}
