package com.liveguard.service;

import com.liveguard.domain.ChipVersion;
import com.liveguard.dto.ChipVersionDTO;
import com.liveguard.dto.ChipVersionDetailDTO;
import com.liveguard.payload.ApiResponse;
import com.liveguard.payload.UpdateChipVersionDescriptionRequest;
import com.liveguard.payload.UpdateChipVersionOverviewRequest;
import com.liveguard.payload.UpdateChipVersionShippingRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ChipVersionService {

    ChipVersion add(ChipVersionDTO chipVersionDTO);

    ChipVersion findById(Long id);

    List<ChipVersion> findAll();

    List<ChipVersion> findAllEnable();

    void updateEnabledStatus(Long id, Boolean status);

    void updateInStockStatus(Long id, Boolean status);

    void updateMainImage(Long id, MultipartFile multipartFile);

    void updateOverview(Long id, UpdateChipVersionOverviewRequest chipVersionOverview);

    void updateDescription(Long id, UpdateChipVersionDescriptionRequest chipVersionDescription);

    void updateDetails(Long id, List<ChipVersionDetailDTO> chipVersionDetailDTOs);

    void updateShipping(Long id, UpdateChipVersionShippingRequest chipVersionShipping);

    void updateReviewCountAndAverageRating(Long id);
}
