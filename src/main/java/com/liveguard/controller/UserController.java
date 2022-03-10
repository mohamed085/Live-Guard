package com.liveguard.controller;

import com.liveguard.dto.UserDTO;
import com.liveguard.mapper.UserMapper;
import com.liveguard.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/customers")
    public ResponseEntity<?> getAllCustomers() {
        log.debug("UserController | getAllCustomers");

        List<UserDTO> customers = new ArrayList<>();
        userService.getAllCustomers().forEach(user -> customers.add(UserMapper.UserToUserDTO(user)));
        return ResponseEntity
                .ok()
                .body(customers);
    }


    @GetMapping("/vendors")
    public ResponseEntity<?> getAllVendors() {
        log.debug("UserController | getAllVendors");

        List<UserDTO> vendors = new ArrayList<>();
        userService.getAllVendors().forEach(user -> vendors.add(UserMapper.UserToUserDTO(user)));
        return ResponseEntity
                .ok()
                .body(vendors);
    }
}
