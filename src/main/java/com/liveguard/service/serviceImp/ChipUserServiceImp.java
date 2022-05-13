package com.liveguard.service.serviceImp;

import com.liveguard.domain.*;
import com.liveguard.dto.ChipUserDTO;
import com.liveguard.exception.BusinessException;
import com.liveguard.payload.AddNewChipRequest;
import com.liveguard.repository.ChipUserDetailRepository;
import com.liveguard.repository.ChipUserRepository;
import com.liveguard.service.AccountService;
import com.liveguard.service.ChipService;
import com.liveguard.service.ChipUserService;
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

    public ChipUserServiceImp(ChipUserRepository chipUserRepository, AccountService accountService,
                              ChipService chipService, ChipUserDetailRepository chipUserDetailRepository) {
        this.chipUserRepository = chipUserRepository;
        this.accountService = accountService;
        this.chipService = chipService;
        this.chipUserDetailRepository = chipUserDetailRepository;
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

        chipUserRepository.save(chipUser);
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
    public List<ChipUser> findAllUsersInChip(Long chipId) {
        log.debug("ChipUserService | findAllUsersInChip | chipId: " + chipId);

        return chipUserRepository.findAllByChipId(chipId);
    }


}
