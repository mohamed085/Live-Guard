package com.liveguard.controller;

import com.liveguard.domain.Setting;
import com.liveguard.service.SettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/settings")
public class SettingController {

    private final SettingService settingService;

    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        log.debug("SettingController | getAll");
        List<Setting> settings = settingService.getAllSettings();

        return ResponseEntity
                .ok()
                .body(settings);
    }
}
