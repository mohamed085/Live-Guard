package com.liveguard.service.serviceImp;

import com.liveguard.domain.*;
import com.liveguard.dto.ChipUserDTO;
import com.liveguard.exception.BusinessException;
import com.liveguard.payload.AddNewChipRequest;
import com.liveguard.repository.ChipUserDetailRepository;
import com.liveguard.repository.ChipUserRepository;
import com.liveguard.service.*;
import com.liveguard.util.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ChipUserServiceImp implements ChipUserService {

    private final ChipUserRepository chipUserRepository;
    private final AccountService accountService;
    private final ChipService chipService;
    private final ChipUserDetailRepository chipUserDetailRepository;
    private final UserService userService;
    private final ChipUserTaskService chipUserTaskService;

    public ChipUserServiceImp(ChipUserRepository chipUserRepository, AccountService accountService,
                              ChipService chipService, ChipUserDetailRepository chipUserDetailRepository,
                              UserService userService, ChipUserTaskService chipUserTaskService) {
        this.chipUserRepository = chipUserRepository;
        this.accountService = accountService;
        this.chipService = chipService;
        this.chipUserDetailRepository = chipUserDetailRepository;
        this.userService = userService;
        this.chipUserTaskService = chipUserTaskService;
    }

    @Override
    public void add(AddNewChipRequest addNewChipRequest) {
        log.debug("ChipUserService | add | addNewChipRequest: " + addNewChipRequest.toString());

        User user = accountService.getAuthenticatedAccount();
        log.debug("ChipUserService | add | user: " + user.toString());

        if (chipUserRepository.existsByUserIdAndChipKey(user.getId(), addNewChipRequest.getKey())) {
            log.error("This chip already assign to you");
            throw new BusinessException("This chip already assign to you", HttpStatus.OK);
        }

        Chip chip = chipService.findByKeyAndPassword(addNewChipRequest.getKey(), addNewChipRequest.getPassword());

        ChipUser chipUser = new ChipUser();
        chipUser.setName("");
        chipUser.setPhoto("");
        chipUser.setAddDate(LocalDateTime.now());
        chipUser.setChip(chip);
        chipUser.setUser(user);
        chipUser.setChipUserType(ChipUserType.Controller);

        ChipUser savedChipUser = chipUserRepository.save(chipUser);
        chipUserTaskService.addChipUserTaskForNewChipUser(savedChipUser);
    }

    @Override
    public List<ChipUser> findAllByUser() {
        log.debug("ChipUserService | findAllByUser");

        User user = accountService.getAuthenticatedAccount();
        log.debug("ChipUserService | findAllByUser | user: " + user.toString());

        return chipUserRepository.findAllByUserId(user.getId());
    }

    @Override
    @Transactional
    public void updateMyChipInfo(ChipUserDTO chipUserDTO) {
        log.debug("ChipUserService | updateMyChipInfo | chipUserDTO: " + chipUserDTO.toString());

        User user = accountService.getAuthenticatedAccount();
        log.debug("ChipUserService | updateMyChipInfo | user: " + user.toString());

        if (!chipUserRepository.existsByUserIdAndId(user.getId(), chipUserDTO.getId())) {
            log.error("This chip not belong to you");
            throw new BusinessException("This chip not belong to you", HttpStatus.BAD_REQUEST);
        }

        try {
            String fileName = StringUtils.cleanPath(chipUserDTO.getPhotoFile().getOriginalFilename());
            log.debug("ChipUserService | updateMyChipInfo | file name: " + fileName);

            String uploadDir = "chip-user-photos/" + user.getId();

            log.debug("ChipUserService | updateMyChipInfo | uploadDir : " + uploadDir);

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, chipUserDTO.getPhotoFile());

            chipUserRepository.updateChipUserInfo(chipUserDTO.getId(), chipUserDTO.getName(), "/chip-user-photos/" + user.getId() + "/" + fileName);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("ChipUserService | updateMyChipInfo | error");
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void updateMyChipDetails(ChipUserDTO chipUserDTO) {
        log.debug("ChipUserService | updateMyChipDetails | chipUserDTO: " + chipUserDTO.toString());

        User user = accountService.getAuthenticatedAccount();
        log.debug("ChipUserService | updateMyChipDetails | user: " + user.toString());

        if (!chipUserRepository.existsByUserIdAndId(user.getId(), chipUserDTO.getId())) {
            log.error("This chip not belong to you");
            throw new BusinessException("This chip not belong to you", HttpStatus.BAD_REQUEST);
        }

        ChipUser chipUser = chipUserRepository.findById(chipUserDTO.getId()).get();

        List<ChipUserDetail> details = new ArrayList<>();
        chipUserDTO.getDetails().forEach(chipDetailDTO -> {
            details.add(new ChipUserDetail(chipDetailDTO.getId(), chipDetailDTO.getName(), chipDetailDTO.getValue(), chipUser));
        });
        chipUserDetailRepository.saveAll(details);
    }

    @Override
    public void addNewUserToMyChip(Long userId, Long chipId) {
        log.debug("ChipUserService | AddNewUserToMyChip | userId: " + userId);
        log.debug("ChipUserService | AddNewUserToMyChip | chipId: " + chipId);

        User user = accountService.getAuthenticatedAccount();
        log.debug("ChipUserService | updateMyChipDetails | user: " + user.toString());

        if (!chipUserRepository.existsByUserIdAndChipUserType(user.getId(), ChipUserType.Controller)) {
            log.error("You not control this chip");
            throw new BusinessException("You not control this chip", HttpStatus.BAD_REQUEST);
        }

        if (chipUserRepository.existsByUserIdAndChipId(userId, chipId)) {
            log.error("User already exist");
            throw new BusinessException("User already exist", HttpStatus.BAD_REQUEST);
        }

        ChipUser chipUser = new ChipUser();
        chipUser.setName("");
        chipUser.setPhoto("");
        chipUser.setAddDate(LocalDateTime.now());
        chipUser.setChip(chipService.findById(chipId));
        chipUser.setUser(userService.findById(userId));
        chipUser.setChipUserType(ChipUserType.Normal);

        ChipUser savedChipUser = chipUserRepository.save(chipUser);
        chipUserTaskService.addChipUserTaskForNewChipUser(savedChipUser);
    }

    @Override
    @Transactional
    public void removeNormalUserFromMyChip(Long userId, Long chipId) {
        log.debug("ChipUserService | removeNormalUserFromMyChip | userId: " + userId);
        log.debug("ChipUserService | removeNormalUserFromMyChip | chipId: " + chipId);

        User user = accountService.getAuthenticatedAccount();
        log.debug("ChipUserService | updateMyChipDetails | user: " + user.toString());

        if (!chipUserRepository.existsByUserIdAndChipUserType(user.getId(), ChipUserType.Controller)) {
            log.error("You not control this chip");
            throw new BusinessException("You not control this chip", HttpStatus.BAD_REQUEST);
        }

        if (!chipUserRepository.existsByUserIdAndChipId(userId, chipId)) {
            log.error("User already not exist");
            throw new BusinessException("User already not exist", HttpStatus.BAD_REQUEST);
        }

        if (chipUserRepository.existsByUserIdAndChipUserType(userId, ChipUserType.Controller)) {
            log.error("You not have access to delete this user");
            throw new BusinessException("You not have access to delete this user", HttpStatus.BAD_REQUEST);
        }

        chipUserRepository.deleteByUserIdAndChipId(userId, chipId);

    }

    @Override
    public ChipUser findById(Long id) {
        log.debug("ChipUserService | findById | id: " + id);
        return chipUserRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Chip not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public Boolean existByChipIdAndUserId(Long chipId, Long userId) {
        log.debug("ChipUserService | existByChipIdAndUserId | chipId: " + chipId);
        log.debug("ChipUserService | existByChipIdAndUserId | userId: " + userId);

        return chipUserRepository.existsByUserIdAndChipId(userId, chipId);
    }

    @Override
    public List<ChipUser> findAllByChipId(Long chipId) {
        log.debug("ChipUserService | findAllByChipId | chipId: " + chipId);

        return chipUserRepository.findAllByChipId(chipId);
    }


}
