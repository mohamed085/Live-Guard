package com.liveguard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChipTypeDTO {
    private Long id;

    @NotNull(message = "Type must not null")
    @NotEmpty(message = "Type must not empty")
    private String name;

    private String alias;

    @NotNull(message = "Short description must not null")
    @NotEmpty(message = "Short description must not empty")
    @JsonProperty("short_description")
    private String shortDescription;

    @NotNull(message = "Full description must not null")
    @NotEmpty(message = "Full description must not empty")
    @JsonProperty("full_description")
    private String fullDescription;

    @JsonProperty("create_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    @JsonProperty("update_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;

    private boolean enabled;

    @JsonProperty("in_stock")
    private boolean inStock;

    @NotNull(message = "Cost must not null")
    @NotEmpty(message = "Cost must not empty")
    private float cost;

    @NotNull(message = "Price must not null")
    @NotEmpty(message = "Price must not empty")
    private float price;

    @JsonProperty("discount_percent")
    private float discountPercent;

    @NotNull(message = "Weight must not null")
    @NotEmpty(message = "Weight must not empty")
    private float weight;

    private String mainImage;

    @JsonProperty("main_image_file")
    private MultipartFile mainImageFile;

    private List<ChipTypeDetailDTO> chipTypeDetailDTOs;
}
