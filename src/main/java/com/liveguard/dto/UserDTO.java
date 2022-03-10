package com.liveguard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.liveguard.domain.Chip;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    private String phone;
    private String gender;
    private String address;

    @JsonProperty("date_of_birth")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @JsonProperty("create_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

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
    private Set<ChipDTO> chips;

    private List<String> roles;
}
