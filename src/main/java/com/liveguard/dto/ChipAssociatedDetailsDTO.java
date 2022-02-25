package com.liveguard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChipAssociatedDetailsDTO {

    @NotNull(message = "Name must not null")
    @NotEmpty(message = "Name must not empty")
    private String name;

    private String age;
    private String phone;
    private String photo;

    @JsonProperty("photo_file")
    private MultipartFile photoFile;

    @JsonProperty("add_by_user")
    private UserSimpleDataDTO addByUser;
}
