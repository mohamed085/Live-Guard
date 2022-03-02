package com.liveguard.service.serviceImp;

import com.liveguard.domain.ChipType;
import com.liveguard.dto.ChipTypeDTO;
import com.liveguard.exception.BusinessException;
import com.liveguard.mapper.ChipTypeMapper;
import com.liveguard.repository.ChipTypeRepository;
import com.liveguard.service.ChipTypeService;
import com.liveguard.util.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class ChipTypeServiceImp implements ChipTypeService {

    private final ChipTypeRepository chipTypeRepository;

    public ChipTypeServiceImp(ChipTypeRepository chipTypeRepository) {
        this.chipTypeRepository = chipTypeRepository;
    }

    @Override
    public List<ChipType> findAll() {
        log.debug("ChipTypeService | findAll");

        return StreamSupport
                .stream(chipTypeRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public ChipType findById(Long id) {
        log.debug("ChipTypeService | findById | id: " + id);

        return chipTypeRepository.findById(id)
                .orElseThrow(() -> new BusinessException("This chip type not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public ChipType add(ChipTypeDTO chipTypeDTO) throws IOException {
        log.debug("ChipTypeService | add: " + chipTypeDTO.toString());
        ChipType chipType = ChipTypeMapper.chipTypeDTOToChipType(chipTypeDTO);

        ChipType savedChipType;
        chipType.setCreatedTime(LocalDateTime.now());
        chipType.setUpdatedTime(LocalDateTime.now());
        chipType.setEnabled(true);
        chipType.setInStock(true);
        chipType.setDiscountPercent(0.0F);


        if (!chipTypeDTO.getMainImageFile().isEmpty()) {
            log.debug("ChipTypeService | add | chipDTO has file");

            MultipartFile multipartFile = chipTypeDTO.getMainImageFile();
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            log.debug("ChipTypeService | add | file name: " + fileName);

            try {
                savedChipType = chipTypeRepository.save(chipType);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            savedChipType.setMainImage("/chip-type-photos/" + savedChipType.getId() + "/" +fileName);

            try {
                savedChipType =chipTypeRepository.save(savedChipType);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String uploadDir = "chip-type-photos/" + savedChipType.getId();

            log.debug("ChipTypeService | add | savedChipType : " + savedChipType.toString());
            log.debug("ChipTypeService | add | uploadDir : " + uploadDir);

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        } else {
            log.debug("ChipTypeService | add | chipTypeDTO not has file");

            savedChipType = chipTypeRepository.save(chipType);
        }


        return savedChipType;
    }
}
