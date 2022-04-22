package com.liveguard.service.serviceImp;

import com.liveguard.domain.*;
import com.liveguard.dto.ChipDTO;
import com.liveguard.dto.SimpleChipDTO;
import com.liveguard.exception.BusinessException;
import com.liveguard.payload.ApiResponse;
import com.liveguard.payload.UpdateChipDetailsRequest;
import com.liveguard.repository.*;
import com.liveguard.service.*;
import com.liveguard.util.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.*;
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

    public static final int CHIPS_PER_PAGE = 6;

    private final ChipRepository chipRepository;
    private final ChipDetailRepository chipDetailRepository;
    private final AccountService accountService;
    private final UserService userService;
    private final UserTaskMuteService userTaskMuteService;
    private final ChipUserService chipUserService;
    private final ChipVersionService chipVersionService;

    public ChipServiceImp(ChipRepository chipRepository, ChipDetailRepository chipDetailRepository,
                          AccountService accountService, UserService userService,
                          UserTaskMuteService userTaskMuteService, ChipUserService chipUserService,
                          ChipVersionService chipVersionService) {
        this.chipRepository = chipRepository;
        this.chipDetailRepository = chipDetailRepository;
        this.accountService = accountService;
        this.userService = userService;
        this.userTaskMuteService = userTaskMuteService;
        this.chipUserService = chipUserService;
        this.chipVersionService = chipVersionService;
    }


    @Override
    public Chip add(ChipDTO chipDTO) {
        log.debug("ChipService | add | chipDTO name: " + chipDTO.getName());

        try {
            Chip chip = new Chip();
            ChipVersion chipVersion = chipVersionService.findById(chipDTO.getChipVersionId());

            chip.setName(chipDTO.getName());

            if (chipDTO.getName() == null || chipDTO.getName().equals("")) {
                chip.setName(RandomString.make(10));
            }
            chip.setChipVersion(chipVersion);
            chip.setPassword(RandomStringUtils.randomAlphanumeric(9));
            chip.setUsed(false);

            return chipRepository.save(chip);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ChipService | add | error");
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
            e.printStackTrace();
            log.error("ChipService | add | error");
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Chip> findAll() {
        log.debug("ChipService | findAll");

        try {

            return (List<Chip>) chipRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ChipService | findAll | error");
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Page<Chip> findAllByPage(int pageNum, String sortField, String sortDir, String keyword) {
        log.debug("ChipService | findAllByPage");
        log.debug("ChipService | findAllByPage | pageNum: " + pageNum);
        log.debug("ChipService | findAllByPage | sortField: " + sortField);
        log.debug("ChipService | findAllByPage | sortDir: " + sortDir);
        log.debug("ChipService | findAllByPage | keyword: " + keyword);

        try {
            if (sortField == null) {
                sortField = "id";
            }

            if (sortDir == null) {
                sortDir = "asc";
            }

            Sort sort = Sort.by(sortField);
            sort = sortDir.equals("disc") ? sort.descending() : sort.ascending();

            Pageable pageable = PageRequest.of(pageNum - 1 , CHIPS_PER_PAGE, sort);


            if (keyword == null) {
                return chipRepository.findAll(pageable);
            } else {
                return chipRepository.findAll(keyword, pageable);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("ChipService | findAll | error");
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deleteById(Long id) {
        log.debug("ChipService | deleteById | id: " + id);
        try {
            Chip chip = findById(id);

            if (chip.getUsed()) {
                throw new BusinessException("This chip already used", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            chipRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ChipService | deleteById | error");
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Chip> findAllByChipVersion(Long id) {
        log.debug("ChipService | findAllByChipVersion | id: " + id);

        try {

            return chipRepository.findAllByChipVersionId(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ChipService | findAllByChipVersion | error");
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public void updateUsedStatus(Long chipId, Boolean used) {
        log.debug("ChipService | existsByChipIdAndUserId | chipId: " + chipId);
        log.debug("ChipService | existsByChipIdAndUserId | used: " + used);

        try {

            chipRepository.updateUsedStatus(chipId, used);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ChipService | existsByChipIdAndUserId | error");
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    @Transactional
    public void addNewChipToUser(Long chipId, String chipPassword) {
        log.debug("ChipService | addNewChipToUser | chip id: " + chipId);
        User user = accountService.getAuthenticatedAccount();

        log.debug("ChipService | addNewChipToUser | user: " + user.toString());

        try {
            Chip chip = findById(chipId);

            if (chipUserService.existsByChipIdAndUserId(chipId, user.getId())) {
                throw new BusinessException("You already assign this chip", HttpStatus.NOT_FOUND);
            }

            if (chip.getPassword().equals(chipPassword)) {
                ChipUser chipUser = new ChipUser(chip, user, LocalDateTime.now(), ChipUserType.Controller);
                chipUserService.save(chipUser);

                userTaskMuteService.addTasksMuteDefaultToNewUser(chip.getId(), user);
                updateUsedStatus(chip.getId(), true);

            } else {
                throw new BusinessException("Chip not add successfully, password incorrect", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ChipService | addNewChipToUser | error");
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void updatePhoto(Long chipId, MultipartFile multipartFile) {
        log.debug("ChipService | updatePhoto | chip id: " + chipId);
        User user = accountService.getAuthenticatedAccount();

        try {
            Chip chip = findById(chipId);

            ChipUser chipUser = chipUserService.findByChipIdAndUserId(chip.getId(), user.getId());

            if (chipUser.getChipUserType().equals(ChipUserType.Controller)) {

                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                log.debug("ChipService | updatePhoto | file name: " + fileName);

                String uploadDir = "chip-photos/" + user.getId();

                log.debug("ChipService | updatePhoto | uploadDir : " + uploadDir);

                FileUploadUtil.cleanDir(uploadDir);
                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

                chipRepository.updatePhoto(chip.getId(), "/chip-photos/" + user.getId() + "/" + fileName);
            } else {
                throw new BusinessException("You have not access to change photo", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ChipService | updatePhoto | error");
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void updateChipDetails(Long chipId, UpdateChipDetailsRequest updateChipDetailsRequest) {
        log.debug("ChipService | updateChipDetails | chip id: " + chipId);
        User user = accountService.getAuthenticatedAccount();

        try {
            Chip chip = findById(chipId);

            ChipUser chipUser = chipUserService.findByChipIdAndUserId(chip.getId(), user.getId());

            if (chipUser.getChipUserType().equals(ChipUserType.Controller)) {
                chipRepository.updateName(chip.getId(), updateChipDetailsRequest.getName());

                chipDetailRepository.deleteAllByChipId(chip.getId());

                List<ChipDetail> details = new ArrayList<>();
                updateChipDetailsRequest.getDetails().forEach(chipDetailDTO -> {
                    details.add(new ChipDetail(chipDetailDTO.getId(), chipDetailDTO.getName(), chipDetailDTO.getValue(), chip));
                });

                chipDetailRepository.saveAll(details);

            } else {
                throw new BusinessException("You have not access to change photo", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ChipService | updateChipDetails | error");
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void addNewUser(Long chipId, Long userId) {
        log.debug("ChipService | addNewUser | chip id: " + chipId);
        User user = accountService.getAuthenticatedAccount();

        try {
            Chip chip = findById(chipId);

            ChipUser chipUser = chipUserService.findByChipIdAndUserId(chip.getId(), user.getId());

            User newUser = userService.findById(userId);

            if (chipUserService.existsByChipIdAndUserId(chipId, userId)) {
                throw new BusinessException("User already exist", HttpStatus.FORBIDDEN);
            }

            if (chipUser.getChipUserType().equals(ChipUserType.Controller)) {
                ChipUser newChipUser = new ChipUser(chip, newUser, LocalDateTime.now(), ChipUserType.Normal);
                chipUserService.save(newChipUser);

                userTaskMuteService.addTasksMuteDefaultToNewUser(chip.getId(), newUser);

            } else {
                throw new BusinessException("You have not access to add new user", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ChipService | addNewUser | error");
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void removeUser(Long chipId, Long userId) {
        log.debug("ChipService | removeUser | chip id: " + chipId);
        User user = accountService.getAuthenticatedAccount();

        try {
            Chip chip = findById(chipId);

            ChipUser chipUser = chipUserService.findByChipIdAndUserId(chip.getId(), user.getId());

            if (chipUser.getChipUserType().equals(ChipUserType.Controller)) {
                chipUserService.deleteByChipIdAndUserId(chipId, userId);

                userTaskMuteService.deleteUserTaskMute(userId, chip.getId());
            } else {
                throw new BusinessException("You have not access to add new user", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ChipService | addNewUser | error");
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Chip> findAllAuthenticatedUserChips() {
        log.debug("ChipService | findAllUserChips");
        User user = accountService.getAuthenticatedAccount();

        try {
            List<ChipUser> chipUsers = chipUserService.findAllByUserId(user.getId());
            List<Chip> chips = new ArrayList<>();


            chipUsers.forEach(chipUser -> {
                chips.add(chipUser.getChip());
            });

            return chips;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ChipService | findAllUserChips | error");
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
