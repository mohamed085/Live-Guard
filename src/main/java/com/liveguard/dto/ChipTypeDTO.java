package com.liveguard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.liveguard.domain.ChipTypeImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
    @JsonFormat(pattern="dd/MM/yyyy hh:mm aa")
    private LocalDateTime createdTime;

    @JsonProperty("update_date")
    @JsonFormat(pattern="dd/MM/yyyy hh:mm aa")
    private LocalDateTime updatedTime;

    private Boolean enabled;

    @JsonProperty("in_stock")
    private Boolean inStock;

    @NotNull(message = "Cost must not null")
    private Float cost;

    @NotNull(message = "Price must not null")
    private Float price;

    @JsonProperty("discount_percent")
    private Float discountPercent;

    @NotNull(message = "Weight must not null")
    private Float weight;

    private String mainImage;

    @JsonProperty("main_image_file")
    private MultipartFile mainImageFile;

    @JsonProperty("chip_type_detail")
    private List<ChipTypeDetailDTO> chipTypeDetails;

    private List<ChipTypeImageDTO> images;

}
