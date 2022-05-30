package com.liveguard.service.serviceImp;

import com.liveguard.domain.Chip;
import com.liveguard.domain.Day;
import com.liveguard.domain.Task;
import com.liveguard.domain.User;
import com.liveguard.dto.TaskDTO;
import com.liveguard.exception.BusinessException;
import com.liveguard.mapper.TaskMapper;
import com.liveguard.repository.TaskRepository;
import com.liveguard.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class TaskServiceImp implements TaskService {

    private final TaskRepository taskRepository;
    private final AccountService accountService;
    private final ChipUserService chipUserService;
    private final ChipService chipService;
    private final DayService dayService;
    private final ChipUserTaskService chipUserTaskService;


    public TaskServiceImp(TaskRepository taskRepository, AccountService accountService,
                          ChipUserService chipUserService, ChipService chipService, DayService dayService,
                          ChipUserTaskService chipUserTaskService) {
        this.taskRepository = taskRepository;
        this.accountService = accountService;
        this.chipUserService = chipUserService;
        this.chipService = chipService;
        this.dayService = dayService;
        this.chipUserTaskService = chipUserTaskService;
    }


    @Override
    public void save(TaskDTO taskDTO) {
        log.debug("TaskService | addTask | chipId: " + taskDTO.getChipId());
        log.debug("TaskService | addTask | taskDTO: " + taskDTO.getName());

        User user = accountService.getAuthenticatedAccount();
        log.debug("TaskService | addTask | user: " + user.getId());

        if (!chipUserService.existByChipIdAndUserId(taskDTO.getChipId(), user.getId())) {
            log.error("You not have access to this chip");
            throw new BusinessException("You not have access to this chip", HttpStatus.BAD_REQUEST);
        }

        Chip chip = chipService.findById(taskDTO.getChipId());
        log.debug("TaskService | addTask | chip: " + chip.getId());

        Task task = TaskMapper.taskDTOToTask(taskDTO);

        Set<Day> repeat = new HashSet<>();
        taskDTO.getRepeatId().forEach(dayId -> repeat.add(dayService.findById(dayId)));
        task.setRepeat(repeat);
        task.setChip(chip);
        task.setAddByUser(user);
        task.setCreateDate(LocalDateTime.now());

        Task savedTask = taskRepository.save(task);
        chipUserTaskService.addChipUserTaskForNewTask(savedTask);
    }
}
