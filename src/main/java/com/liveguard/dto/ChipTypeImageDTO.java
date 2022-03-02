package com.liveguard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChipTypeImageDTO {
    private Long id;
    private String name;
    private ChipTypeSimpleDateDTO chipType;

    @NotNull(message = "Chip type id must not null")
    @NotEmpty(message = "Chip type id must not empty")
    private Long chipTypeId;
}
