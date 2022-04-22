package com.liveguard.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotNull(message = "Email must not null")
    @NotEmpty(message = "Email must not empty")
    @Email(message = "Email not valid")
    private String email;

    @NotNull(message = "Password must not null")
    @NotEmpty(message = "Password must not empty")
    private String password;
}

