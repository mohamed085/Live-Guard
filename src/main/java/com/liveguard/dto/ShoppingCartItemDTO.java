package com.liveguard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartItemDTO {
    private Long id;
    private int quantity;
    private float total;

    @JsonProperty("chip_version")
    private SimpleChipVersion chipVersion;
}
