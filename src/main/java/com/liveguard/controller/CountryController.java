package com.liveguard.controller;

import com.liveguard.domain.Country;
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
    public ResponseEntity<?> findAll() {
        log.debug("CountryController | findAll");

        return ResponseEntity
                .ok()
                .body(countryService.findAll());
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Country country) {
        log.debug("CountryController | save | country: " + country.toString());

        countryService.save(country);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Country added successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        log.debug("CountryController | deleteById | id: " + id);

        countryService.delete(id);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Country deleted successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestBody Country country) {

        log.debug("CountryController | update | id: " + id);
        log.debug("CountryController | update | country: " + country.toString());

        country.setId(id);
        countryService.update(country);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Country updated successfully"));
    }

}
