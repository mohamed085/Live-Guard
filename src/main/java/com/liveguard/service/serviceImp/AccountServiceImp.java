package com.liveguard.service.serviceImp;

import com.liveguard.domain.User;
import com.liveguard.dto.UserDTO;
import com.liveguard.exception.BusinessException;
import com.liveguard.service.AccountService;
import com.liveguard.service.UserService;
import com.liveguard.util.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
        User user = userService.findByEmail(userEmail);
        return user;
    }

    @Override
    public User updateAuthenticatedAccount(UserDTO userDTO) {
        log.debug("UserService | updateAuthenticatedAccount | userDTO name: " + userDTO.getName());
        User user = getAuthenticatedAccount();

        if (userDTO.getName() != null)
            user.setName(userDTO.getName());

        if (userDTO.getPhone() != null)
            user.setPhone(userDTO.getPhone());

        if (userDTO.getAddress() != null)
            user.setAddress(userDTO.getAddress());

        if (userDTO.getDateOfBirth() != null)
            user.setDateOfBirth(userDTO.getDateOfBirth());

        try {
            User savedUser = userService.save(user);
            return savedUser;
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void updateAuthenticatedAccountAvatar(MultipartFile multipartFile) {
        User user = getAuthenticatedAccount();

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        log.debug("UserService | updateAuthenticatedAccountAvatar | file name: " + fileName);

        user.setAvatar("/user-photos/" + user.getId() + "/" + fileName);

        try {
            User savedUser = userService.save(user);
            String uploadDir = "user-photos/" + savedUser.getId();

            log.debug("AccountService | updateAuthenticatedAccountAvatar | savedUser : " + savedUser.toString());
            log.debug("AccountService | updateAuthenticatedAccountAvatar | uploadDir : " + uploadDir);

            FileUploadUtil.cleanDir(uploadDir);
            try {
                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            } catch (IOException e) {
                throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
