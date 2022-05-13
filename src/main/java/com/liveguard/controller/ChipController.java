package com.liveguard.controller;

import com.liveguard.dto.ChipDTO;
import com.liveguard.mapper.ChipMapper;
import com.liveguard.payload.ApiResponse;
import com.liveguard.service.ChipService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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
        log.debug("ChipController | addChip | chipDTO: " + chipDTO.toString());

        chipService.save(chipDTO);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Chip added successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        log.debug("ChipController | getById | id: " + id);

        return ResponseEntity
                .ok()
                .body(ChipMapper.chipToChipDTO(chipService.findById(id), false));
    }

    @GetMapping("")
    public ResponseEntity<?> getFirstPage() {
        log.debug("ChipController | getFirstPage");

        return getAllByPage(1, null, null, null);
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

        Page<ChipDTO> chipDTOPage = chipService
                .findAllByPage(pageNum, sortField, sortDir, keyword)
                .map(chip -> {
                    ChipDTO chipDTO = ChipMapper.chipToChipDTO(chip, true);
                    return chipDTO;
                });


        return ResponseEntity
                .ok()
                .body(chipDTOPage);
    }

    @GetMapping("/version/{id}")
    public ResponseEntity<?> getAllByChipVersion(@PathVariable("id") Long id) {
        log.debug("ChipController | getAll");

        List<ChipDTO> chipDTOS = new ArrayList<>();
        chipService.findAllByChipVersion(id).forEach(chip -> {
            chipDTOS.add(ChipMapper.chipToChipDTO(chip, true));
        });

        return ResponseEntity
                .ok()
                .body(chipDTOS);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        log.debug("ChipController | deleteById | id: " + id);

        chipService.deleteById(id);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Chip deleted successfully"));
    }

}
