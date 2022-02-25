package com.liveguard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChipTypeDTO {
    private Long id;

    @NotNull(message = "Type must not null")
    @NotEmpty(message = "Type must not empty")
    private String type;
}
