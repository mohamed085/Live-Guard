package com.liveguard.mapper;

import com.liveguard.domain.Chip;
import com.liveguard.dto.ChipDTO;
import com.liveguard.dto.SimpleChipVersion;

public class ChipMapper {

    public static ChipDTO chipToChipDTO(Chip chip, Boolean setPassword) {
        ChipDTO chipDTO = new ChipDTO();
        chipDTO.setId(chip.getId());
        chipDTO.setKey(chip.getKey());
        chipDTO.setUsed(chip.getUsed());
        chipDTO.setChipVersionId(chip.getChipVersion().getId());
        chipDTO.setChipVersion(new SimpleChipVersion(chip.getChipVersion().getId(),
                chip.getChipVersion().getName(), chip.getChipVersion().getMainImage(),
                chip.getChipVersion().getAverageRating()));

        if (setPassword) {
            chipDTO.setPassword(chip.getPassword());
        }

        return chipDTO;
    }
}
