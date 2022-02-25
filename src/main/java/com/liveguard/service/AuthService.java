package com.liveguard.service;

import com.liveguard.dto.UserDTO;
import com.liveguard.payload.VerifyAccountRequest;
import com.liveguard.payload.VerifyAccountResponse;

public interface AuthService {

    void register(UserDTO userDTO);

    String login(String email, String password);

    VerifyAccountResponse verifyAccount(VerifyAccountRequest request);

    void resendVerifyAccount(String email);

    void sendResetPasswordToken(String email);

    void resetPassword(String userEmail, String resetPasswordToken, String newPassword);
}
