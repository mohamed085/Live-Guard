package com.liveguard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChipVersionImageDTO {

    private Long id;
    private String name;
    private MultipartFile nameFile;

    public ChipVersionImageDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
