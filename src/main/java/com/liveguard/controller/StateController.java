package com.liveguard.controller;

import com.liveguard.dto.StateDTO;
import com.liveguard.mapper.StateMapper;
import com.liveguard.payload.ApiResponse;
import com.liveguard.service.StateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/states")
public class StateController {

    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> addState(@RequestBody StateDTO state) {
        log.debug("StateController | addState | state: " + state.toString());
        stateService.add(state);

        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "State saved successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getState(@PathVariable("id") Long id) {
        log.debug("StateController | getState | id: " + id);

        return ResponseEntity
                .ok()
                .body(StateMapper.stateToStateDTO(stateService.findById(id)));

    }


    @GetMapping("/country/{id}")
    public ResponseEntity<?> getCountryStates(@PathVariable("id") Long id) {
        log.debug("StateController | getCountryStates | country id: " + id);
        List<StateDTO> stateDTOs = new ArrayList<>();

        stateService.findByCountryIdOrderByNameAsc(id).forEach(state -> stateDTOs.add(StateMapper.stateToStateDTO(state)));
        return ResponseEntity
                .ok()
                .body(stateDTOs);

    }
}
