package com.liveguard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDTO {
    private Long id;
    private int quantity;
    private float productsTotal;
    private Set<ShoppingCartItemDTO> items;
}
