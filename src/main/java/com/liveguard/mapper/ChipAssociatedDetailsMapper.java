package com.liveguard.mapper;

import com.liveguard.domain.ChipAssociatedDetails;
import com.liveguard.dto.ChipAssociatedDetailsDTO;
import com.liveguard.dto.UserSimpleDataDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChipAssociatedDetailsMapper {

    public static ChipAssociatedDetails ChipAssociatedDetailsDTOToChipAssociatedDetails(ChipAssociatedDetailsDTO chipAssociatedDetailsDTO) {
        ChipAssociatedDetails chipAssociatedDetails = new ChipAssociatedDetails();
        chipAssociatedDetails.setName(chipAssociatedDetailsDTO.getName());
        chipAssociatedDetails.setAge(chipAssociatedDetailsDTO.getAge());
        chipAssociatedDetails.setPhone(chipAssociatedDetailsDTO.getPhone());

        return chipAssociatedDetails;
    }

    public static ChipAssociatedDetailsDTO chipAssociatedDetailsToChipAssociatedDetailsDTO (ChipAssociatedDetails chipAssociatedDetails) {
        ChipAssociatedDetailsDTO chipAssociatedDetailsDTO = new ChipAssociatedDetailsDTO();
        chipAssociatedDetailsDTO.setName(chipAssociatedDetails.getName());
        chipAssociatedDetailsDTO.setAge(chipAssociatedDetails.getAge());
        chipAssociatedDetailsDTO.setPhoto(chipAssociatedDetails.getPhoto());
        chipAssociatedDetailsDTO.setPhone(chipAssociatedDetails.getPhone());
        chipAssociatedDetailsDTO.setAddByUser(new UserSimpleDataDTO(
                chipAssociatedDetails.getAddByUser().getId(),
                chipAssociatedDetails.getAddByUser().getEmail(),
                chipAssociatedDetails.getAddByUser().getName(),
                chipAssociatedDetails.getAddByUser().getAvatar()));

        return chipAssociatedDetailsDTO;
    }
}
