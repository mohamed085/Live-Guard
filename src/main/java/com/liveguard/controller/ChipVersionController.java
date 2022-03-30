package com.liveguard.controller;

import com.liveguard.dto.ChipVersionDTO;
import com.liveguard.service.ChipVersionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/chip-versions")
public class ChipVersionController {

    private final ChipVersionService chipVersionService;

    public ChipVersionController(ChipVersionService chipVersionService) {
        this.chipVersionService = chipVersionService;
    }

    @RequestMapping(value = "",  method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<?> add(@ModelAttribute @Valid ChipVersionDTO chipVersionDTO) {
        log.debug("ChipVersionController | add | chipVersionDTO: " + chipVersionDTO.toString());

        return ResponseEntity
                .ok()
                .body(chipVersionService.add(chipVersionDTO));
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        log.debug("ChipVersionController | getAll");

        return ResponseEntity
                .ok()
                .body(chipVersionService.findAll());

    }

    @GetMapping("/enable")
    public ResponseEntity<?> getAllEnable() {
        log.debug("ChipVersionController | getAll");

        return ResponseEntity
                .ok()
                .body(chipVersionService.findAllEnable());

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        log.debug("ChipVersionController | getById | id: " + id);

        return ResponseEntity
                .ok()
                .body(chipVersionService.findById(id));
    }

}
