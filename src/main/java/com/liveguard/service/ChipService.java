package com.liveguard.service;

import com.liveguard.domain.Chip;
import com.liveguard.dto.ChipDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ChipService {

    Boolean existsByKey(String key);

    void save(ChipDTO chipDTO);

    Chip findById(Long id);

    List<Chip> findAll();

    Page<Chip> findAllByPage(int pageNum, String sortField, String sortDir, String keyword);

    void deleteById(Long id);

    List<Chip> findAllByChipVersion(Long id);

    void updateUsedStatus(Long chipId, Boolean used);


}
