package com.liveguard.controller;

import com.liveguard.domain.Task;
import com.liveguard.domain.User;
import com.liveguard.dto.TaskDTO;
import com.liveguard.dto.DayDTO;
import com.liveguard.dto.TaskSimpleDataDTO;
import com.liveguard.mapper.DayMapper;
import com.liveguard.mapper.TaskMapper;
import com.liveguard.payload.ApiResponse;
import com.liveguard.service.AccountService;
import com.liveguard.service.DayService;
import com.liveguard.service.DayService;
import com.liveguard.service.TaskService;
import com.liveguard.service.UsersTasksMuteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UsersTasksMuteService usersTasksMuteService;
    private final AccountService accountService;

    public TaskController(TaskService taskService, UsersTasksMuteService usersTasksMuteService,
                          AccountService accountService) {
        this.taskService = taskService;
        this.usersTasksMuteService = usersTasksMuteService;
        this.accountService = accountService;
    }


    @PostMapping("/chip/{id}")
    public ResponseEntity<?> addTaskToChip(@PathVariable("id") Long id,
                                           @RequestBody TaskDTO taskDTO) {

        log.debug("TaskController | addTaskToChip | chip id: " + id);
        log.debug("TaskController | addTaskToChip | task name: " + taskDTO.getName());

        Task task = taskService.addTask(id, taskDTO);
        return ResponseEntity
                .ok()
                .body(TaskMapper.taskToTaskDTO(task));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTask(@PathVariable ("id") Long id) throws ParseException {
        log.debug("TaskController | getTask | task id: " + id);

        Task task = taskService.findById(id);
        return  ResponseEntity
                .ok()
                .body(TaskMapper.taskToTaskDTO(task));
    }


    @GetMapping("/chip/{id}")
    public ResponseEntity<?> getAllChipTasks(@PathVariable("id") Long id) {
        log.debug("TaskController | getAllChipTasks | chip id: " + id);


        return ResponseEntity
                .ok()
                .body(taskService.findByChipId(id));
    }


    @GetMapping("/user/chip/{id}")
    public ResponseEntity<?> getAllUserChipTasks(@PathVariable("id") Long id) {
        log.debug("TaskController | getAllUserChipTasks | chip id: " + id);

        List<TaskSimpleDataDTO> taskSimpleDataDTOs = taskService.findByChipIdAndUser(id);
        return ResponseEntity
                .ok()
                .body(taskSimpleDataDTOs);
    }

    @GetMapping("/{userId}/chip/{id}")
    public ResponseEntity<?> getAllSpecificUserChipTasks(@PathVariable("id") Long id,
                                                         @PathVariable("userId") Long userId) {

        log.debug("TaskController | getAllSpecificUserChipTasks | chip id: " + id);
        log.debug("TaskController | getAllSpecificUserChipTasks | user id: " + userId);

        List<TaskSimpleDataDTO> taskSimpleDataDTOs = taskService.findByChipIdAndUser(id);
        return ResponseEntity
                .ok()
                .body(taskSimpleDataDTOs);
    }

    @GetMapping("/{id}/{mute}")
    private ResponseEntity<?> editMuteMode(@PathVariable("id") Long id,
                                           @PathVariable("mute") Boolean muteStatus) {

        log.debug("TaskController | getAllChipTasks | task id: " + id);
        log.debug("TaskController | getAllChipTasks | muteStatus: " + muteStatus);

        User user = accountService.getAuthenticatedAccount();
        usersTasksMuteService.updateMuteStatusByUserIdAndTaskId(user.getId(), id, muteStatus);

        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Status updated successfully"));
    }

}
