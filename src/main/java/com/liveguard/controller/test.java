package com.liveguard.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class test {

    @GetMapping("/api")
    public ResponseEntity<?> responseEntity() {
        log.debug("Asdasd");
        List<String> strings = new ArrayList<>();
        strings.add("Hii1");
        strings.add("Hii2");

        return ResponseEntity
                .ok()
                .body(strings);
    }
}
