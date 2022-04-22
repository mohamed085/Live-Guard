package com.liveguard.service;

import com.liveguard.domain.User;

public interface UserService {

    void save(User user);

    Boolean existsByEmail(String email);

    void updateEnableStatus(Long id, Boolean status);

    User findByEmail(String email);

    void updateResetPasswordToken(Long id, String resetPasswordToken);

    User findByResetPasswordToken(String resetPasswordToken);

    void updatePassword(Long id, String newPassword);
}
