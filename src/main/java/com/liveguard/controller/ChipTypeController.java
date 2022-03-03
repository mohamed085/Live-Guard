package com.liveguard.controller;

import com.liveguard.domain.ChipType;
import com.liveguard.dto.ChipTypeDTO;
import com.liveguard.dto.ChipTypeDetailDTO;
import com.liveguard.exception.BusinessException;
import com.liveguard.mapper.ChipTypeMapper;
import com.liveguard.payload.*;
import com.liveguard.service.ChipTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.internalServerError;

@Slf4j
@RestController
@RequestMapping("/chip-types")
public class ChipTypeController {


    private final ChipTypeService chipTypeService;

    public ChipTypeController(ChipTypeService chipTypeService) {
        this.chipTypeService = chipTypeService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllChipTypes() {
        log.debug("ChipTypeController | getAllChipTypes");

        List<ChipTypeDTO> chipTypeDTOs = new ArrayList<>();
        chipTypeService.findAll().forEach(chipType -> chipTypeDTOs.add(ChipTypeMapper.chipTypeToChipTypeDTO(chipType)));
        return ResponseEntity
                .ok()
                .body(chipTypeDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getChipType(@PathVariable Long id) {
        log.debug("ChipTypeController | getChipType | id: " + id);

        ChipTypeDTO chipTypeDTO = ChipTypeMapper.chipTypeToChipTypeDTO(chipTypeService.findById(id));
        return ResponseEntity
                .ok()
                .body(chipTypeDTO);
    }

    @RequestMapping(value = "",  method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<?> addChipType(@Valid @ModelAttribute ChipTypeDTO chipTypeDTO) {
        log.debug("ChipTypeController | addChipType | chipTypeDTO: " + chipTypeDTO.toString());

        ChipTypeDTO savedChipTypeDTO = ChipTypeMapper.chipTypeToChipTypeDTO(chipTypeService.add(chipTypeDTO));
        return ResponseEntity
                .ok()
                .body(savedChipTypeDTO);
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<ApiResponse> updateChipTypeMainImage(@PathVariable("id") Long id,
                                                               @Valid @RequestParam("file") MultipartFile multipartFile) {
        log.debug("ChipTypeController | updateChipTypeMainImage | chip id: " + id);

        chipTypeService.updateChipTypeMainImage(id, multipartFile);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Image saved successfully"));
    }


    @PutMapping("/{id}/overview")
    public ResponseEntity<ApiResponse> updateChipTypeOverview(@PathVariable("id") Long id,
                                                              @Valid @RequestBody UpdateChipTypeOverviewRequest chipTypeOverview) {

        log.debug("ChipTypeController | updateChipTypeOverview | chip id: " + id);
        this.chipTypeService.updateChipTypeOverview(id, chipTypeOverview);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Chip id: " + id + " updated successfully"));
    }

    @PutMapping("/{id}/description")
    public ResponseEntity<ApiResponse> updateChipTypeDescription(@PathVariable("id") Long id,
                                                                 @Valid @RequestBody UpdateChipTypeDescriptionRequest chipTypeDescription) {

        log.debug("ChipTypeController | updateChipTypeDescription | chip id: " + id);
        chipTypeService.updateChipTypeDescription(id, chipTypeDescription);

        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Chip id: " + id + " updated successfully"));
    }


    @PutMapping("/{id}/details")
    public ResponseEntity<ApiResponse> updateChipTypeDetails(@PathVariable("id") Long id,
                                                             @Valid @RequestBody UpdateChipTypeDetailsRequest chipTypeDetails) {
        log.debug("ChipTypeController | updateChipTypeDetails | chip id: " + id);
        log.debug("ChipTypeController | updateChipTypeDetails | chipTypeDetails: " + chipTypeDetails.toString());
        chipTypeService.updateChipTypeDetails(id, chipTypeDetails.getChipTypeDetailDTOs());


        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Chip id: " + id + " updated successfully"));
    }

    @PutMapping("/{id}/shipping")
    public ResponseEntity<ApiResponse> updateChipTypeShipping(@PathVariable("id") Long id,
                                                              @Valid @RequestBody UpdateChipTypeShippingRequest chipTypeShipping) {
        log.debug("ChipTypeController | updateChipTypeShipping | chip id: " + id);
        chipTypeService.updateChipTypeShipping(id, chipTypeShipping);

        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Chip id: " + id + " updated successfully"));
    }

}
