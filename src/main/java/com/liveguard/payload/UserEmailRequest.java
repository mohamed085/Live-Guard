package com.liveguard.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEmailRequest {

    @NotNull(message = "Email must not null")
    @NotEmpty(message = "Email must not empty")
    @Email(message = "Email not valid")
    @JsonProperty("user_email")
    private String userEmail;
}
