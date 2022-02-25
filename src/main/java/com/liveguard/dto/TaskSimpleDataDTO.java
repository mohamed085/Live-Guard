package com.liveguard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskSimpleDataDTO {
    private Long id;
    private String name;
    private Boolean mute;

    @JsonProperty("add_by_user")
    private UserSimpleDataDTO addByUser;
}
