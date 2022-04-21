package com.liveguard.service.serviceImp;

import com.liveguard.domain.User;
import com.liveguard.exception.BusinessException;
import com.liveguard.repository.UserRepository;
import com.liveguard.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
}
