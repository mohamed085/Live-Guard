package com.liveguard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChipTypeImageDTO {

    private Long id;
    private String name;
    private MultipartFile nameFile;

    public ChipTypeImageDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
