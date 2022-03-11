package com.liveguard.controller;

import com.liveguard.payload.ApiResponse;
import com.liveguard.service.CountryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        log.debug("CountryController | deleteById | id: " + id);
        countryService.deleteById(id);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Country deleted successfully"));
    }

}
