package com.liveguard.service;

import com.liveguard.domain.Chip;
import com.liveguard.domain.ChipAssociatedDetails;
import com.liveguard.dto.ChipAssociatedDetailsDTO;
import com.liveguard.dto.ChipDTO;
import com.liveguard.payload.ApiResponse;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface ChipService {

    List<Chip> findAll();

    Chip findById(Long id);

    Chip add(ChipDTO chipDTO);

    Chip save(Chip chip);

    List<Chip> getChipsByType(Long chipTypeId);

    Set<Chip> getUserChips();

    ApiResponse addNewChipToUser(Long chipId, String chipPassword);
}
