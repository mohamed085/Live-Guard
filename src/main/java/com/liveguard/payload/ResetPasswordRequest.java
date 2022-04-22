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
public class ResetPasswordRequest {

    @NotNull(message = "Reset password token must not null")
    @NotEmpty(message = "Reset password token must not empty")
    @JsonProperty("reset_password_token")
    private String resetPasswordToken;

    @NotNull(message = "Password must not null")
    @NotEmpty(message = "Password must not empty")
    private String password;
}
