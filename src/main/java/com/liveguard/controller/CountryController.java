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
    public ResponseEntity<?> getAll() {
        log.debug("CountryController | getAll");

        return ResponseEntity
                .ok()
                .body(countryService.findAllByOrderByNameAsc());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        log.debug("CountryController | getById | id: " + id);

        return ResponseEntity
                .ok()
                .body(countryService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        log.debug("CountryController | deleteById | id: " + id);
        countryService.deleteById(id);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Country deleted successfully"));
    }

    @PostMapping("")
    public ResponseEntity<?> add(@RequestBody Country country) {
        log.debug("CountryController | deleteById | name: " + country.getName());
        countryService.add(country);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Country saved successfully"));

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") Long id,
                                        @RequestBody Country country) {
        log.debug("CountryController | updateById | id: " + id);
        countryService.update(id, country); ;
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Country deleted successfully"));
    }

}
