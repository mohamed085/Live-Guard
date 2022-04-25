package com.liveguard.controller;

import com.liveguard.service.CheckoutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/checkout_info")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @GetMapping("")
    public ResponseEntity<?> getCheckoutInfo() {
        log.debug("CheckoutController | getCheckoutInfo");

        return ResponseEntity
                .ok()
                .body(checkoutService.generateCheckout());
    }

}
