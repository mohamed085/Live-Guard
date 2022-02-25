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
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByEmail(String email) {
        log.debug("UserService | findByEmail | email: " + email);

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("User not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public User findById(Long id) {
        log.debug("UserService | findById | id: " + id);

        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("User not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public Boolean userExistByEmail(String email) {
        log.debug("UserService | userExistByEmail | email: " + email);

        return userRepository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        log.debug("UserService | userExistByEmail | email: " + user.getEmail());

        if (user.getId() == null) {
            if (userExistByEmail(user.getEmail())) {
                log.warn("UserService | Register User | User: " + user.getEmail() + " email already exists");
                throw new BusinessException(String.format("Email %s already exists", user.getEmail()), HttpStatus.BAD_REQUEST);
            }
        }
        User savedUser = userRepository.save(user);
        return savedUser;
    }
}
