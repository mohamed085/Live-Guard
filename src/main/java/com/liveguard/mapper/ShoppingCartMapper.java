package com.liveguard.mapper;

import com.liveguard.domain.ChipVersion;
import com.liveguard.domain.ShoppingCart;
import com.liveguard.domain.ShoppingCartItem;
import com.liveguard.dto.ShoppingCartDTO;
import com.liveguard.dto.ShoppingCartItemDTO;
import com.liveguard.dto.SimpleChipVersion;

import java.util.HashSet;
import java.util.Set;

public class ShoppingCartMapper {

    public static ShoppingCartDTO shoppingCartTOShoppingCartDTO(ShoppingCart shoppingCart) {
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        shoppingCartDTO.setId(shoppingCart.getId());
        shoppingCartDTO.setQuantity(shoppingCart.getQuantity());
        shoppingCartDTO.setProductsTotal(shoppingCart.getProductsTotal());
        shoppingCartDTO.setItems(shoppingCartItemSetToShoppingCartItemDTOSet(shoppingCart.getShoppingCartItems()));

        return shoppingCartDTO;
    }

    public static Set<ShoppingCartItemDTO> shoppingCartItemSetToShoppingCartItemDTOSet(Set<ShoppingCartItem> shoppingCartItems) {
        Set<ShoppingCartItemDTO> items = new HashSet<>();
        shoppingCartItems.forEach(shoppingCartItem -> {
            items.add(shoppingCartItemToShoppingCartItemDTO(shoppingCartItem));
        });

        return items;
    }

    private static ShoppingCartItemDTO shoppingCartItemToShoppingCartItemDTO(ShoppingCartItem shoppingCartItem) {
        ShoppingCartItemDTO shoppingCartItemDTO = new ShoppingCartItemDTO();
        shoppingCartItemDTO.setId(shoppingCartItem.getId());
        shoppingCartItemDTO.setQuantity(shoppingCartItem.getQuantity());
        shoppingCartItemDTO.setTotal(shoppingCartItem.getTotal());
        shoppingCartItemDTO.setChipVersion(chipVersionTOSimpleChipVersion(shoppingCartItem.getChipVersion()));

        return shoppingCartItemDTO;
    }

    private static SimpleChipVersion chipVersionTOSimpleChipVersion(ChipVersion chipVersion) {
        SimpleChipVersion simpleChipVersion = new SimpleChipVersion();
        simpleChipVersion.setId(chipVersion.getId());
        simpleChipVersion.setName(chipVersion.getName());
        simpleChipVersion.setMainImage(chipVersion.getMainImage());
        simpleChipVersion.setAverageRating(chipVersion.getAverageRating());

        return simpleChipVersion;
    }
}
