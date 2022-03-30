package com.liveguard.service;

import com.liveguard.dto.ChipDTO;
import com.liveguard.payload.ApiResponse;
import com.liveguard.payload.UpdateChipDetailsRequest;
import org.springframework.web.multipart.MultipartFile;

public interface ChipService {

    ChipDTO add(ChipDTO chipDTO);

    ChipDTO findById(Long id);

    ApiResponse addNewChipToUser(Long chipId, String chipPassword);

    ApiResponse updatePhoto(Long chipId, MultipartFile multipartFile);

    ApiResponse updateChipDetails(Long chipId, UpdateChipDetailsRequest updateChipDetailsRequest);

    ApiResponse addNewUser(Long chipId, Long userId);
}
