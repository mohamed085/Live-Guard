package com.liveguard.controller;

import com.liveguard.domain.User;
import com.liveguard.dto.UserDTO;
import com.liveguard.mapper.UserMapper;
import com.liveguard.payload.ApiResponse;
import com.liveguard.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/account")

public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("")
    public ResponseEntity<UserDTO> userInfo() {
        log.debug("AccountController | userInfo");

        User user = accountService.getAuthenticatedAccount();
        UserDTO userDTO = UserMapper.UserToUserDTO(user);
        return ResponseEntity
                .ok()
                .body(userDTO);
    }

    @PutMapping("")
    public ResponseEntity<UserDTO> updateUserInfo(@RequestBody UserDTO userDTO) {
        log.debug("AccountController | updateUserInfo");
        User returnUser = accountService.updateAuthenticatedAccount(userDTO);
        UserDTO returnUserDTO = UserMapper.UserToUserDTO(returnUser);

        return ResponseEntity
                .ok()
                .body(returnUserDTO);
    }

    @PutMapping("/avatar")
    public ResponseEntity<ApiResponse> updateUserAvatar(@Valid @RequestParam("file") MultipartFile multipartFile) {
        log.debug("AccountController | updateUserAvatar");
        accountService.updateAuthenticatedAccountAvatar(multipartFile);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Image saved successfully"));
    }

}
