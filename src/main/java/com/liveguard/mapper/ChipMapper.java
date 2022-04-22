package com.liveguard.mapper;

import com.liveguard.domain.Chip;
import com.liveguard.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ChipMapper {

    public static ChipDTO chipToChipDTO(Chip chip, Boolean setPassword) {
        log.debug("ChipMapper | chipToChipDTO | chip: " + chip.toString());
        ChipDTO chipDTO = new ChipDTO();

        List<ChipDetailDTO> details = new ArrayList<>();
        List<ChipUserDTO> chipUsers = new ArrayList<>();

        if (chip.getDetails() != null) {
            chip.getDetails().forEach(detail -> details.add(new ChipDetailDTO(detail.getId(), detail.getName(), detail.getValue())));
        }

        if (chip.getUsers() != null) {
            chip.getUsers().forEach(chipUser -> {
                chipUsers.add(new ChipUserDTO(chipUser.getId(),
                        new SimpleUserDTO(chipUser.getUser().getId(), chipUser.getUser().getEmail(), chipUser.getUser().getName(), chipUser.getUser().getAbout(), chipUser.getUser().getAvatar()),
                        chipUser.getAddDate(), chipUser.getChipUserType()));
            });
        }
        chipDTO.setId(chip.getId());
        chipDTO.setName(chip.getName());
        chipDTO.setPhoto(chip.getPhoto());

        if (setPassword) {
            chipDTO.setPassword(chip.getPassword());
        } else {
            chipDTO.setPassword(null);
        }
        chipDTO.setUsed(chip.getUsed());
        chipDTO.setChipVersionId(chip.getChipVersion().getId());
        chipDTO.setChipVersion(new SimpleChipVersion(chip.getChipVersion().getId(),
                chip.getChipVersion().getName(), chip.getChipVersion().getMainImage(),
                chip.getChipVersion().getAverageRating()));
        chipDTO.setDetails(details);
        chipDTO.setUsers(chipUsers);

        return chipDTO;
    }

}