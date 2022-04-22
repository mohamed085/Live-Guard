package com.liveguard.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateChipVersionOverviewRequest {

    @NotNull(message = "name must not null")
    @NotEmpty(message = "name must not empty")
    private String name;

    @NotNull(message = "alias must not null")
    @NotEmpty(message = "alias must not empty")
    private String alias;

    @NotNull(message = "cost must not null")
    private Float cost;

    @NotNull(message = "price must not null")
    private Float price;

    @NotNull(message = "discount_percent must not null")
    @JsonProperty("discount_percent")
    private Float discountPercent;

    @JsonProperty("customer_can_review")
    private boolean customerCanReview;

    @JsonProperty("reviewed_by_customer")
    private boolean reviewedByCustomer;

    @JsonProperty("in_stock")
    private Boolean inStock;

    private Boolean enabled;

}

/**
 * {
 *      "name": "Version 1",
 *     "alias": "Version 1",
 *     "cost": 280.0,
 *     "price": 300.0,
 *     "discount_percent": 0.0,
 *     "discount_price": 300.0,
 *     "customer_can_review": true,
 *     "reviewed_by_customer": true,
 *     "in_stock": true,
 *     "enabled": true
 * }
 */