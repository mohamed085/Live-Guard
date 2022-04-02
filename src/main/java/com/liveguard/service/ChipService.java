package com.liveguard.service;

import com.liveguard.domain.Chip;
import com.liveguard.dto.ChipDTO;
import com.liveguard.dto.SimpleChipDTO;
import com.liveguard.payload.ApiResponse;
import com.liveguard.payload.UpdateChipDetailsRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ChipService {

    ChipDTO add(ChipDTO chipDTO);

    ChipDTO findById(Long id);

    List<ChipDTO> findAll();

    Page<ChipDTO> findAllByPage(int pageNum, String sortField, String sortDir, String keyword);

    List<ChipDTO> findAllByChipVersion(Long id);

    ApiResponse addNewChipToUser(Long chipId, String chipPassword);

    ApiResponse updatePhoto(Long chipId, MultipartFile multipartFile);

    ApiResponse updateChipDetails(Long chipId, UpdateChipDetailsRequest updateChipDetailsRequest);

    ApiResponse addNewUser(Long chipId, Long userId);

    List<SimpleChipDTO> findAllUserChips();
}
