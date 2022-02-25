package com.liveguard.mapper;

import com.liveguard.domain.Chip;
import com.liveguard.dto.UserSimpleDataDTO;
import com.liveguard.dto.ChipDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ChipMapper {

    public static Chip chipDTOToChip(ChipDTO chipDTO) {
        Chip chip = new Chip();
        chip.setName(chipDTO.getName());

        return chip;
    }

    public static ChipDTO chipToChipDTO(Chip chip) {
        ChipDTO chipDTO = new ChipDTO();
        chipDTO.setId(chip.getId());
        chipDTO.setName(chip.getName());
        chipDTO.setPhoto(chip.getPhoto());
        chipDTO.setChipType(ChipTypeMapper.chipTypeToChipTypeDTO(chip.getChipType()));
        chipDTO.setChipTypeId(chip.getChipType().getId());

        List<UserSimpleDataDTO> users = new ArrayList<>();
        chip.getUsers().forEach(user -> users.add(new UserSimpleDataDTO(user.getId(), user.getEmail(), user.getName(), user.getAvatar())));

        chipDTO.setUsers(users);

        if (chip.getChipAssociatedDetails() != null)
            chipDTO.setChipAssociatedDetails(ChipAssociatedDetailsMapper.chipAssociatedDetailsToChipAssociatedDetailsDTO(chip.getChipAssociatedDetails()));


        return chipDTO;
    }


}
