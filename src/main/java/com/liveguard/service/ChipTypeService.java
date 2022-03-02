package com.liveguard.service;


import com.liveguard.domain.ChipType;
import com.liveguard.dto.ChipTypeDTO;

import java.io.IOException;
import java.util.List;

public interface ChipTypeService {

    List<ChipType> findAll();

    ChipType findById(Long id);

    ChipType add(ChipTypeDTO chipTypeDTO) throws IOException;

}
