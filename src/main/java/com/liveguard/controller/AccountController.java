package com.liveguard.controller;

import com.liveguard.dto.UserDTO;
import com.liveguard.mapper.UserMapper;
import com.liveguard.payload.ApiResponse;
import com.liveguard.payload.UpdateAccountRequest;
import com.liveguard.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("")
    public ResponseEntity<?> userInfo() {
        log.debug("AccountController | userInfo");

        return ResponseEntity
                .ok()
                .body(UserMapper.userToUserDTO(accountService.getAuthenticatedAccount()));
    }

    @PutMapping("")
    public ResponseEntity<?> updateUserInfo(@Valid @RequestBody UpdateAccountRequest updateAccountRequest) {
        log.debug("AccountController | updateUserInfo");

        accountService.updateAuthenticatedAccount(updateAccountRequest);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Account updated successfully"));
    }

    @PutMapping("/avatar")
    public ResponseEntity<?> updateUserAvatar(@Valid @RequestParam("file") MultipartFile multipartFile) {
        log.debug("AccountController | updateUserAvatar");

        accountService.updateAuthenticatedAccountAvatar(multipartFile);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Image saved successfully"));
    }
}
