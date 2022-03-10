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
    private String address;

    @JsonProperty("date_of_birth")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @JsonProperty("create_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    private Boolean enable;
    private Boolean accountNonExpired;
    private Boolean credentialsNonExpired;
    private Boolean accountNonLocked;

    private String avatar;
    private Set<ChipDTO> chips;

    private List<String> roles;
}
