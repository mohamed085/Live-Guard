package com.liveguard.controller;

import com.liveguard.dto.ChipDTO;
import com.liveguard.payload.AddNewChipRequest;
import com.liveguard.payload.ApiResponse;
import com.liveguard.payload.UpdateChipDetailsRequest;
import com.liveguard.service.ChipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
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
        log.debug("ChipController | addChipType | chipTypeDTO: " + chipDTO.getName());

        return ResponseEntity
                .ok()
                .body(chipService.add(chipDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        log.debug("ChipController | getAll");

        return ResponseEntity
                .ok()
                .body(chipService.findById(id));
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        log.debug("ChipController | getAll");

        return ResponseEntity
                .ok()
                .body(chipService.findAllByPage(1, "id", "asc", null));
    }

    @GetMapping("/page/{pageNum}")
    public ResponseEntity<?> getAllByPage(@PathVariable(name = "pageNum") int pageNum,
                                          @Param("sortField") String sortField,
                                          @Param("sortDir") String sortDir,
                                          @Param("keyword") String keyword) {

        log.debug("ChipController | getAllByPage");
        log.debug("ChipController | getAllByPage | pageNum: " + pageNum);
        log.debug("ChipController | getAllByPage | sortField: " + sortField);
        log.debug("ChipController | getAllByPage | sortDir: " + sortDir);
        log.debug("ChipController | getAllByPage | keyword: " + keyword);

        return ResponseEntity
                .ok()
                .body(chipService.findAllByPage(pageNum, sortField, sortDir, keyword));
    }

    @GetMapping("/version/{id}")
    public ResponseEntity<?> getAllByChipVersion(@PathVariable("id") Long id) {
        log.debug("ChipController | getAll");

        return ResponseEntity
                .ok()
                .body(chipService.findAllByChipVersion(id));
    }

    @PostMapping("/add-new-chip-my-chips")
    public ResponseEntity<?> addNewChipToUser(@RequestBody AddNewChipRequest addNewChipRequest) {
        log.debug("ChipController | addNewChipToUser");

        return ResponseEntity
                .ok()
                .body(chipService.addNewChipToUser(addNewChipRequest.getId(), addNewChipRequest.getPassword()));
    }

    @PutMapping("/{id}/photo")
    public ResponseEntity<?> updateChipPhoto(@PathVariable("id") Long id, @Valid @RequestParam("file") MultipartFile multipartFile) {
        log.debug("ChipController | updateChipPhoto | chip id: " + id);
        log.debug("ChipController | updateChipPhoto");

        return ResponseEntity
                .ok()
                .body(chipService.updatePhoto(id, multipartFile));
    }

    @PutMapping("/{id}/details")
    public ResponseEntity<?> updateChipDetails(@PathVariable("id") Long id, @Valid @RequestBody UpdateChipDetailsRequest updateChipDetailsRequest) {
        log.debug("ChipController | updateChipDetails | chip id: " + id);
        log.debug("ChipController | updateChipDetails");

        return ResponseEntity
                .ok()
                .body(chipService.updateChipDetails(id, updateChipDetailsRequest));
    }

    @GetMapping("/{chipId}/new-user/{userId}")
    public ResponseEntity<?> addNewUser(@PathVariable("chipId") Long chipId,
                                        @PathVariable("userId") Long userId) {
        log.debug("ChipController | addNewUser | chip id: " + chipId);
        log.debug("ChipController | addNewUser | user id: " + userId);

        return ResponseEntity
                .ok()
                .body(chipService.addNewUser(chipId, userId));
    }

    @GetMapping("/my-chips")
    public ResponseEntity<?> getAllMyChips() {
        log.debug("ChipController | getAllMyChips");

        return ResponseEntity
                .ok()
                .body(chipService.findAllUserChips());
    }

}
