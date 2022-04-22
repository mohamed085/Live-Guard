package com.liveguard.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateChipVersionShippingRequest {

    @NotNull(message = "Weight must not null")
    private Float weight;
}
