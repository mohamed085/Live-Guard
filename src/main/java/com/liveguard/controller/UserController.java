package com.liveguard.controller;

import com.liveguard.service.UserServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServices userServices;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        log.debug("UserController | getById | user id: " + id);

        return ResponseEntity
                .ok()
                .body(userServices.findById(id));
    }

    @GetMapping("/simple-customers")
    public ResponseEntity<?> getAllCustomersInSimpleForm() {
        log.debug("UserController | findAllCustomersInSimpleForm");
        
        return ResponseEntity
                .ok()
                .body(userServices.findAllCustomersInSimpleForm());
    }
}
