package com.liveguard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.liveguard.domain.ChipUserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInChipDTO {
    private Long id;
    private String email;
    private String name;
    private String about;
    private String avatar;

    @JsonProperty("role")
    private ChipUserType chipUserType;
}
