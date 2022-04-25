package com.liveguard.service.serviceImp;

import com.liveguard.domain.CheckoutInfo;
import com.liveguard.domain.ShoppingCart;
import com.liveguard.mapper.ShoppingCartMapper;
import com.liveguard.service.CheckoutService;
import com.liveguard.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CheckoutServiceImp implements CheckoutService {

    private final ShoppingCartService shoppingCartService;

    public CheckoutServiceImp(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    public CheckoutInfo generateCheckout() {
        log.debug("CheckoutService | generateCheckout");
        ShoppingCart shoppingCart = shoppingCartService.findMyShoppingCart();

        log.debug("CheckoutService | generateCheckout | shoppingCart: " + shoppingCart.toString());

        CheckoutInfo checkoutInfo = new CheckoutInfo();
        checkoutInfo.setItems(ShoppingCartMapper.shoppingCartItemSetToShoppingCartItemDTOSet(shoppingCart.getShoppingCartItems()));
        checkoutInfo.setProductTotal(shoppingCart.getProductsTotal());

        return checkoutInfo;
    }
}
