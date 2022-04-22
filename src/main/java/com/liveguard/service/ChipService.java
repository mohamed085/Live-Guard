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

    Chip add(ChipDTO chipDTO);

    Chip findById(Long id);

    List<Chip> findAll();

    Page<Chip> findAllByPage(int pageNum, String sortField, String sortDir, String keyword);

    void deleteById(Long id);

    List<Chip> findAllByChipVersion(Long id);


    void updateUsedStatus(Long chipId, Boolean used);

    void addNewChipToUser(Long chipId, String chipPassword);

    void updatePhoto(Long chipId, MultipartFile multipartFile);

    void updateChipDetails(Long chipId, UpdateChipDetailsRequest updateChipDetailsRequest);

    void addNewUser(Long chipId, Long userId);

    void removeUser(Long chipId, Long userId);

    List<Chip> findAllAuthenticatedUserChips();
}
