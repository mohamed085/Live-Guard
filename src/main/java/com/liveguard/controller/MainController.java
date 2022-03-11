package com.liveguard.controller;

import com.liveguard.dto.DayDTO;
import com.liveguard.mapper.DayMapper;
import com.liveguard.service.DayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class MainController {

    private final DayService dayService;

    public MainController(DayService dayService) {
        this.dayService = dayService;
    }

    @GetMapping("/days")
    public ResponseEntity<?> getAllDays() {
        log.debug("TaskController | getAllDays");

        List<DayDTO> taskDayDTOs = new ArrayList<>();
        dayService.findAll().forEach(taskDay -> taskDayDTOs.add(DayMapper.taskDayToTaskDayDTO(taskDay)));
        return ResponseEntity
                .ok()
                .body(taskDayDTOs);
    }

    @GetMapping("/days/{id}")
    public ResponseEntity<?> getDay(@PathVariable("id") Long id) {
        log.debug("TaskController | getDay | id: " + id);

        DayDTO taskDayDTO = DayMapper.taskDayToTaskDayDTO(dayService.findById(id));
        return ResponseEntity
                .ok()
                .body(taskDayDTO);
    }

}
