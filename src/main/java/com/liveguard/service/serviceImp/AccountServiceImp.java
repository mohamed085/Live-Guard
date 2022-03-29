package com.liveguard.service.serviceImp;

import com.liveguard.domain.User;
import com.liveguard.dto.UserDTO;
import com.liveguard.exception.BusinessException;
import com.liveguard.mapper.UserMapper;
import com.liveguard.repository.UserRepository;
import com.liveguard.service.AccountService;
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

    private final UserRepository userRepository;

    public AccountServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getAuthenticatedAccount() {
        log.debug("AccountService | getAuthenticatedUser | start");
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        log.debug("AccountService | getAuthenticatedUser | user email: " + userEmail);

        try {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new BusinessException("User not found", HttpStatus.BAD_REQUEST));

            return user;
        } catch (Exception e) {
            log.error("AuthService | register | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserDTO updateAuthenticatedAccount(UserDTO userDTO) {
        log.debug("AccountService | updateAuthenticatedAccount | userDTO: " + userDTO.toString());
        User oldUser = getAuthenticatedAccount();
        log.debug("AccountService | updateAuthenticatedAccount | oldUser: " + oldUser.toString());

        User user = UserMapper.UserDTOToUser(userDTO);
        user.setRoles(oldUser.getRoles());

        if (user.getPassword() != null) {
            user.setPassword(oldUser.getPassword());
        }

        if (oldUser.getResetPasswordToken() != null) {
            user.setResetPasswordToken(oldUser.getResetPasswordToken());
        }

        user.setAuthenticationType(oldUser.getAuthenticationType());
        try {
            User savedUSer = userRepository.save(user);

            return UserMapper.userToUserDTO(savedUSer);
        } catch (Exception e) {
            log.error("AuthService | updateAuthenticatedAccount | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    @Transactional
    public void updateAuthenticatedAccountAvatar(MultipartFile multipartFile) {
        User user = getAuthenticatedAccount();
        try {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            log.debug("UserService | updateAuthenticatedAccountAvatar | file name: " + fileName);

            user.setAvatar("/user-photos/" + user.getId() + "/" + fileName);
            userRepository.updateAvatar(user.getId(), user.getAvatar());
            String uploadDir = "user-photos/" + user.getId();

            log.debug("AccountService | updateAuthenticatedAccountAvatar | savedUser : " + user.toString());
            log.debug("AccountService | updateAuthenticatedAccountAvatar | uploadDir : " + uploadDir);

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
