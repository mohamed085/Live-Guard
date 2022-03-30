package com.liveguard.mapper;

import com.liveguard.domain.ChipVersion;
import com.liveguard.domain.ChipVersionDetail;
import com.liveguard.domain.ChipVersionImage;
import com.liveguard.dto.ChipVersionDTO;
import com.liveguard.dto.ChipVersionDetailDTO;
import com.liveguard.dto.ChipVersionImageDTO;
import com.liveguard.util.DateConverterUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class ChipVersionMapper {

    public static ChipVersion chipVersionDTOToChipVersion(ChipVersionDTO chipVersionDTO) {
        log.debug("ChipVersionMapper | chipVersionDTOToChipVersion | chipVersionDTO: " + chipVersionDTO.toString());
        ChipVersion chipVersion = new ChipVersion();

        List<ChipVersionImage> images = new ArrayList<>();
        List<ChipVersionDetail> details = new ArrayList<>();

        chipVersion.setName(chipVersionDTO.getName());
        chipVersion.setAlias(chipVersionDTO.getAlias());
        chipVersion.setShortDescription(chipVersionDTO.getShortDescription());
        chipVersion.setFullDescription(chipVersionDTO.getFullDescription());
        chipVersion.setEnabled(chipVersionDTO.getEnabled());
        chipVersion.setInStock(chipVersionDTO.getInStock());
        chipVersion.setCost(chipVersionDTO.getCost());
        chipVersion.setPrice(chipVersionDTO.getPrice());
        chipVersion.setDiscountPercent(chipVersionDTO.getDiscountPercent());
        chipVersion.setWeight(chipVersionDTO.getWeight());
        chipVersion.setMainImage(chipVersionDTO.getMainImage());
        chipVersion.setReviewCount(chipVersionDTO.getReviewCount());
        chipVersion.setAverageRating(chipVersionDTO.getAverageRating());
        chipVersion.setCustomerCanReview(chipVersionDTO.isCustomerCanReview());
        chipVersion.setReviewedByCustomer(chipVersionDTO.isReviewedByCustomer());

        return chipVersion;
    }

    public static ChipVersionDTO chipVersionToChipVersionDTO(ChipVersion chipVersion) {
        log.debug("ChipVersionMapper | chipVersionToChipVersionDTO | chipVersion: " + chipVersion.toString());

        ChipVersionDTO chipVersionDTO = new ChipVersionDTO();

        List<ChipVersionImageDTO> images = new ArrayList<>();
        List<ChipVersionDetailDTO> details = new ArrayList<>();

        chipVersion.getImages().forEach(image -> images.add(new ChipVersionImageDTO(image.getId(), image.getPhoto())));
        chipVersion.getDetails().forEach(detail -> details.add(new ChipVersionDetailDTO(detail.getId(), detail.getName(), detail.getValue())));

        chipVersionDTO.setId(chipVersion.getId());
        chipVersionDTO.setName(chipVersion.getName());
        chipVersionDTO.setAlias(chipVersion.getAlias());
        chipVersionDTO.setShortDescription(chipVersion.getShortDescription());
        chipVersionDTO.setFullDescription(chipVersion.getFullDescription());
        chipVersionDTO.setCreatedTime(chipVersion.getCreatedTime());
        chipVersionDTO.setUpdatedTime(chipVersion.getUpdatedTime());
        chipVersionDTO.setEnabled(chipVersion.getEnabled());
        chipVersionDTO.setInStock(chipVersion.getInStock());
        chipVersionDTO.setCost(chipVersion.getCost());
        chipVersionDTO.setPrice(chipVersion.getPrice());
        chipVersionDTO.setDiscountPercent(chipVersion.getDiscountPercent());
        chipVersionDTO.setDiscountPrice(chipVersion.getDiscountPrice());
        chipVersionDTO.setMainImage(chipVersion.getMainImage());
        chipVersionDTO.setReviewCount(chipVersion.getReviewCount());
        chipVersionDTO.setAverageRating(chipVersion.getAverageRating());
        chipVersionDTO.setCustomerCanReview(chipVersion.isCustomerCanReview());
        chipVersionDTO.setReviewedByCustomer(chipVersion.isReviewedByCustomer());
        chipVersionDTO.setDetails(details);
        chipVersionDTO.setImages(images);

        return chipVersionDTO;
    }
}
