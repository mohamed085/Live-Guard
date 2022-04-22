package com.liveguard.controller;

import com.liveguard.dto.ChipDTO;
import com.liveguard.dto.SimpleChipDTO;
import com.liveguard.mapper.ChipMapper;
import com.liveguard.payload.AddNewChipRequest;
import com.liveguard.payload.ApiResponse;
import com.liveguard.payload.UpdateChipDetailsRequest;
import com.liveguard.service.ChipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        log.debug("ChipController | addChipType | chipTypeDTO: " + chipDTO.getName());

        return ResponseEntity
                .ok()
                .body(ChipMapper.chipToChipDTO(chipService.add(chipDTO), false));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        log.debug("ChipController | getAll");

        return ResponseEntity
                .ok()
                .body(ChipMapper.chipToChipDTO(chipService.findById(id), false));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        log.debug("ChipController | getAll");

        chipService.deleteById(id);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Chip deleted successfully"));
    }


    @GetMapping("")
    public ResponseEntity<?> getAll() {
        log.debug("ChipController | getAll");

        Page<ChipDTO> chipDTOPage = chipService
                .findAllByPage(1, "id", "asc", null)
                .map(chip -> {
                    ChipDTO chipDTO = ChipMapper.chipToChipDTO(chip, true);
                    return chipDTO;
                });

        return ResponseEntity
                .ok()
                .body(chipDTOPage);
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

    @PostMapping("/add-new-chip-my-chips")
    public ResponseEntity<?> addNewChipToUser(@RequestBody AddNewChipRequest addNewChipRequest) {
        log.debug("ChipController | addNewChipToUser");

        chipService.addNewChipToUser(addNewChipRequest.getId(), addNewChipRequest.getPassword());
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Chip add successfully"));
    }

    @PutMapping("/{id}/photo")
    public ResponseEntity<?> updateChipPhoto(@PathVariable("id") Long id, @Valid @RequestParam("file") MultipartFile multipartFile) {
        log.debug("ChipController | updateChipPhoto | chip id: " + id);
        log.debug("ChipController | updateChipPhoto");

        chipService.updatePhoto(id, multipartFile);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Photo changed successfully"));
    }

    @PutMapping("/{id}/details")
    public ResponseEntity<?> updateChipDetails(@PathVariable("id") Long id, @Valid @RequestBody UpdateChipDetailsRequest updateChipDetailsRequest) {
        log.debug("ChipController | updateChipDetails | chip id: " + id);

        chipService.updateChipDetails(id, updateChipDetailsRequest);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Details changed successfully"));
    }

    @GetMapping("/{chipId}/new-user/{userId}")
    public ResponseEntity<?> addNewUser(@PathVariable("chipId") Long chipId,
                                        @PathVariable("userId") Long userId) {
        log.debug("ChipController | addNewUser | chip id: " + chipId);
        log.debug("ChipController | addNewUser | user id: " + userId);

        chipService.addNewUser(chipId, userId);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "User added successfully"));
    }

    @GetMapping("/{chipId}/remove-user/{userId}")
    public ResponseEntity<?> removeUser(@PathVariable("chipId") Long chipId,
                                           @PathVariable("userId") Long userId) {
        log.debug("ChipController | removeUser | chip id: " + chipId);
        log.debug("ChipController | removeUser | user id: " + userId);

        chipService.removeUser(chipId, userId);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "User removed successfully"));
    }

    @GetMapping("/my-chips")
    public ResponseEntity<?> getAllMyChips() {
        log.debug("ChipController | getAllMyChips");

        List<SimpleChipDTO> chips = new ArrayList<>();

        chipService.findAllAuthenticatedUserChips().forEach(chip -> {
            chips.add(new SimpleChipDTO(chip.getId(), chip.getName(), chip.getPhoto()));
        });

        return ResponseEntity
                .ok()
                .body(chips);
    }

}
