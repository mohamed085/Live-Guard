package com.liveguard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSimpleDataDTO {
    private Long id;
    private String email;
    private String name;
    private String avatar;
}
