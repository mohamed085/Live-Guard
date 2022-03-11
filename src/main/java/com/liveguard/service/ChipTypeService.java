package com.liveguard.service;


import com.liveguard.domain.ChipType;
import com.liveguard.dto.ChipTypeDTO;
import com.liveguard.dto.ChipTypeDetailDTO;
import com.liveguard.payload.UpdateChipTypeDescriptionRequest;
import com.liveguard.payload.UpdateChipTypeOverviewRequest;
import com.liveguard.payload.UpdateChipTypeShippingRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ChipTypeService {

    List<ChipType> findAll();

    List<ChipType> findAllEnable();

    ChipType findById(Long id);

    ChipType add(ChipTypeDTO chipTypeDTO);

    void updateChipTypeMainImage(Long id, MultipartFile multipartFile);

    void updateChipTypeOverview(Long id, UpdateChipTypeOverviewRequest chipTypeOverview);

    void updateChipTypeDescription(Long id, UpdateChipTypeDescriptionRequest chipTypeDescription);

    void updateChipTypeDetails(Long id, List<ChipTypeDetailDTO> chipTypeDetailDTOs);

    void updateChipTypeShipping(Long id, UpdateChipTypeShippingRequest chipTypeShipping);

    void updateEnabledStatus(Long id, Boolean status);

    void updateInStockStatus(Long id, Boolean status);

}
