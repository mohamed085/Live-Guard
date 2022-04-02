package com.liveguard.service.serviceImp;

import com.liveguard.domain.*;
import com.liveguard.dto.ChipDTO;
import com.liveguard.dto.SimpleChipDTO;
import com.liveguard.exception.BusinessException;
import com.liveguard.mapper.ChipMapper;
import com.liveguard.payload.ApiResponse;
import com.liveguard.payload.UpdateChipDetailsRequest;
import com.liveguard.repository.*;
import com.liveguard.service.AccountService;
import com.liveguard.service.ChipService;
import com.liveguard.service.UserTaskMuteService;
import com.liveguard.util.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
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
public class ChipServiceImp implements ChipService {

    private final ChipRepository chipRepository;
    private final ChipVersionRepository chipVersionRepository;
    private final AccountService accountService;
    private final ChipUserRepository chipUserRepository;
    private final UserRepository userRepository;
    private final ChipDetailRepository chipDetailRepository;
    private final UserTaskMuteService userTaskMuteService;

    public ChipServiceImp(ChipRepository chipRepository, ChipVersionRepository chipVersionRepository,
                          AccountService accountService, ChipUserRepository chipUserRepository,
                          UserRepository userRepository, ChipDetailRepository chipDetailRepository,
                          UserTaskMuteService userTaskMuteService) {
        this.chipRepository = chipRepository;
        this.chipVersionRepository = chipVersionRepository;
        this.accountService = accountService;
        this.chipUserRepository = chipUserRepository;
        this.userRepository = userRepository;
        this.chipDetailRepository = chipDetailRepository;
        this.userTaskMuteService = userTaskMuteService;
    }

