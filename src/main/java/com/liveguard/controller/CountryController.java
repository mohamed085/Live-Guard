package com.liveguard.controller;

import com.liveguard.service.CountryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        log.debug("CountryController | getAll");

        return ResponseEntity
                .ok()
                .body(countryService.findAllByOrderByNameAsc());
    }
}
