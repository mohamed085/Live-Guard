package com.liveguard.service.serviceImp;

import com.liveguard.domain.*;
import com.liveguard.dto.TaskDTO;
import com.liveguard.dto.TaskSimpleDataDTO;
import com.liveguard.dto.UserSimpleDataDTO;
import com.liveguard.exception.BusinessException;
import com.liveguard.mapper.TaskMapper;
import com.liveguard.repository.TaskRepository;
import com.liveguard.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class TaskServiceImp implements TaskService {

    private final TaskRepository taskRepository;
    private final ChipService chipService;
    private final AccountService accountService;
    private final DayService dayService;
    private final UsersTasksMuteService usersTasksMuteService;
    private final UserService userService;

    public TaskServiceImp(TaskRepository taskRepository, ChipService chipService, AccountService accountService,
                          DayService dayService, UsersTasksMuteService usersTasksMuteService,
                          UserService userService) {
        this.taskRepository = taskRepository;
        this.chipService = chipService;
        this.accountService = accountService;
        this.dayService = dayService;
        this.usersTasksMuteService = usersTasksMuteService;
        this.userService = userService;
    }

    @Override
    public Task findById(Long id) {
        log.debug("TaskService | findById | id: " + id);
        try {
            return taskRepository
                    .findById(id)
                    .orElseThrow(() -> new BusinessException("Task not found", HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Task addTask(Long chipId, TaskDTO taskDTO) {
        log.debug("TaskService | addTask | chipId: " + chipId);
        log.debug("TaskService | addTask | taskDTO: " + taskDTO.getName());

        User user = accountService.getAuthenticatedAccount();
        log.debug("TaskService | addTask | userEmail: " + user.getEmail());

        Chip chip = chipService.findById(chipId);
        log.debug("TaskService | addChipAssociatedDetails | chip: " + chip.getName());

        if (chip.getUsers().isEmpty()) {
            log.warn("TaskService | addTask | This chip not used yet");
            throw new BusinessException("This chip not used yet", HttpStatus.BAD_REQUEST);
        }

        Task task = TaskMapper.taskDTOToTask(taskDTO);

        Set<Day> repeat = new HashSet<>();
        taskDTO.getRepeatId().forEach(dayId -> repeat.add(dayService.findById(dayId)));

        task.setRepeat(repeat);
        task.setChip(chip);
        task.setAddByUser(user);
        task.setCreateDate(LocalDateTime.now());

        log.debug("TaskService | addTask | task: " + task.getName());

        try {
            Task savedTask = taskRepository.save(task);
            log.debug("TaskService | addTask | savedTask: " + savedTask.getName());

            usersTasksMuteService.addTasksMuteDefaultToNewTask(chip, savedTask);

            return savedTask;
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public List<TaskSimpleDataDTO> findByChipId(Long id) {
        log.debug("TaskService | findByChipIdInTaskSimpleDataDTO | chipId: " + id);

        User user = accountService.getAuthenticatedAccount();
        log.debug("TaskService | findByChipIdInTaskSimpleDataDTO | user id: " + user.getId());

        List<TaskSimpleDataDTO> taskSimpleDataDTOs = new ArrayList<>();
        try {
            taskRepository.findByChipId(id).forEach(task -> {
                UserSimpleDataDTO userSimpleDataDTO = new UserSimpleDataDTO(task.getAddByUser().getId(), task.getAddByUser().getEmail(), task.getAddByUser().getName(), task.getAddByUser().getAvatar());
                UsersTasksMute usersTasksMute = usersTasksMuteService.findByTaskIdAndUserId(task.getId(), user.getId());
                taskSimpleDataDTOs.add(new TaskSimpleDataDTO(task.getId(), task.getName(), usersTasksMute.getStatus(), userSimpleDataDTO));
            });

            return taskSimpleDataDTOs;
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<TaskSimpleDataDTO> findByChipIdAndUser(Long id) {
        log.debug("TaskService | findByChipIdAndUser | chipId: " + id);
        User user = accountService.getAuthenticatedAccount();

        log.debug("TaskService | findByChipIdAndUser | userEmail: " + user.getEmail());
        try {
            List<Task> tasks = taskRepository.findByChipIdAndAddByUserId(id, user.getId());
            List<TaskSimpleDataDTO> taskSimpleDataDTOs = new ArrayList<>();

            tasks.forEach(task -> {
                UserSimpleDataDTO userSimpleDataDTO = new UserSimpleDataDTO(task.getAddByUser().getId(), task.getAddByUser().getEmail(), task.getAddByUser().getName(), task.getAddByUser().getAvatar());
                UsersTasksMute usersTasksMute = usersTasksMuteService.findByTaskIdAndUserId(task.getId(), user.getId());
                taskSimpleDataDTOs.add(new TaskSimpleDataDTO(task.getId(), task.getName(), usersTasksMute.getStatus(), userSimpleDataDTO));
            });

            return taskSimpleDataDTOs;
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<TaskSimpleDataDTO> findByChipIdAndSpecificUser(Long userId, Long chipId) {
        log.debug("TaskService | findByChipIdAndUser | chipId: " + chipId);
        log.debug("TaskService | findByChipIdAndUser | chipId: " + userId);
        User user = userService.findById(userId);

        log.debug("TaskService | findByChipIdAndUser | userEmail: " + user.getEmail());

       try {
           List<Task> tasks = taskRepository.findByChipIdAndAddByUserId(chipId, user.getId());
           List<TaskSimpleDataDTO> taskSimpleDataDTOs = new ArrayList<>();

           tasks.forEach(task -> {
               UserSimpleDataDTO userSimpleDataDTO = new UserSimpleDataDTO(task.getAddByUser().getId(), task.getAddByUser().getEmail(), task.getAddByUser().getName(), task.getAddByUser().getAvatar());
               UsersTasksMute usersTasksMute = usersTasksMuteService.findByTaskIdAndUserId(task.getId(), user.getId());
               taskSimpleDataDTOs.add(new TaskSimpleDataDTO(task.getId(), task.getName(), usersTasksMute.getStatus(), userSimpleDataDTO));
           });

           return taskSimpleDataDTOs;
       } catch (Exception e) {
           throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

}
