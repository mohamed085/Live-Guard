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
public class ResetPasswordRequest {

    @NotNull(message = "Email must not null")
    @NotEmpty(message = "Email must not empty")
    @Email(message = "Email not valid")
    @JsonProperty("user_email")
    private String userEmail;

    @NotNull(message = "Reset password token must not null")
    @NotEmpty(message = "Reset password token must not empty")
    @JsonProperty("reset_password_token")
    private String resetPasswordToken;

    @NotNull(message = "New password must not null")
    @NotEmpty(message = "New password must not empty")
    @JsonProperty("new_password")
    private String newPassword;


}
