package com.liveguard.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/location")
public class LocationController {

    @GetMapping("/{chipId}/{lng}/{lat}")
    public ResponseEntity<?> addLocation(@PathVariable("chipId") Long chipId,
                                        @PathVariable("lng") Double lng,
                                        @PathVariable("lat") Double lat) {

        String s = "chipId: " + chipId + ", lng: " + lng + ", lat: " + lat;
        return ResponseEntity
                .ok()
                .body(s);
    }
}
