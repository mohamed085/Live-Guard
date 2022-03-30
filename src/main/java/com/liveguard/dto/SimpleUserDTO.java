package com.liveguard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleUserDTO {

    private Long id;
    private String email;
    private String name;
    private String about;
    private String avatar;

}
