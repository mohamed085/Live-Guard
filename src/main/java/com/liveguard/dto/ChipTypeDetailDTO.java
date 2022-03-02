package com.liveguard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChipTypeDetailDTO {

    private Long id;

    @NotNull(message = "Name must not null")
    @NotEmpty(message = "Name must not empty")
    private String name;

    @NotNull(message = "Value must not null")
    @NotEmpty(message = "Value must not empty")
    private String value;


    private ChipTypeSimpleDateDTO chipType;

    @NotNull(message = "Chip type id must not null")
    @NotEmpty(message = "Chip type id must not empty")
    private Long chipTypeId;


}
