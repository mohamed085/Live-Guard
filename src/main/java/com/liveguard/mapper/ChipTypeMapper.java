package com.liveguard.mapper;

import com.liveguard.domain.ChipType;
import com.liveguard.dto.ChipTypeDTO;
import com.liveguard.dto.ChipTypeDetailDTO;
import com.liveguard.dto.ChipTypeImageDTO;
import com.liveguard.dto.ChipTypeSimpleDateDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ChipTypeMapper {

    public static ChipType chipTypeDTOToChipType(ChipTypeDTO chipTypeDTO) {
        ChipType chipType = new ChipType();
        chipType.setName(chipTypeDTO.getName());
        chipType.setAlias(chipTypeDTO.getAlias());
        chipType.setShortDescription(chipTypeDTO.getShortDescription());
        chipType.setFullDescription(chipTypeDTO.getFullDescription());
        chipType.setCost(chipTypeDTO.getCost());
        chipType.setPrice(chipTypeDTO.getPrice());
        chipType.setDiscountPercent(chipTypeDTO.getDiscountPercent());
        chipType.setWeight(chipTypeDTO.getWeight());


        return chipType;
    }

    public static ChipTypeDTO chipTypeToChipTypeDTO(ChipType chipType) {
        List<ChipTypeDetailDTO> chipTypeDetails = new ArrayList<>();
        List<ChipTypeImageDTO> images = new ArrayList<>();

        chipType.getDetails().forEach(chipTypeDetail ->
                chipTypeDetails.add(new ChipTypeDetailDTO(chipTypeDetail.getId(),
                        chipTypeDetail.getName(),
                        chipTypeDetail.getValue(),
                        null,
                        chipType.getId())));

        chipType.getImages().forEach(chipTypeImage ->
                images.add(new ChipTypeImageDTO(chipTypeImage.getId(),
                        chipTypeImage.getName(),
                        null,
                        chipType.getId())));

        ChipTypeDTO chipTypeDTO = new ChipTypeDTO();
        chipTypeDTO.setId(chipType.getId());
        chipTypeDTO.setName(chipType.getName());
        chipTypeDTO.setAlias(chipType.getAlias());
        chipTypeDTO.setShortDescription(chipType.getShortDescription());
        chipTypeDTO.setFullDescription(chipType.getFullDescription());
        chipTypeDTO.setCreatedTime(chipType.getCreatedTime());
        chipTypeDTO.setUpdatedTime(chipType.getUpdatedTime());
        chipTypeDTO.setEnabled(chipType.getEnabled());
        chipTypeDTO.setInStock(chipType.getInStock());
        chipTypeDTO.setCost(chipType.getCost());
        chipTypeDTO.setPrice(chipType.getPrice());
        chipTypeDTO.setDiscountPercent(chipType.getDiscountPercent());
        chipTypeDTO.setWeight(chipType.getWeight());
        chipTypeDTO.setMainImage(chipType.getMainImage());
        chipTypeDTO.setChipTypeDetails(chipTypeDetails);
        chipTypeDTO.setImages(images);

        return chipTypeDTO;
    }
}
