package com.liveguard.controller;

import com.liveguard.payload.*;
import com.liveguard.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> register(@Valid @RequestBody CustomerRegisterRequest customer) {
        log.debug("AuthController | Register | user email: " + customer.getEmail());

        authService.register(customer);

        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Account created successfully"));

    }

    @GetMapping("/verify/{code}")
    public ResponseEntity<?> verify(@PathVariable("code") String code) {
        log.debug("AuthController | verify | code: " + code);

        authService.verify(code);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Account verified successfully"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody UserEmailRequest email) {
        log.debug("AuthController | forgotPassword | email: " + email);

        authService.forgotPassword(email.getUserEmail());
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Check your email"));
    }

    @PostMapping("/reset_password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        log.debug("AuthController | resetPassword ");

        authService.resetPassword(resetPasswordRequest.getResetPasswordToken(), resetPasswordRequest.getPassword());

        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Password updated successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.debug("AuthController | Login | user email: " + loginRequest.getEmail());

        String token = authService.login(loginRequest.getEmail(), loginRequest.getPassword());

        return ResponseEntity
                .ok()
                .body(new LoginResponse(loginRequest.getEmail(), token));
    }

}
