package com.liveguard.service.serviceImp;

import com.liveguard.domain.Chip;
import com.liveguard.domain.ChipAssociatedDetails;
import com.liveguard.domain.User;
import com.liveguard.dto.ChipAssociatedDetailsDTO;
import com.liveguard.exception.BusinessException;
import com.liveguard.mapper.ChipAssociatedDetailsMapper;
import com.liveguard.repository.ChipAssociatedDetailsRepository;
import com.liveguard.service.AccountService;
import com.liveguard.service.ChipAssociatedDetailsService;
import com.liveguard.service.ChipService;
import com.liveguard.util.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class ChipAssociatedDetailsServiceImp implements ChipAssociatedDetailsService {

    private final AccountService accountService;
    private final ChipService chipService;
    private final ChipAssociatedDetailsRepository chipAssociatedDetailsRepository;

    public ChipAssociatedDetailsServiceImp(AccountService accountService, ChipService chipService, ChipAssociatedDetailsRepository chipAssociatedDetailsRepository) {
        this.accountService = accountService;
        this.chipService = chipService;
        this.chipAssociatedDetailsRepository = chipAssociatedDetailsRepository;
    }

    @Override
    public ChipAssociatedDetails addChipAssociatedDetails(Long chipId, ChipAssociatedDetailsDTO chipAssociatedDetailsDTO) {
        log.debug("ChipAssociatedDetailsService | addChipAssociatedDetails | chipId: " + chipId);
        log.debug("ChipAssociatedDetailsService | addChipAssociatedDetails | chipAssociatedDetails: " + chipAssociatedDetailsDTO.getName());

        log.debug("ChipAssociatedDetailsService | addChipAssociatedDetails | get user authenticated account");
        User user = accountService.getAuthenticatedAccount();
        log.debug("ChipAssociatedDetailsService | addChipAssociatedDetails | user email: " + user.getEmail());

        Chip chip = chipService.findById(chipId);
        log.debug("ChipAssociatedDetailsService | addChipAssociatedDetails | chip: " + chip.getName());

        ChipAssociatedDetails chipAssociatedDetails = ChipAssociatedDetailsMapper.ChipAssociatedDetailsDTOToChipAssociatedDetails(chipAssociatedDetailsDTO);
        chipAssociatedDetails.setAddByUser(user);

        ChipAssociatedDetails savedChipAssociatedDetails;
        if (!chipAssociatedDetailsDTO.getPhotoFile().isEmpty()) {
            log.debug("ChipAssociatedDetailsService | addChipAssociatedDetails | chipAssociatedDetailsDTO has file");

            MultipartFile multipartFile = chipAssociatedDetailsDTO.getPhotoFile();
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            log.debug("ChipAssociatedDetailsService | addChipAssociatedDetails | file name: " + fileName);

            try {
                savedChipAssociatedDetails = chipAssociatedDetailsRepository.save(chipAssociatedDetails);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            savedChipAssociatedDetails.setPhoto("/chip-associated_details-photos/" + savedChipAssociatedDetails.getId() + "/" +fileName);

            try {
                savedChipAssociatedDetails = chipAssociatedDetailsRepository.save(savedChipAssociatedDetails);
            }  catch (Exception e) {
                throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String uploadDir = "chip-associated_details-photos/" + savedChipAssociatedDetails.getId();

            log.debug("ChipAssociatedDetailsService | addChipAssociatedDetails | savedChipAssociatedDetails : " + savedChipAssociatedDetails.toString());
            log.debug("ChipAssociatedDetailsService | addChipAssociatedDetails | uploadDir : " + uploadDir);

            FileUploadUtil.cleanDir(uploadDir);
            try {
                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            } catch (IOException e) {
                throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            log.debug("ChipAssociatedDetailsService | addChipAssociatedDetails | savedChipAssociatedDetails not has file");

           try {
               savedChipAssociatedDetails = chipAssociatedDetailsRepository.save(chipAssociatedDetails);
           }  catch (Exception e) {
               throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
           }
        }

        chip.setChipAssociatedDetails(savedChipAssociatedDetails);
        chipService.save(chip);

        return savedChipAssociatedDetails;
    }
}
