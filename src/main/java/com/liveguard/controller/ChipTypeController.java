package com.liveguard.controller;

import com.liveguard.domain.ChipType;
import com.liveguard.dto.ChipTypeDTO;
import com.liveguard.mapper.ChipTypeMapper;
import com.liveguard.payload.ApiResponse;
import com.liveguard.service.ChipTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.internalServerError;

@Slf4j
@RestController
@RequestMapping("/chipTypes")
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

        ChipTypeDTO savedChipTypeDTO = null;
        try {
            savedChipTypeDTO = ChipTypeMapper.chipTypeToChipTypeDTO(chipTypeService.add(chipTypeDTO));
            return ResponseEntity
                    .ok()
                    .body(savedChipTypeDTO);
        } catch (IOException e) {
            return ResponseEntity
                    .internalServerError()
                    .body(new ApiResponse(false, "Failed to save chip photo"));
        }
    }

}
