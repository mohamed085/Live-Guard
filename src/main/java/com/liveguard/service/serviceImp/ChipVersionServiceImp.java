package com.liveguard.service.serviceImp;

import com.liveguard.domain.ChipVersion;
import com.liveguard.domain.ChipVersionDetail;
import com.liveguard.dto.ChipVersionDTO;
import com.liveguard.dto.ChipVersionDetailDTO;
import com.liveguard.exception.BusinessException;
import com.liveguard.mapper.ChipVersionMapper;
import com.liveguard.payload.UpdateChipVersionDescriptionRequest;
import com.liveguard.payload.UpdateChipVersionOverviewRequest;
import com.liveguard.payload.UpdateChipVersionShippingRequest;
import com.liveguard.repository.ChipVersionDetailRepository;
import com.liveguard.repository.ChipVersionRepository;
import com.liveguard.service.ChipVersionService;
import com.liveguard.util.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ChipVersionServiceImp implements ChipVersionService {

    private final ChipVersionRepository chipVersionRepository;
    private final ChipVersionDetailRepository chipVersionDetailRepository;

    public ChipVersionServiceImp(ChipVersionRepository chipVersionRepository, ChipVersionDetailRepository chipVersionDetailRepository) {
        this.chipVersionRepository = chipVersionRepository;
        this.chipVersionDetailRepository = chipVersionDetailRepository;
    }

    @Override
    public ChipVersionDTO add(ChipVersionDTO chipVersionDTO) {
        log.debug("ChipVersionService | add | chipVersionDTO: " + chipVersionDTO.toString());

        try {
            ChipVersion chipVersion = ChipVersionMapper.chipVersionDTOToChipVersion(chipVersionDTO);
            chipVersion.setCreatedTime(LocalDateTime.now());
            chipVersion.setUpdatedTime(LocalDateTime.now());
            chipVersion.setInStock(true);
            chipVersion.setEnabled(true);

            if (chipVersionDTO.getMainImageFile() != null) {
                log.debug("ChipVersionService | add | chipTypeDTO has image file");

                MultipartFile multipartFile = chipVersionDTO.getMainImageFile();
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                log.debug("ChipVersionService | add | file name: " + fileName);

                chipVersion = chipVersionRepository.save(chipVersion);

                chipVersion.setMainImage("/chip-version-photos/" + chipVersion.getId() + "/" + fileName);
                String uploadDir = "chip-version-photos/" + chipVersion.getId();

                log.debug("ChipVersionService | add | savedChipType : " + chipVersion.toString());
                log.debug("ChipVersionService | add | uploadDir : " + uploadDir);

                FileUploadUtil.cleanDir(uploadDir);
                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

            } else {
                log.debug("ChipVersionService | add | chipTypeDTO not has image file");

            }

            ChipVersion savedChipVersion = chipVersionRepository.save(chipVersion);

            return ChipVersionMapper.chipVersionToChipVersionDTO(savedChipVersion);
        } catch (Exception e) {
            log.error("ChipTypeController | add | error");
            e.printStackTrace();
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ChipVersionDTO findById(Long id) {
        log.debug("ChipVersionService | findById | id: " + id);

        try {
            ChipVersion chipVersion = chipVersionRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("This chip type not found", HttpStatus.NOT_FOUND));

            return ChipVersionMapper.chipVersionToChipVersionDTO(chipVersion);
        } catch (Exception e) {
            log.error("ChipTypeController | findById | error");
            e.printStackTrace();
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<ChipVersionDTO> findAll() {
        log.debug("ChipVersionService | findAll");

        try {
            List<ChipVersion> chipVersions = chipVersionRepository.findAll();
            List<ChipVersionDTO> chipVersionDTOs = new ArrayList<>();

            chipVersions.forEach(chipVersion -> chipVersionDTOs.add(ChipVersionMapper.chipVersionToChipVersionDTO(chipVersion)));
            return chipVersionDTOs;
        } catch (Exception e) {
            log.error("ChipVersionService | findAll | error: " + e.getMessage());
            e.printStackTrace();
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<ChipVersionDTO> findAllEnable() {
        log.debug("ChipVersionService | findAllEnable");

        try {
            List<ChipVersion> chipVersions = chipVersionRepository.findAllByEnabledTrue();
            List<ChipVersionDTO> chipVersionDTOs = new ArrayList<>();

            chipVersions.forEach(chipVersion -> chipVersionDTOs.add(ChipVersionMapper.chipVersionToChipVersionDTO(chipVersion)));
            return chipVersionDTOs;
        } catch (Exception e) {
            log.error("ChipVersionService | findAllEnable | error: " + e.getMessage());
            e.printStackTrace();
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    @Transactional
    public void updateEnabledStatus(Long id, Boolean status) {
        log.debug("ChipVersionService | updateEnabledStatus | chip id: " + id);
        log.debug("ChipVersionService | updateEnabledStatus | status: " + status);

        try {
            chipVersionRepository.updateEnabledStatus(id, status);
        } catch (Exception e) {
            log.error("ChipVersionService | updateEnabledStatus | error");
            e.printStackTrace();
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void updateInStockStatus(Long id, Boolean status) {
        log.debug("ChipVersionService | updateInStockStatus | chip id: " + id);
        log.debug("ChipVersionService | updateInStockStatus | status: " + status);

        try {
            chipVersionRepository.updateInStockStatus(id, status);
        } catch (Exception e) {
            log.error("ChipVersionService | updateInStockStatus | error");
            e.printStackTrace();
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void updateMainImage(Long id, MultipartFile multipartFile) {
        log.debug("ChipVersionService | updateMainImage | id: " + id);
        log.debug("ChipVersionService | updateMainImage | multipartFile: " + multipartFile.getOriginalFilename());

        try {
            ChipVersion chipVersion = chipVersionRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("Chip not found", HttpStatus.NOT_FOUND));


            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            log.debug("ChipVersionService | updateMainImage | file name: " + fileName);

            String uploadDir = "chip-type-photos/" + chipVersion.getId();

            log.debug("CChipVersionService | updateMainImage | savedChipType : " + chipVersion.toString());
            log.debug("ChipVersionService | updateMainImage | uploadDir : " + uploadDir);

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

            chipVersionRepository.updateMainImage(chipVersion.getId(), "/chip-version-photos/" + chipVersion.getId() + "/" + fileName);
        } catch (Exception e) {
            log.error("ChipVersionService | updateMainImage | error");
            e.printStackTrace();
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void updateOverview(Long id, UpdateChipVersionOverviewRequest chipVersionOverview) {
        log.debug("ChipVersionService | updateMainImage | id: " + id);
        log.debug("ChipVersionService | updateMainImage | chipVersionOverview: " + chipVersionOverview.toString());

        try {
            ChipVersion chipVersion = chipVersionRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("Chip not found", HttpStatus.NOT_FOUND));

            chipVersion.setName(chipVersionOverview.getName());
            chipVersion.setAlias(chipVersionOverview.getAlias());
            chipVersion.setCost(chipVersionOverview.getCost());
            chipVersion.setPrice(chipVersionOverview.getPrice());
            chipVersion.setDiscountPercent(chipVersionOverview.getDiscountPercent());
            chipVersion.setInStock(chipVersionOverview.getInStock());
            chipVersion.setEnabled(chipVersionOverview.getEnabled());
            chipVersion.setReviewedByCustomer(chipVersionOverview.isReviewedByCustomer());
            chipVersion.setCustomerCanReview(chipVersionOverview.isCustomerCanReview());

            chipVersionRepository.save(chipVersion);

        } catch (Exception e) {
            log.error("ChipVersionService | updateInStockStatus | error");
            e.printStackTrace();
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void updateDescription(Long id, UpdateChipVersionDescriptionRequest chipVersionDescription) {
        log.debug("ChipVersionService | updateMainImage | id: " + id);
        log.debug("ChipVersionService | updateMainImage | chipVersionDescription: " + chipVersionDescription.toString());

        try {
            ChipVersion chipVersion = chipVersionRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("Chip not found", HttpStatus.NOT_FOUND));

            chipVersionRepository.updateDescription(chipVersion.getId(), chipVersionDescription.getShortDescription(), chipVersionDescription.getFullDescription());

        } catch (Exception e) {
            log.error("ChipVersionService | updateInStockStatus | error");
            e.printStackTrace();
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void updateDetails(Long id, List<ChipVersionDetailDTO> chipVersionDetailDTOs) {
        log.debug("ChipVersionService | updateMainImage | id: " + id);
        log.debug("ChipVersionService | updateMainImage | chipVersionDetailDTOs: " + chipVersionDetailDTOs.toString());

        try {
            ChipVersion chipVersion = chipVersionRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("Chip not found", HttpStatus.NOT_FOUND));

            chipVersionDetailRepository.deleteAllByChipVersionId(chipVersion.getId());
            List<ChipVersionDetail> details = new ArrayList<>();
            chipVersionDetailDTOs.forEach(detailDTO -> {
                details.add(new ChipVersionDetail(detailDTO.getName(), detailDTO.getValue(), chipVersion));
            });

            chipVersionDetailRepository.saveAll(details);
        } catch (Exception e) {
            log.error("ChipVersionService | updateInStockStatus | error");
            e.printStackTrace();
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void updateShipping(Long id, UpdateChipVersionShippingRequest chipVersionShipping) {
        log.debug("ChipVersionService | updateMainImage | id: " + id);
        log.debug("ChipVersionService | updateMainImage | chipVersionShipping: " + chipVersionShipping.toString());

        try {
            ChipVersion chipVersion = chipVersionRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("Chip not found", HttpStatus.NOT_FOUND));


        } catch (Exception e) {
            log.error("ChipVersionService | updateInStockStatus | error");
            e.printStackTrace();
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
