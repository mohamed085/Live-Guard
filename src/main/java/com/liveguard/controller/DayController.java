package com.liveguard.controller;

import com.liveguard.service.DayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/days")
public class DayController {

    private final DayService dayService;

    public DayController(DayService dayService) {
        this.dayService = dayService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllDays() {
        log.debug("DayController | getAllDays");

        return ResponseEntity
                .ok()
                .body(dayService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDay(@PathVariable("id") Long id) {
        log.debug("DayController | getDay | id: " + id);

        return ResponseEntity
                .ok()
                .body(dayService.findById(id));
    }
}
