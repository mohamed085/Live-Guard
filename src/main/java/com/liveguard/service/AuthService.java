package com.liveguard.service;

import com.liveguard.payload.CustomerRegisterRequest;

public interface AuthService {

    void register(CustomerRegisterRequest customer);

    void verify(String code);

    void forgotPassword(String email);

    void resetPassword(String restPasswordToken, String newPassword);

    String login(String email, String password);

}
