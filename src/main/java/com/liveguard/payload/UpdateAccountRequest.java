package com.liveguard.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccountRequest {

    @NotNull(message = "Name must not null")
    @NotEmpty(message = "Name must not empty")
    private String name;

    private String about;
    private String phone;
    private String address;

    @JsonProperty("facebook_url")
    private String facebookUrl;

    @JsonProperty("twitter_url")
    private String twitterUrl;

    @JsonProperty("instagram_url")
    private String instagramUrl;
}
