package com.liveguard.controller;

import com.liveguard.dto.ChipDTO;
import com.liveguard.payload.AddNewChipRequest;
import com.liveguard.payload.ApiResponse;
import com.liveguard.payload.UpdateChipDetailsRequest;
import com.liveguard.service.ChipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/chips")
public class ChipController {

    private final ChipService chipService;

    public ChipController(ChipService chipService) {
        this.chipService = chipService;
    }

    @PostMapping("")
    public ResponseEntity<?> addChip(@Valid @RequestBody ChipDTO chipDTO) {
        log.debug("ChipTypeController | addChipType | chipTypeDTO: " + chipDTO.getName());

        return ResponseEntity
                .ok()
                .body(chipService.add(chipDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        log.debug("ChipTypeController | getAll");

        return ResponseEntity
                .ok()
                .body(chipService.findById(id));
    }

    @PostMapping("/add-new-chip-my-chips")
    public ResponseEntity<?> addNewChipToUser(@RequestBody AddNewChipRequest addNewChipRequest) {
        log.debug("ChipTypeController | addNewChipToUser");

        return ResponseEntity
                .ok()
                .body(chipService.addNewChipToUser(addNewChipRequest.getId(), addNewChipRequest.getPassword()));
    }

    @PutMapping("/{id}/photo")
    public ResponseEntity<?> updateChipPhoto(@PathVariable("id") Long id, @Valid @RequestParam("file") MultipartFile multipartFile) {
        log.debug("ChipTypeController | updateChipPhoto | chip id: " + id);
        log.debug("ChipTypeController | updateChipPhoto");

        return ResponseEntity
                .ok()
                .body(chipService.updatePhoto(id, multipartFile));
    }

    @PutMapping("/{id}/details")
    public ResponseEntity<?> updateChipDetails(@PathVariable("id") Long id, @Valid @RequestBody UpdateChipDetailsRequest updateChipDetailsRequest) {
        log.debug("ChipTypeController | updateChipDetails | chip id: " + id);
        log.debug("ChipTypeController | updateChipDetails");

        return ResponseEntity
                .ok()
                .body(chipService.updateChipDetails(id, updateChipDetailsRequest));
    }

    @GetMapping("/{chipId}/new-user/{userId}")
    public ResponseEntity<?> addNewUser(@PathVariable("chipId") Long chipId,
                                        @PathVariable("userId") Long userId) {
        log.debug("ChipTypeController | addNewUser | chip id: " + chipId);
        log.debug("ChipTypeController | addNewUser | user id: " + userId);

        return ResponseEntity
                .ok()
                .body(chipService.addNewUser(chipId, userId));
    }

    @GetMapping("/my-chips")
    public ResponseEntity<?> getAllMyChips() {
        log.debug("ChipTypeController | getAllMyChips");

        return ResponseEntity
                .ok()
                .body(chipService.findAllUserChips());
    }

}
