package com.liveguard.controller;

import com.liveguard.domain.ChipType;
import com.liveguard.dto.ChipTypeDTO;
import com.liveguard.mapper.ChipTypeMapper;
import com.liveguard.service.ChipTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    @PostMapping("")
    public ResponseEntity<?> addChipType(@Valid @RequestBody ChipTypeDTO chipTypeDTO) {
        log.debug("ChipTypeController | addChipType | chipTypeDTO: " + chipTypeDTO.toString());

        ChipTypeDTO savedChipTypeDTO = ChipTypeMapper.chipTypeToChipTypeDTO(chipTypeService.add(chipTypeDTO));
        return ResponseEntity
                .ok()
                .body(savedChipTypeDTO);
    }

}
