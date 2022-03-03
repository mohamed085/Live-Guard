package com.liveguard.service.serviceImp;

import com.liveguard.domain.Chip;
import com.liveguard.domain.ChipAssociatedDetails;
import com.liveguard.domain.User;
import com.liveguard.dto.ChipAssociatedDetailsDTO;
import com.liveguard.dto.ChipDTO;
import com.liveguard.exception.BusinessException;
import com.liveguard.mapper.ChipMapper;
import com.liveguard.payload.ApiResponse;
import com.liveguard.repository.ChipRepository;
import com.liveguard.service.*;
import com.liveguard.util.FileUploadUtil;
import com.liveguard.util.GenerateCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class ChipServiceImp implements ChipService {

    private final ChipRepository chipRepository;
    private final ChipTypeService chipTypeService;
    private final AccountService accountService;
    private final UserService userService;
    private final UsersTasksMuteService usersTasksMuteService;

    public ChipServiceImp(ChipRepository chipRepository, ChipTypeService chipTypeService,
                          AccountService accountService, UserService userService,
                          UsersTasksMuteService usersTasksMuteService) {
        this.chipRepository = chipRepository;
        this.chipTypeService = chipTypeService;
        this.accountService = accountService;
        this.userService = userService;
        this.usersTasksMuteService = usersTasksMuteService;
    }

    @Override
    public List<Chip> findAll() {
        log.debug("ChipService | findAll");

        try {
            return chipRepository.findAll();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Chip findById(Long id) {
        log.debug("ChipService | findById | id: " + id);

        try {
            return chipRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("This chip not found", HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Chip add(ChipDTO chipDTO) throws IOException {
        log.debug("ChipService | add | chipDTO name: " + chipDTO.getName());
        Chip chip = ChipMapper.chipDTOToChip(chipDTO);

        chip.setChipType(chipTypeService.findById(chipDTO.getChipTypeId()));
        chip.setPassword(RandomStringUtils.randomAlphanumeric(9));

        Chip savedChip;

        if (chipDTO.getPhotoFile() != null || !chipDTO.getPhotoFile().isEmpty()) {
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
        try {
            return chipRepository.save(chip);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Chip> getChipsByType(Long chipTypeId) {
        log.debug("ChipService | getChipsByType | chipTypeId: " + chipTypeId);

        try {
            return chipRepository.findByChipTypeId(chipTypeId);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Set<Chip> getUserChips() {
        User user = accountService.getAuthenticatedAccount();
        log.debug("ChipService | getUserChips | user: " + user.getEmail());

        return user.getChips();
    }

    @Override
    public ApiResponse addNewChipToUser(Long chipId, String chipPassword) {
        log.debug("ChipService | addNewChipToUser | chip id: " + chipId);
        Chip chip = findById(chipId);

        User user = accountService.getAuthenticatedAccount();
        log.debug("ChipService | addNewChipToUser | user: " + user.getEmail());

        if (chip.getPassword().equals(chipPassword)) {
            user.getChips().add(chip);

            try {
                userService.save(user);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            usersTasksMuteService.addUsersMuteDefaultToAddNewChipToUser(chip, user);
            return new ApiResponse(true, "Chip add successfully");
        } else {
            throw new BusinessException("Chip not add successfully, password incorrect", HttpStatus.BAD_REQUEST);
        }
    }
}
