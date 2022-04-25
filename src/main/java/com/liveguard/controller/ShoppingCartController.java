package com.liveguard.controller;

import com.liveguard.mapper.ShoppingCartMapper;
import com.liveguard.payload.ApiResponse;
import com.liveguard.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/shopping_cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("")
    public ResponseEntity<?> getMyShoppingCart() {
        log.debug("ShoppingCartController | getMyShoppingCart");

        return ResponseEntity
                .ok()
                .body(ShoppingCartMapper.shoppingCartTOShoppingCartDTO(shoppingCartService.findMyShoppingCart()));
    }

    @GetMapping("/add/{chipVersionId}/{quantity}")
    public ResponseEntity<?> addProductToCart(@PathVariable("chipVersionId") Long chipVersionId,
                                              @PathVariable("quantity") Integer quantity) {
        log.debug("ShoppingCartController | addProductToCart | productId:" + chipVersionId);
        log.debug("ShoppingCartController | addProductToCart | quantity:" + quantity);

        shoppingCartService.addChipVersionToMyCart(chipVersionId, quantity);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Product added successfully"));
    }

}
