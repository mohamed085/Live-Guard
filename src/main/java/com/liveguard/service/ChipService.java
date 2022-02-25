package com.liveguard.service;

import com.liveguard.domain.Chip;
import com.liveguard.domain.ChipAssociatedDetails;
import com.liveguard.dto.ChipAssociatedDetailsDTO;
import com.liveguard.dto.ChipDTO;

import java.io.IOException;
import java.util.List;

public interface ChipService {

    List<Chip> findAll();

    Chip findById(Long id);

    Chip add(ChipDTO chipDTO) throws IOException;

    Chip save(Chip chip);

    List<Chip> getChipsByType(Long chipTypeId);

    ChipAssociatedDetails addChipAssociatedDetails(Long chipId, ChipAssociatedDetailsDTO chipAssociatedDetailsDTO) throws IOException;

}
