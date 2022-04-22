package com.liveguard.controller;

import com.liveguard.dto.ChipVersionDTO;
import com.liveguard.mapper.ChipVersionMapper;
import com.liveguard.payload.*;
import com.liveguard.service.ChipVersionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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
                .body(ChipVersionMapper.chipVersionToChipVersionDTO(chipVersionService.add(chipVersionDTO)));
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        log.debug("ChipVersionController | getAll");

        List<ChipVersionDTO> chipVersionDTOs = new ArrayList<>();

        chipVersionService.findAll().forEach(chipVersion -> {
            chipVersionDTOs.add(ChipVersionMapper.chipVersionToChipVersionDTO(chipVersion));
        });

        return ResponseEntity
                .ok()
                .body(chipVersionDTOs);

    }

    @GetMapping("/enable")
    public ResponseEntity<?> getAllEnable() {
        log.debug("ChipVersionController | getAll");

        List<ChipVersionDTO> chipVersionDTOs = new ArrayList<>();

        chipVersionService.findAllEnable().forEach(chipVersion -> {
            chipVersionDTOs.add(ChipVersionMapper.chipVersionToChipVersionDTO(chipVersion));
        });

        return ResponseEntity
                .ok()
                .body(chipVersionDTOs);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        log.debug("ChipVersionController | getById | id: " + id);

        return ResponseEntity
                .ok()
                .body(ChipVersionMapper.chipVersionToChipVersionDTO(chipVersionService.findById(id)));
    }

    @GetMapping("/{id}/enabled/{status}")
    public ResponseEntity<?> updateEnabledStatus(@PathVariable("id") Long id, @PathVariable("status") Boolean status) {
        log.debug("ChipVersionController | updateEnabledStatus | chip id: " + id);

        chipVersionService.updateEnabledStatus(id, status);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Chip id: " + id + " updated successfully"));
    }

    @GetMapping("/in-stock/{id}/{status}")
    public ResponseEntity<?> updateInStockStatus(@PathVariable("id") Long id, @PathVariable("status") Boolean status) {
        log.debug("ChipVersionController | updateInStockStatus | chip id: " + id);

        chipVersionService.updateInStockStatus(id, status);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Chip id: " + id + " updated successfully"));
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<ApiResponse> updateMainImage(@PathVariable("id") Long id,
                                                       @Valid @RequestParam("file") MultipartFile multipartFile) {
        log.debug("ChipVersionController | updateMainImage | chip id: " + id);

        chipVersionService.updateMainImage(id, multipartFile);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Image saved successfully"));
    }

    @PutMapping("/{id}/overview")
    public ResponseEntity<ApiResponse> updateOverview(@PathVariable("id") Long id,
                                                      @Valid @RequestBody UpdateChipVersionOverviewRequest chipVersionOverview) {

        log.debug("ChipVersionController | updateMainImage | chip id: " + id);
        log.debug("ChipVersionController | updateOverview | chipVersionOverview: " + chipVersionOverview.toString());

        this.chipVersionService.updateOverview(id, chipVersionOverview);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Chip version id: " + id + " updated successfully"));
    }

    @PutMapping("/{id}/description")
    public ResponseEntity<ApiResponse> updateDescription(@PathVariable("id") Long id,
                                                         @Valid @RequestBody UpdateChipVersionDescriptionRequest chipVersionDescription) {

        log.debug("ChipVersionController | updateDescription | chip id: " + id);
        log.debug("ChipVersionController | updateDescription | chipVersionOverview: " + chipVersionDescription.toString());

        chipVersionService.updateDescription(id, chipVersionDescription);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Chip id: " + id + " updated successfully"));
    }


    @PutMapping("/{id}/details")
    public ResponseEntity<ApiResponse> updateDetails(@PathVariable("id") Long id,
                                                     @Valid @RequestBody UpdateChipVersionDetailsRequest chipVersionDetails) {

        log.debug("ChipVersionController | updateDetails | chip id: " + id);
        log.debug("ChipVersionController | updateDetails | chipVersionOverview: " + chipVersionDetails.toString());

        chipVersionService.updateDetails(id, chipVersionDetails.getChipTypeDetailDTOs());
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Chip id: " + id + " updated successfully"));
    }

    @PutMapping("/{id}/shipping")
    public ResponseEntity<ApiResponse> updateShipping(@PathVariable("id") Long id,
                                                      @Valid @RequestBody UpdateChipVersionShippingRequest chipVersionShipping) {

        log.debug("ChipVersionController | updateShipping | chip id: " + id);
        log.debug("ChipVersionController | updateShipping | chipVersionShipping: " + chipVersionShipping.toString());

        chipVersionService.updateShipping(id, chipVersionShipping);

        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Chip id: " + id + " updated successfully"));
    }

}
