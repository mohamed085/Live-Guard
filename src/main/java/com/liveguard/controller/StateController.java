package com.liveguard.controller;

import com.liveguard.dto.StateDTO;
import com.liveguard.payload.ApiResponse;
import com.liveguard.service.StateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/states")
public class StateController {

    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping("/country/{id}")
    public ResponseEntity<?> getAllCountryId(@PathVariable("id") Long id) {
        log.debug("StateController | getAllCountryId | id: " + id);

        return ResponseEntity
                .ok()
                .body(stateService.findAllByCountryId(id));
    }

    @PostMapping("/country/{id}")
    public ResponseEntity<?> save(@PathVariable("id") Long id,
                                  @RequestBody StateDTO stateDTO) {
        log.debug("StateController | save | id: " + id);
        log.debug("StateController | save | id: " + stateDTO.toString());

        stateService.save(stateDTO);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "State added successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        log.debug("StateController | deleteById | id: " + id);

        stateService.delete(id);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "State deleted successfully"));
    }

}
