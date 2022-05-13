package com.liveguard.mapper;

import com.liveguard.domain.ChipUser;
import com.liveguard.dto.ChipUserDetailDTO;
import com.liveguard.dto.ChipUserDTO;
import com.liveguard.dto.SimpleChipVersion;
import com.liveguard.dto.SimpleUserDTO;

import java.util.ArrayList;
import java.util.List;

public class ChipUserMapper {

    public static ChipUserDTO chipUserTOChipUserDTO(ChipUser chipUser) {
        List<ChipUserDetailDTO> details = new ArrayList<>();

        if (chipUser.getDetails() != null) {
            chipUser.getDetails().forEach(detail -> details.add(new ChipUserDetailDTO(detail.getId(), detail.getName(), detail.getValue())));
        }

        ChipUserDTO chipUserDTO = new ChipUserDTO();
        chipUserDTO.setId(chipUser.getId());
        chipUserDTO.setName(chipUser.getName());
        chipUserDTO.setPhoto(chipUser.getPhoto());
        chipUserDTO.setAddDate(chipUser.getAddDate());
        chipUserDTO.setChipUserType(chipUser.getChipUserType());
        chipUserDTO.setDetails(details);
        chipUserDTO.setChipId(chipUser.getChip().getId());
        chipUserDTO.setChipVersion(new SimpleChipVersion(chipUser.getChip().getChipVersion().getId(),
                chipUser.getChip().getChipVersion().getName(), chipUser.getChip().getChipVersion().getMainImage(),
                chipUser.getChip().getChipVersion().getAverageRating()));


        return chipUserDTO;
    }
}
