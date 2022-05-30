package com.liveguard.controller;

import com.liveguard.dto.TaskDTO;
import com.liveguard.mapper.TaskMapper;
import com.liveguard.payload.ApiResponse;
import com.liveguard.service.ChipUserTaskService;
import com.liveguard.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final ChipUserTaskService chipUserTaskService;

    public TaskController(TaskService taskService, ChipUserTaskService chipUserTaskService) {
        this.taskService = taskService;
        this.chipUserTaskService = chipUserTaskService;
    }

    @PostMapping("")
    public ResponseEntity<?> addTaskToChip(@RequestBody TaskDTO taskDTO) {
        log.debug("TaskController | addTaskToChip | task name: " + taskDTO.getName());

        taskService.save(taskDTO);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Task added successfully"));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTaskById(@PathVariable("taskId") Long taskId) {
        log.debug("TaskController | getTaskById | taskId: " + taskId);

        return ResponseEntity
                .ok()
                .body(TaskMapper.chipUserTaskToTaskDTO(chipUserTaskService.findByTaskIdAndAuthenticatedUser(taskId)));
    }


    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTaskById(@PathVariable("taskId") Long taskId) {
        log.debug("TaskController | deleteTaskById | taskId: " + taskId);

        chipUserTaskService.deleteByTaskIdAndAuthenticatedUser(taskId);
        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Task deleted successfully"));
    }

    @GetMapping("/chip/{chipId}")
    public ResponseEntity<?> getAllTasksByChipId(@PathVariable("chipId") Long chipId) {
        log.debug("TaskController | getTaskById | chipId: " + chipId);

        List<TaskDTO> taskDTOs = new ArrayList<>();
        chipUserTaskService.findAllTasksForAuthenticatedUserByChipId(chipId)
                .forEach(chipUserTask -> {
                    taskDTOs.add(TaskMapper.chipUserTaskToTaskDTO(chipUserTask));
                });
        return ResponseEntity
                .ok()
                .body(taskDTOs);
    }

}
