package com.liveguard.controller;

import com.liveguard.dto.UserDTO;
import com.liveguard.payload.*;
import com.liveguard.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO) {
        log.debug("AuthController | Register | user email: " + userDTO.getEmail());

        authService.register(userDTO);

        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Account created successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.debug("AuthController | Login | user email: " + loginRequest.getEmail());

        String token = authService.login(loginRequest.getEmail(), loginRequest.getPassword());

        return ResponseEntity
                .ok()
                .body(new LoginResponse(loginRequest.getEmail(), token));
    }

    @PostMapping("/verify-account")
    public ResponseEntity<?> verifyAccount(@Valid @RequestBody VerifyAccountRequest verifyAccountRequest) {
        log.debug("AuthController | verifyAccount | user email: " + verifyAccountRequest.getUserEmail());

        return ResponseEntity
                .ok()
                .body(authService.verifyAccount(verifyAccountRequest));
    }

    @PostMapping("/resend-verify-mail")
    public ResponseEntity<?> resendVerifyMail(@Valid @RequestBody UserEmailRequest email) {
        log.debug("AuthController | resendVerifyMail | user email: " + email.getUserEmail());

        authService.resendVerifyAccount(email.getUserEmail());

        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Mail resend successfully"));
    }

    @PostMapping("/send-reset-password-token")
    public ResponseEntity<?> sendResetPasswordToken(@Valid @RequestBody UserEmailRequest email) {
        log.debug("AuthController | sendResetPasswordToken | user email: " + email.getUserEmail());

        authService.sendResetPasswordToken(email.getUserEmail());

        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Reset token send successfully"));

    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        log.debug("AuthController | sendResetPasswordToken | user email: " + resetPasswordRequest.getUserEmail());

        authService.resetPassword(resetPasswordRequest.getUserEmail(), resetPasswordRequest.getResetPasswordToken(), resetPasswordRequest.getNewPassword());

        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Password change successfully"));

    }
}