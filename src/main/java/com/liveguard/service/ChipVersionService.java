package com.liveguard.service;

import com.liveguard.domain.ChipVersion;
import com.liveguard.dto.ChipVersionDTO;
import com.liveguard.payload.ApiResponse;

import java.util.List;

public interface ChipVersionService {

    ChipVersionDTO add(ChipVersionDTO chipVersionDTO);

    ChipVersionDTO findById(Long id);

    List<ChipVersionDTO> findAll();

    List<ChipVersionDTO> findAllEnable();

    void updateEnabledStatus(Long id, Boolean status);

    void updateInStockStatus(Long id, Boolean status);
}
