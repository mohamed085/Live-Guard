package com.liveguard.controller;

import com.liveguard.dto.TaskDTO;
import com.liveguard.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/chip/{id}")
    public ResponseEntity<?> addTaskToChip(@PathVariable("id") Long id,
                                           @RequestBody TaskDTO taskDTO) {

        log.debug("TaskController | addTaskToChip | chip id: " + id);
        log.debug("TaskController | addTaskToChip | task name: " + taskDTO.getName());

        return ResponseEntity
                .ok()
                .body(taskService.addTask(id, taskDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        log.debug("TaskController | getById | task id: " + id);

        return ResponseEntity
                .ok()
                .body(taskService.findById(id));
    }

    @GetMapping("/chip/{id}")
    public ResponseEntity<?> getAllInChip(@PathVariable("id") Long id) {
        log.debug("TaskController | getAllInChip | chip id: " + id);

        return ResponseEntity
                .ok()
                .body(taskService.findAllInChip(id));
    }

    @GetMapping("/my/chip/{id}")
    public ResponseEntity<?> getMyTasksInChip(@PathVariable("id") Long id) {
        log.debug("TaskController | getMyTasksInChip | chip id: " + id);

        return ResponseEntity
                .ok()
                .body(taskService.findMyTasksInChip(id));
    }

    @GetMapping("/others/chip/{id}")
    public ResponseEntity<?> getOtherTasksInChip(@PathVariable("id") Long id) {
        log.debug("TaskController | getOtherTasksInChip | chip id: " + id);

        return ResponseEntity
                .ok()
                .body(taskService.findOtherTasksInChip(id));
    }
}
