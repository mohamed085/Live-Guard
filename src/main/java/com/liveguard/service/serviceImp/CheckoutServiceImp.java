package com.liveguard.service.serviceImp;

import com.liveguard.domain.Address;
import com.liveguard.domain.CheckoutInfo;
import com.liveguard.domain.ShippingRate;
import com.liveguard.domain.ShoppingCart;
import com.liveguard.exception.BusinessException;
import com.liveguard.mapper.ShoppingCartMapper;
import com.liveguard.service.AddressService;
import com.liveguard.service.CheckoutService;
import com.liveguard.service.ShippingRateService;
import com.liveguard.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CheckoutServiceImp implements CheckoutService {

    private final ShoppingCartService shoppingCartService;
    private final ShippingRateService shippingRateService;
    private final AddressService addressService;

    public CheckoutServiceImp(ShoppingCartService shoppingCartService, ShippingRateService shippingRateService, AddressService addressService) {
        this.shoppingCartService = shoppingCartService;
        this.shippingRateService = shippingRateService;
        this.addressService = addressService;
    }

    @Override
    public CheckoutInfo generateCheckout() {
        log.debug("CheckoutService | generateCheckout");
        ShoppingCart shoppingCart = shoppingCartService.findMyShoppingCart();

        log.debug("CheckoutService | generateCheckout | shoppingCart: " + shoppingCart.toString());

        CheckoutInfo checkoutInfo = new CheckoutInfo();
        checkoutInfo.setItems(ShoppingCartMapper.shoppingCartItemSetToShoppingCartItemDTOSet(shoppingCart.getShoppingCartItems()));
        checkoutInfo.setProductTotal(shoppingCart.getProductsTotal());

        Address address = addressService.findMyDefaultAddress();

        if (address == null) {
            throw new BusinessException("You should add address first", HttpStatus.BAD_REQUEST);
        }

        String shipTo = "'" + address.getUser().getName() + "', " + address.getAddressLine() + ", " +
                address.getCity() + ", " + address.getState() + ", " + address.getCountry() + ", Phone number: " + address.getPhoneNumber();

        checkoutInfo.setShipTo(shipTo);

        log.debug("CheckoutService | generateCheckout | address: " + address.toString());
        ShippingRate shippingRate = shippingRateService.getShippingRateForAddress(address)
                .orElseThrow(() -> new BusinessException("No shipping for your address please select another address or add new address", HttpStatus.BAD_REQUEST));

        float shippingTotal = shoppingCart.getQuantity() * shippingRate.getRate();
        checkoutInfo.setShippingTotal(shippingTotal);

        float paymentTotal = shippingTotal + shoppingCart.getProductsTotal();
        checkoutInfo.setPaymentTotal(paymentTotal);

        checkoutInfo.setCodSupported(shippingRate.isCodSupported());
        checkoutInfo.setDeliverDays(shippingRate.getDays());

        log.debug("CheckoutService | generateCheckout | shippingRate: " + shippingRate.toString());

        return checkoutInfo;
    }
}
