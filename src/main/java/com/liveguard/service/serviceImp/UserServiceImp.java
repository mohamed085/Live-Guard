package com.liveguard.service.serviceImp;

import com.liveguard.domain.User;
import com.liveguard.exception.BusinessException;
import com.liveguard.repository.UserRepository;
import com.liveguard.service.RoleService;
import com.liveguard.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserServiceImp(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }


    @Override
    public User findByEmail(String email) {
        log.debug("UserService | findByEmail | email: " + email);

        try {
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new BusinessException("User not found", HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public User findById(Long id) {
        log.debug("UserService | findById | id: " + id);

        try {
            return userRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("User not found", HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Boolean userExistByEmail(String email) {
        log.debug("UserService | userExistByEmail | email: " + email);

        try {
            return userRepository.existsByEmail(email);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<User> getAllVendors() {
        log.debug("UserService | getAllVendors");
        try {
            return userRepository.findAllByRoles(roleService.findById(2L));
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<User> getAllCustomers() {
        log.debug("UserService | getAllCustomers");
        try {
            return userRepository.findAllByRoles(roleService.findById(1L));
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }    }
}
