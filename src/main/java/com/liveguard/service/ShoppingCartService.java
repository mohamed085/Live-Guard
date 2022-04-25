package com.liveguard.service;

import com.liveguard.domain.ShoppingCart;

public interface ShoppingCartService {

    ShoppingCart findMyShoppingCart();

    void addChipVersionToMyCart(Long chipVersionId, Integer quantity);

    void updateCartItemQuantity(Long cartItemId, int quantity);

}
