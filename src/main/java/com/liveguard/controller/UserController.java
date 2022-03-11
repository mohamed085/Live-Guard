package com.liveguard.controller;

import com.liveguard.dto.UserDTO;
import com.liveguard.mapper.UserMapper;
import com.liveguard.payload.ApiResponse;
import com.liveguard.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id) {
        log.debug("UserController | getUser | user id: " + id);

        return ResponseEntity
                .ok()
                .body(UserMapper.UserToUserDTO(userService.findById(id)));
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

    @PostMapping("/vendor")
    @PreAuthorize("hasRole('ROLE_admin')")
    public ResponseEntity<?> addVendor(@RequestBody UserDTO userDTO) {
        log.debug("UserController | getAllVendors");

        userService.addVendor(userDTO);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Vendor added successfully updated successfully"));
    }

    @GetMapping("/{id}/enabled/{status}")
    @PreAuthorize("hasRole('ROLE_admin')")
    public ResponseEntity<?> updateEnabledStatus(@PathVariable("id") Long id, @PathVariable("status") Boolean status) {
        log.debug("UserController | updateEnabledStatus | user id: " + id);

        userService.updateEnabledStatus(id, status);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "User id: " + id + " updated successfully"));
    }

    @GetMapping("/{id}/account_non_expired/{status}")
    @PreAuthorize("hasRole('ROLE_admin')")
    public ResponseEntity<?> updateAccountNonExpiredStatus(@PathVariable("id") Long id, @PathVariable("status") Boolean status) {
        log.debug("UserController | updateAccountNonExpiredStatus | user id: " + id);

        userService.updateAccountNonExpiredStatus(id, status);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "User id: " + id + " updated successfully"));
    }

    @GetMapping("/{id}/account_non_locked/{status}")
    @PreAuthorize("hasRole('ROLE_admin')")
    public ResponseEntity<?> updateAccountNonLockedStatus(@PathVariable("id") Long id, @PathVariable("status") Boolean status) {
        log.debug("UserController | updateAccountNonLockedStatus | user id: " + id);

        userService.updateAccountNonLockedStatus(id, status);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "User id: " + id + " updated successfully"));
    }

    @GetMapping("/{id}/credentials_non_expired/{status}")
    @PreAuthorize("hasRole('ROLE_admin')")
    public ResponseEntity<?> updateCredentialsNonExpiredStatus(@PathVariable("id") Long id, @PathVariable("status") Boolean status) {
        log.debug("UserController | updateCredentialsNonExpiredStatus | user id: " + id);

        userService.updateCredentialsNonExpiredStatus(id, status);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "User id: " + id + " updated successfully"));
    }

}
