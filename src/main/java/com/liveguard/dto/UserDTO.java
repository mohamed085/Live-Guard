package com.liveguard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotNull(message = "Email must not null")
    @NotEmpty(message = "Email must not empty")
    @Email(message = "Email not valid")
    private String email;

    @NotNull(message = "Name must not null")
    @NotEmpty(message = "Name must not empty")
    private String name;

    @NotNull(message = "Password must not null")
    @NotEmpty(message = "Password must not empty")
    private String password;

    private String about;
    private String phone;
    private String gender;
    private String address;

    @JsonProperty("date_of_birth")
    @JsonFormat(pattern="dd MMMM yyyy")
    private Date dateOfBirth;

    @JsonProperty("create_date")
    @JsonFormat(pattern="dd MMMM yyyy h:mm a")
    private Date createdDate;

    private Boolean enable;

    @JsonProperty("account_non_expired")
    private Boolean accountNonExpired;

    @JsonProperty("credentials_non_expired")
    private Boolean credentialsNonExpired;

    @JsonProperty("account_non_locked")
    private Boolean accountNonLocked;

    @JsonProperty("facebook_url")
    private String facebookUrl;

    @JsonProperty("twitter_url")
    private String twitterUrl;

    @JsonProperty("instagram_url")
    private String instagramUrl;

    private String avatar;

    private List<String> roles;
}
