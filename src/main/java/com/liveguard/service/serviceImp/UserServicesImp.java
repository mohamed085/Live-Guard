package com.liveguard.service.serviceImp;

import com.liveguard.domain.User;
import com.liveguard.exception.BusinessException;
import com.liveguard.repository.UserRepository;
import com.liveguard.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class UserServicesImp implements UserService {

    private final UserRepository userRepository;

    public UserServicesImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        log.debug("UserService | save | user user: " + user.toString());
        try {
            userRepository.save(user);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("UserService | save | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Boolean existsByEmail(String email) {
        log.debug("UserService | existsByEmail | user email: " + email);
        try {
            return userRepository.existsByEmail(email);

        } catch (Exception e) {
            log.error("UserService | existsByEmail | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void updateEnableStatus(Long id, Boolean status) {
        log.debug("UserService | updateEnableStatus | id: " + id);
        log.debug("UserService | updateEnableStatus | status: " + status);

        try {
            userRepository.updateEnableStatus(id, status);
        } catch (Exception e) {
            log.error("UserService | updateEnableStatus | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public User findByEmail(String email) {
        log.debug("UserService | findByEmail | user email: " + email);

        try {
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new BusinessException("User not found", HttpStatus.NOT_FOUND));

        } catch (Exception e) {
            log.error("UserService | existsByEmail | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void updateResetPasswordToken(Long id, String resetPasswordToken) {
        log.debug("UserService | updateResetPasswordToken | id: " + id);
        log.debug("UserService | updateResetPasswordToken | resetPasswordToken: " + resetPasswordToken);

        try {
            userRepository.updateResetPasswordToken(id, resetPasswordToken);

        } catch (Exception e) {
            log.error("UserService | updateResetPasswordToken | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public User findByResetPasswordToken(String resetPasswordToken) {
        log.debug("UserService | findByResetPasswordToken | resetPasswordToken: " + resetPasswordToken);

        try {
            return userRepository.findByResetPasswordToken(resetPasswordToken)
                    .orElseThrow(() -> new BusinessException("User not found", HttpStatus.NOT_FOUND));

        } catch (Exception e) {
            log.error("UserService | updateResetPasswordToken | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void updatePassword(Long id, String newPassword) {
        log.debug("UserService | updateResetPasswordToken | id: " + id);
        log.debug("UserService | updateResetPasswordToken | newPassword: " + newPassword);

        try {
            userRepository.updatePassword(id, newPassword);

        } catch (Exception e) {
            log.error("UserService | updateResetPasswordToken | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
