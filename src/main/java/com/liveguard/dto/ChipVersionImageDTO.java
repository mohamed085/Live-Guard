package com.liveguard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChipVersionImageDTO {

    private Long id;
    private String photo;

    @JsonProperty("photo_file")
    private MultipartFile photoFile;

    public ChipVersionImageDTO(Long id, String photo) {
        this.id = id;
        this.photo = photo;
    }
}
