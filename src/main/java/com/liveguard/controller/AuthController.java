package com.liveguard.controller;

import com.liveguard.payload.ApiResponse;
import com.liveguard.payload.CustomerRegisterRequest;
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
    public ResponseEntity<?> register(@Valid @RequestBody CustomerRegisterRequest customer) {
        log.debug("AuthController | Register | user email: " + customer.getEmail());

        authService.register(customer);

        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Account created successfully"));

    }

}
