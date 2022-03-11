package com.liveguard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorDTO {

    private Long id;
    private String name;
    private String avatar;
    private String address;
    private String phone;

    @JsonProperty("facebook_url")
    private String facebookUrl;

    @JsonProperty("twitter_url")
    private String twitterUrl;

    @JsonProperty("instagram_url")
    private String instagramUrl;

}
