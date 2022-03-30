package com.liveguard.service;

import com.liveguard.domain.ChipVersion;
import com.liveguard.dto.ChipVersionDTO;

import java.util.List;

public interface ChipVersionService {

    ChipVersionDTO add(ChipVersionDTO chipVersionDTO);

    ChipVersionDTO findById(Long id);

    List<ChipVersionDTO> findAll();

    List<ChipVersionDTO> findAllEnable();
}
