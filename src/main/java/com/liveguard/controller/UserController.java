package com.liveguard.controller;

import com.liveguard.service.UserServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
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

    @GetMapping("/page/{pageNum}")
    public ResponseEntity<?> getAllByPage(@PathVariable(name = "pageNum") int pageNum,
                                          @Param("sortField") String sortField,
                                          @Param("sortDir") String sortDir,
                                          @Param("keyword") String keyword) {

        log.debug("UserController | getAllByPage");
        log.debug("UserController | getAllByPage | pageNum: " + pageNum);
        log.debug("UserController | getAllByPage | sortField: " + sortField);
        log.debug("UserController | getAllByPage | sortDir: " + sortDir);
        log.debug("UserController | getAllByPage | keyword: " + keyword);

        return ResponseEntity
                .ok()
                .body(userServices.findAllByPage(pageNum, sortField, sortDir, keyword));
    }
}
