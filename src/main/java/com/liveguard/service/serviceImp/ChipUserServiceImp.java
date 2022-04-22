package com.liveguard.service.serviceImp;

import com.liveguard.domain.ChipUser;
import com.liveguard.exception.BusinessException;
import com.liveguard.repository.ChipUserRepository;
import com.liveguard.service.ChipUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class ChipUserServiceImp implements ChipUserService {

    private final ChipUserRepository chipUserRepository;

    public ChipUserServiceImp(ChipUserRepository chipUserRepository) {
        this.chipUserRepository = chipUserRepository;
    }

    @Override
    public void save(ChipUser chipUser) {
        log.debug("ChipUserService | save | chipId: " + chipUser.toString());

        try {
            chipUserRepository.save(chipUser);
        }  catch (Exception e) {
            e.printStackTrace();
            log.error("ChipUserService | findByChipIdAndUserId | error");
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ChipUser findByChipIdAndUserId(Long chipId, Long userId) {
        log.debug("ChipUserService | findByChipIdAndUserId | chipId: " + chipId);
        log.debug("ChipUserService | findByChipIdAndUserId | userId: " + userId);

        try {
          return chipUserRepository.findByChipIdAndUserId(chipId, userId)
                  .orElseThrow(() -> new BusinessException("User not found", HttpStatus.NOT_FOUND));

        }  catch (Exception e) {
            e.printStackTrace();
            log.error("ChipUserService | findByChipIdAndUserId | error");
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Boolean existsByChipIdAndUserId(Long chipId, Long userId) {
        log.debug("ChipService | existsByChipIdAndUserId | chipId: " + chipId);
        log.debug("ChipService | existsByChipIdAndUserId | userId: " + userId);

        try {

            return chipUserRepository.existsByChipIdAndUserId(chipId, userId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ChipService | existsByChipIdAndUserId | error");
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    @Transactional
    public void deleteByChipIdAndUserId(Long chipId, Long userId) {
        log.debug("ChipService | existsByChipIdAndUserId | chipId: " + chipId);
        log.debug("ChipService | existsByChipIdAndUserId | userId: " + userId);

        try {
            if (!existsByChipIdAndUserId(chipId, userId)) {
                throw new BusinessException("User already not assign in chip", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            chipUserRepository.deleteByChipIdAndUserId(chipId, userId);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("ChipService | existsByChipIdAndUserId | error");
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public List<ChipUser> findAllByUserId(Long userId) {
        log.debug("ChipService | existsByChipIdAndUserId | userId: " + userId);

        try {

            return chipUserRepository.findAllByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ChipService | existsByChipIdAndUserId | error");
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
