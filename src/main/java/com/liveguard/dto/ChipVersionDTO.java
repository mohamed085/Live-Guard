package com.liveguard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChipVersionDTO {

    private Long id;

    @NotNull(message = "Name must not null")
    @NotEmpty(message = "Name must not empty")
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

    @JsonProperty("created_time")
    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private Date createdTime;

    @JsonProperty("updated_time")
    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private Date updatedTime;

    private Boolean enabled;

    @JsonProperty("in_stock")
    private Boolean inStock;

    @NotNull(message = "Cost must not null")
    private Float cost;

    @NotNull(message = "Price must not null")
    private Float price;

    @JsonProperty("discount_percent")
    private Float discountPercent;

    @JsonProperty("Discount_price")
    private Float discountPrice;

    private Float weight;

    @JsonProperty("main_image")
    private String mainImage;

    @JsonProperty("main_image_file")
    private MultipartFile mainImageFile;

    @JsonProperty("review_count")
    private int reviewCount;

    @JsonProperty("average_rating")
    private float averageRating;

    @JsonProperty("customer_can_review")
    private boolean customerCanReview;

    @JsonProperty("reviewed_by_customer")
    private boolean reviewedByCustomer;

    private List<ChipVersionImageDTO> images = new ArrayList<>();
    private List<ChipVersionDetailDTO> details = new ArrayList<>();
}
