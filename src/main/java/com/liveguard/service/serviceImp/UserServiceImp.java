package com.liveguard.service.serviceImp;

import com.liveguard.domain.AuthenticationType;
import com.liveguard.domain.User;
import com.liveguard.domain.VerificationCode;
import com.liveguard.dto.UserDTO;
import com.liveguard.exception.BusinessException;
import com.liveguard.mapper.UserMapper;
import com.liveguard.repository.UserRepository;
import com.liveguard.service.RoleService;
import com.liveguard.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
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
        log.debug("UserService | save | email: " + user.getEmail());

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
    public List<User> getAllEnableVendors() {
        log.debug("UserService | getAllVendors");
        try {
            return userRepository.findAllByRolesAndEnableTrue(roleService.findById(2L));
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
        }
    }

    @Override
    public void addVendor(UserDTO userDTO) {
        log.debug("AuthService | addVendor | user: " + userDTO.getEmail());

        User user = UserMapper.UserDTOToUser(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(roleService.findById(2L));
        user.setCreatedTime(LocalDateTime.now());
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setEnable(true);
        user.setAuthenticationType(AuthenticationType.DATABASE);

        save(user);
    }

    @Override
    @Transactional
    public void updateEnabledStatus(Long id, Boolean status) {
        log.debug("UserService | updateEnabledStatus");
        try {
            userRepository.updateEnabledStatus(id, status);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void updateAccountNonExpiredStatus(Long id, Boolean status) {
        log.debug("UserService | updateAccountNonExpiredStatus");
        try {
            userRepository.updateAccountNonExpiredStatus(id, status);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    @Transactional
    public void updateAccountNonLockedStatus(Long id, Boolean status) {
        log.debug("UserService | updateAccountNonLockedStatus");
        try {
            userRepository.updateAccountNonLockedStatus(id, status);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    @Transactional
    public void updateCredentialsNonExpiredStatus(Long id, Boolean status) {
        log.debug("UserService | updateCredentialsNonExpiredStatus");
        try {
            userRepository.updateCredentialsNonExpiredStatus(id, status);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
