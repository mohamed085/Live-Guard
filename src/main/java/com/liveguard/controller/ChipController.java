package com.liveguard.controller;

import com.liveguard.domain.Chip;
import com.liveguard.domain.ChipAssociatedDetails;
import com.liveguard.dto.ChipAssociatedDetailsDTO;
import com.liveguard.dto.ChipDTO;
import com.liveguard.dto.ChipSimpleDataDTO;
import com.liveguard.mapper.ChipAssociatedDetailsMapper;
import com.liveguard.mapper.ChipMapper;
import com.liveguard.payload.AddNewChipRequest;
import com.liveguard.payload.ApiResponse;
import com.liveguard.service.ChipAssociatedDetailsService;
import com.liveguard.service.ChipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/chips")
public class ChipController {

    private final ChipService chipService;
    private final ChipAssociatedDetailsService chipAssociatedDetailsService;

    public ChipController(ChipService chipService, ChipAssociatedDetailsService chipAssociatedDetailsService) {
        this.chipService = chipService;
        this.chipAssociatedDetailsService = chipAssociatedDetailsService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllChips() {
        log.debug("ChipTypeController | getAllChipTypes");

        List<ChipSimpleDataDTO> chipDTOs = new ArrayList<>();
        chipService.findAll().forEach(chip -> chipDTOs
                .add(new ChipSimpleDataDTO(chip.getId(),
                        chip.getName(),
                        chip.getPassword(),
                        chip.getUsed(),
                        chip.getPhoto())));

        return ResponseEntity
                .ok()
                .body(chipDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getChip(@PathVariable("id") Long id) {
        log.debug("ChipTypeController | getChipType | id: " + id);


        return ResponseEntity
                .ok()
                .body(ChipMapper.chipToChipDTO(chipService.findById(id)));
    }


    @RequestMapping(value = "",  method = RequestMethod.POST, consumes = {"multipart/form-data"})
    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_vendor')")
    public ResponseEntity<?> addChip(@Valid @ModelAttribute ChipDTO chipDTO) {
        log.debug("ChipTypeController | addChipType | chipTypeDTO: " + chipDTO.getName());

        Chip returnChip = chipService.add(chipDTO);
        return ResponseEntity
                .ok()
                .body(ChipMapper.chipToChipDTO(returnChip));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_vendor')")
    public ResponseEntity<?> deleteChip(@PathVariable("id") Long id) {
        log.debug("ChipTypeController | deleteChip | id: " + id);

        chipService.delete(id);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Chip deleted successfully"));
    }

    @PostMapping("/add-new-chip-to-user")
    public ResponseEntity<?> addNewChipToUser(@RequestBody AddNewChipRequest addNewChipRequest) {
        log.debug("ChipTypeController | addNewChipToUser");

        return ResponseEntity
                .ok()
                .body(chipService.addNewChipToUser(addNewChipRequest.getId(), addNewChipRequest.getPassword()));
    }

    @GetMapping("/get-user-chips")
    private ResponseEntity<?> getUserChips() {
        log.debug("ChipTypeController | getUserChips");

        Set<ChipDTO> chipDTOs = new HashSet<>();
        chipService.getUserChips().forEach(chip -> chipDTOs.add(ChipMapper.chipToChipDTO(chip)));

        return ResponseEntity
                .ok()
                .body(chipDTOs);
    }


    @GetMapping("/chipType/{chipTypeId}")
    public ResponseEntity<?> getChipsByType(@PathVariable("chipTypeId") Long chipTypeId) {

        log.debug("ChipTypeController | getChipsByType | chipTypeId: " + chipTypeId);

        List<ChipDTO> chipDTOs = new ArrayList<>();
        chipService.getChipsByType(chipTypeId).forEach(chip -> chipDTOs.add(ChipMapper.chipToChipDTO(chip)));
        return ResponseEntity
                .ok()
                .body(chipDTOs);
    }

    @RequestMapping(value = "/{id}/chip_associated_details",  method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<?> addChipAssociatedDetails(@PathVariable("id") Long id,
                                                      @Valid @ModelAttribute ChipAssociatedDetailsDTO chipAssociatedDetailsDTO) {

        log.debug("ChipTypeController | addChipAssociatedDetails | chip id: " + id);
        log.debug("ChipTypeController | addChipAssociatedDetails | chipAssociatedDetailsDTO: " + chipAssociatedDetailsDTO.getName());

        ChipAssociatedDetails savedChipAssociatedDetails = chipAssociatedDetailsService.addChipAssociatedDetails(id, chipAssociatedDetailsDTO);
        return ResponseEntity
                .ok()
                .body(ChipAssociatedDetailsMapper.chipAssociatedDetailsToChipAssociatedDetailsDTO(savedChipAssociatedDetails));

    }



}