    @Override
    public ChipDTO add(ChipDTO chipDTO) {
        log.debug("ChipService | add | chipDTO name: " + chipDTO.getName());

        try {
            Chip chip = new Chip();
            ChipVersion chipVersion = chipVersionRepository.findById(chipDTO.getChipVersionId())
                    .orElseThrow(() -> new BusinessException("Chip version not found", HttpStatus.NOT_FOUND));

            chip.setName(chipDTO.getName());
            chip.setChipVersion(chipVersion);
            chip.setPassword(RandomStringUtils.randomAlphanumeric(9));
            chip.setUsed(false);
            Chip savedChip = chipRepository.save(chip);

            return ChipMapper.chipToChipDTO(savedChip);
        } catch (Exception e) {
            log.error("ChipService | add | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @Override
    public ChipDTO findById(Long id) {
        log.debug("ChipService | findById | id: " + id);

        try {
            Chip chip = chipRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("This chip not found", HttpStatus.NOT_FOUND));

            return ChipMapper.chipToChipDTO(chip);
        } catch (Exception e) {
            log.error("ChipService | add | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public ApiResponse addNewChipToUser(Long chipId, String chipPassword) {
        log.debug("ChipService | addNewChipToUser | chip id: " + chipId);
        User user = accountService.getAuthenticatedAccount();

        log.debug("ChipService | addNewChipToUser | user: " + user.toString());

        try {
            Chip chip = chipRepository.findById(chipId)
                    .orElseThrow(() -> new BusinessException("This chip not found", HttpStatus.NOT_FOUND));

            if (chipUserRepository.existsByChipIdAndUserId(chipId, user.getId())) {
                throw new BusinessException("You already assign this chip", HttpStatus.NOT_FOUND);
            }

            if (chip.getPassword().equals(chipPassword)) {
                ChipUser chipUser = new ChipUser(chip, user, LocalDateTime.now(), ChipUserType.Controller);
                chipUserRepository.save(chipUser);

                userTaskMuteService.addTasksMuteDefaultToNewUser(chip.getId(), user);
                chipRepository.updateUsedStatus(chip.getId(), true);
                return new ApiResponse(true, "Chip add successfully");
            } else {
                throw new BusinessException("Chip not add successfully, password incorrect", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("ChipService | add | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public ApiResponse updatePhoto(Long chipId, MultipartFile multipartFile) {
        log.debug("ChipService | updatePhoto | chip id: " + chipId);
        User user = accountService.getAuthenticatedAccount();

        try {
            Chip chip = chipRepository.findById(chipId)
                    .orElseThrow(() -> new BusinessException("This chip not found", HttpStatus.NOT_FOUND));

            ChipUser chipUser = chipUserRepository.findByChipIdAndUserId(chip.getId(), user.getId())
                    .orElseThrow(() -> new BusinessException("You not own this chip", HttpStatus.BAD_REQUEST));

            if (chipUser.getChipUserType().equals(ChipUserType.Controller)) {

                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                log.debug("ChipService | updatePhoto | file name: " + fileName);

                String uploadDir = "chip-photos/" + user.getId();

                log.debug("ChipService | updatePhoto | uploadDir : " + uploadDir);

                FileUploadUtil.cleanDir(uploadDir);
                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

                chipRepository.updatePhoto(chip.getId(), "/chip-photos/" + user.getId() + "/" + fileName);
                return new ApiResponse(true, "Photo changed successfully");
            } else {
                throw new BusinessException("You have not access to change photo", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            log.error("ChipService | updatePhoto | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public ApiResponse updateChipDetails(Long chipId, UpdateChipDetailsRequest updateChipDetailsRequest) {
        log.debug("ChipService | updateChipDetails | chip id: " + chipId);
        User user = accountService.getAuthenticatedAccount();

        try {
            Chip chip = chipRepository.findById(chipId)
                    .orElseThrow(() -> new BusinessException("This chip not found", HttpStatus.NOT_FOUND));

            ChipUser chipUser = chipUserRepository.findByChipIdAndUserId(chip.getId(), user.getId())
                    .orElseThrow(() -> new BusinessException("You not own this chip", HttpStatus.BAD_REQUEST));

            if (chipUser.getChipUserType().equals(ChipUserType.Controller)) {
                chipRepository.updateName(chip.getId(), updateChipDetailsRequest.getName());

                chipDetailRepository.deleteAllByChipId(chip.getId());

                List<ChipDetail> details = new ArrayList<>();
                updateChipDetailsRequest.getDetails().forEach(chipDetailDTO -> {
                    details.add(new ChipDetail(chipDetailDTO.getId(), chipDetailDTO.getName(), chipDetailDTO.getValue(), chip));
                });

                chipDetailRepository.saveAll(details);

                return new ApiResponse(true, "Details changed successfully");
            } else {
                throw new BusinessException("You have not access to change photo", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            log.error("ChipService | updateChipDetails | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse addNewUser(Long chipId, Long userId) {
        log.debug("ChipService | addNewUser | chip id: " + chipId);
        User user = accountService.getAuthenticatedAccount();

        try {
            Chip chip = chipRepository.findById(chipId)
                    .orElseThrow(() -> new BusinessException("This chip not found", HttpStatus.NOT_FOUND));

            ChipUser chipUser = chipUserRepository.findByChipIdAndUserId(chip.getId(), user.getId())
                    .orElseThrow(() -> new BusinessException("You not own this chip", HttpStatus.BAD_REQUEST));

            User newUser = userRepository.findById(userId)
                    .orElseThrow(() -> new BusinessException("This user not found", HttpStatus.NOT_FOUND));

            if (chipUserRepository.existsByChipIdAndUserId(chipId, userId)) {
                throw new BusinessException("User already exist", HttpStatus.FORBIDDEN);
            }

            if (chipUser.getChipUserType().equals(ChipUserType.Controller)) {
                ChipUser newChipUser = new ChipUser(chip, newUser, LocalDateTime.now(), ChipUserType.Normal);
                chipUserRepository.save(newChipUser);

                userTaskMuteService.addTasksMuteDefaultToNewUser(chip.getId(), newUser);

                return new ApiResponse(true, "User successfully");
            } else {
                throw new BusinessException("You have not access to add new user", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            log.error("ChipService | updatePhoto | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<SimpleChipDTO> findAllUserChips() {
        log.debug("ChipService | findAllUserChips");
        User user = accountService.getAuthenticatedAccount();

        try {
            List<ChipUser> chipUsers = chipUserRepository.findAllByUserId(user.getId());
            List<SimpleChipDTO> chips = new ArrayList<>();

            chipUsers.forEach(chipUser -> {
                chips.add(new SimpleChipDTO(chipUser.getChip().getId(), chipUser.getChip().getName(), chipUser.getChip().getPhoto()));
            });

            return chips;
        } catch (Exception e) {
            log.error("ChipService | updatePhoto | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
