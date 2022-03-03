package com.liveguard.service.serviceImp;

import com.liveguard.domain.ChipType;
import com.liveguard.domain.ChipTypeDetail;
import com.liveguard.dto.ChipTypeDTO;
import com.liveguard.dto.ChipTypeDetailDTO;
import com.liveguard.exception.BusinessException;
import com.liveguard.mapper.ChipTypeMapper;
import com.liveguard.payload.UpdateChipTypeDescriptionRequest;
import com.liveguard.payload.UpdateChipTypeOverviewRequest;
import com.liveguard.payload.UpdateChipTypeShippingRequest;
import com.liveguard.repository.ChipTypeDetailRepository;
import com.liveguard.repository.ChipTypeRepository;
import com.liveguard.service.ChipTypeService;
import com.liveguard.util.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class ChipTypeServiceImp implements ChipTypeService {

    private final ChipTypeRepository chipTypeRepository;
    private final ChipTypeDetailRepository chipTypeDetailRepository;

    public ChipTypeServiceImp(ChipTypeRepository chipTypeRepository, ChipTypeDetailRepository chipTypeDetailRepository) {
        this.chipTypeRepository = chipTypeRepository;
        this.chipTypeDetailRepository = chipTypeDetailRepository;
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
    public ChipType add(ChipTypeDTO chipTypeDTO) {
        log.debug("ChipTypeService | add: " + chipTypeDTO.toString());
        ChipType chipType = ChipTypeMapper.chipTypeDTOToChipType(chipTypeDTO);

        ChipType savedChipType;
        chipType.setCreatedTime(LocalDateTime.now());
        chipType.setUpdatedTime(LocalDateTime.now());
        chipType.setInStock(true);
        chipType.setEnabled(true);


        if (chipTypeDTO.getMainImageFile() != null) {
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
                savedChipType = chipTypeRepository.save(savedChipType);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String uploadDir = "chip-type-photos/" + savedChipType.getId();

            log.debug("ChipTypeService | add | savedChipType : " + savedChipType.toString());
            log.debug("ChipTypeService | add | uploadDir : " + uploadDir);

            FileUploadUtil.cleanDir(uploadDir);
            try {
                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            } catch (IOException e) {
                throw new BusinessException("Failed to save chip photo", HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            log.debug("ChipTypeService | add | chipTypeDTO not has file");

            savedChipType = chipTypeRepository.save(chipType);
        }

        return savedChipType;
    }

    @Override
    public void updateChipTypeMainImage(Long id, MultipartFile multipartFile) {
        log.debug("ChipTypeService | updateChipTypeMainImage | id: " + id);
        log.debug("ChipTypeService | updateChipTypeMainImage | multipartFile: " + multipartFile.getOriginalFilename());

        ChipType savedChipType = findById(id);

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        log.debug("ChipTypeService | updateChipTypeMainImage | file name: " + fileName);

        savedChipType.setMainImage("/chip-type-photos/" + savedChipType.getId() + "/" +fileName);

        try {
            savedChipType = chipTypeRepository.save(savedChipType);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String uploadDir = "chip-type-photos/" + savedChipType.getId();

        log.debug("ChipTypeService | updateChipTypeMainImage | savedChipType : " + savedChipType.toString());
        log.debug("ChipTypeService | updateChipTypeMainImage | uploadDir : " + uploadDir);

        FileUploadUtil.cleanDir(uploadDir);
        try {
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } catch (IOException e) {
            throw new BusinessException("Failed to save chip photo", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void updateChipTypeOverview(Long id, UpdateChipTypeOverviewRequest chipTypeOverview) {
        log.debug("ChipTypeService | updateChipTypeOverview | id: " + id);
        ChipType savedChipType = findById(id);

        savedChipType.setName(chipTypeOverview.getName());
        savedChipType.setAlias(chipTypeOverview.getAlias());
        savedChipType.setCost(chipTypeOverview.getCost());
        savedChipType.setPrice(chipTypeOverview.getPrice());
        savedChipType.setInStock(chipTypeOverview.getInStock());
        savedChipType.setEnabled(chipTypeOverview.getEnabled());

        try {
            chipTypeRepository.save(savedChipType);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void updateChipTypeDescription(Long id, UpdateChipTypeDescriptionRequest chipTypeDescription) {
        log.debug("ChipTypeService | updateChipTypeDescription | id: " + id);
        ChipType savedChipType = findById(id);

        savedChipType.setShortDescription(chipTypeDescription.getShortDescription());
        savedChipType.setFullDescription(chipTypeDescription.getFullDescription());

        try {
            chipTypeRepository.save(savedChipType);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void updateChipTypeDetails(Long id, List<ChipTypeDetailDTO> chipTypeDetailDTOs) {
        log.debug("ChipTypeService | updateChipTypeShipping | id: " + id);
        ChipType savedChipType = findById(id);
        savedChipType.setDetails(null);

        try {
            chipTypeDetailRepository.deleteAllByChipTypeId(savedChipType.getId());

            chipTypeDetailDTOs.forEach(chipTypeDetailDTO -> {
                ChipTypeDetail chipTypeDetail = new ChipTypeDetail(chipTypeDetailDTO.getName(), chipTypeDetailDTO.getValue(), savedChipType);
                chipTypeDetailRepository.save(chipTypeDetail);
            });
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void updateChipTypeShipping(Long id, UpdateChipTypeShippingRequest chipTypeShipping) {
        log.debug("ChipTypeService | updateChipTypeShipping | id: " + id);
        ChipType savedChipType = findById(id);

        savedChipType.setWeight(chipTypeShipping.getWeight());

        try {
            chipTypeRepository.save(savedChipType);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
