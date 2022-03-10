package com.liveguard.controller;

import com.liveguard.service.CurrencyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        log.debug("CurrencyController | getAll");

        return ResponseEntity
                .ok()
                .body(currencyService.findAll());
    }
}
