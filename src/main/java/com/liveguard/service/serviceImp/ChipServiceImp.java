package com.liveguard.service.serviceImp;

import com.liveguard.domain.Chip;
import com.liveguard.domain.ChipAssociatedDetails;
import com.liveguard.domain.User;
import com.liveguard.dto.ChipAssociatedDetailsDTO;
import com.liveguard.dto.ChipDTO;
import com.liveguard.exception.BusinessException;
import com.liveguard.mapper.ChipAssociatedDetailsMapper;
import com.liveguard.mapper.ChipMapper;
import com.liveguard.repository.ChipRepository;
import com.liveguard.service.ChipService;
import com.liveguard.service.ChipTypeService;
import com.liveguard.util.FileUploadUtil;
import com.liveguard.util.GenerateCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class ChipServiceImp implements ChipService {

    private final ChipRepository chipRepository;
    private final ChipTypeService chipTypeService;

    public ChipServiceImp(ChipRepository chipRepository, ChipTypeService chipTypeService) {
        this.chipRepository = chipRepository;
        this.chipTypeService = chipTypeService;
    }

    @Override
    public List<Chip> findAll() {
        log.debug("ChipService | findAll");

        return StreamSupport
                .stream(chipRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Chip findById(Long id) {
        log.debug("ChipService | findById | id: " + id);

        return chipRepository.findById(id)
                .orElseThrow(() -> new BusinessException("This chip not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public Chip add(ChipDTO chipDTO) throws IOException {
        log.debug("ChipService | add | chipDTO name: " + chipDTO.getName());
        Chip chip = ChipMapper.chipDTOToChip(chipDTO);

        chip.setChipType(chipTypeService.findById(chipDTO.getChipTypeId()));
        chip.setPassword(String.valueOf(GenerateCodeUtil.generateRandomDigits(12)));

        Chip savedChip;

        if (!chipDTO.getPhotoFile().isEmpty()) {
            log.debug("ChipService | add | chipDTO has file");

            MultipartFile multipartFile = chipDTO.getPhotoFile();
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            log.debug("ChipService | add | file name: " + fileName);

            savedChip = save(chip);
            chip.setPhoto("/chip-photos/" + savedChip.getId() + "/" +fileName);
            savedChip = save(chip);

            String uploadDir = "chip-photos/" + savedChip.getId();

            log.debug("ChipService | add | savedChip : " + savedChip.toString());
            log.debug("ChipService | add | uploadDir : " + uploadDir);

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        } else {
            log.debug("ChipService | add | chipDTO not has file");

            savedChip = save(chip);
        }

        return savedChip;
    }

    @Override
    public Chip save(Chip chip) {
        return chipRepository.save(chip);
    }

    @Override
    public List<Chip> getChipsByType(Long chipTypeId) {
        log.debug("ChipService | getChipsByType | chipTypeId: " + chipTypeId);

        return chipRepository.findByChipTypeId(chipTypeId);
    }

    @Override
    public ChipAssociatedDetails addChipAssociatedDetails(Long chipId, ChipAssociatedDetailsDTO chipAssociatedDetailsDTO) throws IOException {
        return new ChipAssociatedDetails();
    }
}
