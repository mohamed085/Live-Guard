package com.liveguard.service.serviceImp;

import com.liveguard.domain.User;
import com.liveguard.dto.UserDTO;
import com.liveguard.exception.BusinessException;
import com.liveguard.mapper.UserMapper;
import com.liveguard.payload.UpdateAccountRequest;
import com.liveguard.service.AccountService;
import com.liveguard.service.UserService;
import com.liveguard.util.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Slf4j
@Service
public class AccountServiceImp implements AccountService {

    private final UserService userService;

    public AccountServiceImp(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User getAuthenticatedAccount() {
        log.debug("AccountService | getAuthenticatedUser | start");
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        log.debug("AccountService | getAuthenticatedUser | user email: " + userEmail);

        try {
            return userService.findByEmail(userEmail);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("AccountService | getAuthenticatedUser | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void updateAuthenticatedAccount(UpdateAccountRequest updateAccountRequest) {
        log.debug("AccountService | updateAuthenticatedAccount | userDTO: " + updateAccountRequest.toString());

        User oldUser = getAuthenticatedAccount();
        log.debug("AccountService | updateAuthenticatedAccount | oldUser: " + oldUser.toString());

        userService.updateInfo(oldUser.getId(),
                updateAccountRequest.getName(),
                updateAccountRequest.getAbout(),
                updateAccountRequest.getPhone(),
                updateAccountRequest.getAddress(),
                updateAccountRequest.getFacebookUrl(),
                updateAccountRequest.getTwitterUrl(),
                updateAccountRequest.getInstagramUrl());
    }

    @Override
    public void updateAuthenticatedAccountAvatar(MultipartFile multipartFile) {
        User user = getAuthenticatedAccount();
        userService.updateAvatar(user.getId(), multipartFile);
    }
}
