package com.liveguard.controller;


import com.liveguard.dto.LocationDTO;
import com.liveguard.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/location")
    @ResponseStatus(HttpStatus.CREATED)
    public void addLocation(@RequestBody LocationDTO locationDTO) {

        log.debug("LocationController | addLocation | chipId: " + locationDTO.getChipId());
        log.debug("LocationController | addLocation | Location lat: " + locationDTO.getLat() + " ,lng: " +locationDTO.getLng());

        locationService.add(locationDTO);
    }
}
