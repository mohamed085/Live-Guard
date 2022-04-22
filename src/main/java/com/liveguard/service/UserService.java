package com.liveguard.service;

import com.liveguard.domain.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    void save(User user);

    Boolean existsByEmail(String email);

    void updateEnableStatus(Long id, Boolean status);

    User findByEmail(String email);

    void updateResetPasswordToken(Long id, String resetPasswordToken);

    User findByResetPasswordToken(String resetPasswordToken);

    void updatePassword(Long id, String newPassword);

    void updateAvatar(Long id, MultipartFile multipartFile);
}
