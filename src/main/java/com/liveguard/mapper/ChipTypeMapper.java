package com.liveguard.mapper;

import com.liveguard.domain.ChipType;
import com.liveguard.dto.ChipTypeDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChipTypeMapper {

    public static ChipType chipTypeDTOToChipType(ChipTypeDTO chipTypeDTO) {
        ChipType chipType = new ChipType();
        chipType.setType(chipTypeDTO.getType());

        return chipType;
    }

    public static ChipTypeDTO chipTypeToChipTypeDTO(ChipType chipType) {
        ChipTypeDTO chipTypeDTO = new ChipTypeDTO();
        chipTypeDTO.setType(chipType.getType());
        chipTypeDTO.setId(chipType.getId());

        return chipTypeDTO;
    }
}
