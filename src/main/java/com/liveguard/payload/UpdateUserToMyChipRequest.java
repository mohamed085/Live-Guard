package com.liveguard.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserToMyChipRequest {

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("chip_id")
    private Long chipId;
}
