package com.liveguard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private Long id;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("address_line")
    private String addressLine;

    private String city;
    private String state;
    private String country;

    @JsonProperty("default_address")
    private Boolean defaultForShipping;

}
