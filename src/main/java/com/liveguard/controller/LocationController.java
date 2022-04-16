package com.liveguard.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/loc")
public class LocationController {

    @GetMapping("/{chipId}/{lng}/{lat}")
    public ResponseEntity<?> addLocation(@PathVariable("chipId") Long chipId,
                            @PathVariable("lng") Double lng,
                            @PathVariable("lat") Double lat,
                            HttpServletRequest request) {

        log.debug("LocationController | addLocation | chipId: " + chipId);
        log.debug("LocationController | addLocation | lng: " + lng);
        log.debug("LocationController | addLocation | lat: " + lat);
        log.debug("LocationController | addLocation | request: " + request.getRemoteAddr());

        return ResponseEntity
                .ok()
                .body("Success");
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> addLocation2(HttpServletRequest request) {

        log.debug("LocationController | addLocation2 | request: " + request.getRemoteAddr());

        return ResponseEntity
                .ok()
                .body("Success");
    }

}
