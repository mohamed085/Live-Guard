package com.liveguard.service.serviceImp;

import com.liveguard.domain.*;
import com.liveguard.dto.*;
import com.liveguard.exception.BusinessException;
import com.liveguard.mapper.TaskMapper;
import com.liveguard.repository.*;
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
    private final DayService dayService;
    private final ChipService chipService;
    private final AccountService accountService;
    private final ChipUserService chipUserService;
    private final UserTaskMuteService userTaskMuteService;
    private final UserService userService;

    public TaskServiceImp(TaskRepository taskRepository, DayService dayService, ChipService chipService,
                          AccountService accountService, ChipUserService chipUserService,
                          UserTaskMuteService userTaskMuteService, UserService userService) {
        this.taskRepository = taskRepository;
        this.dayService = dayService;
        this.chipService = chipService;
        this.accountService = accountService;
        this.chipUserService = chipUserService;
        this.userTaskMuteService = userTaskMuteService;
        this.userService = userService;
    }


    @Override
    public void addTask(Long chipId, TaskDTO taskDTO) {
        log.debug("TaskService | addTask | chipId: " + chipId);
        log.debug("TaskService | addTask | taskDTO: " + taskDTO.getName());

        User user = accountService.getAuthenticatedAccount();
        log.debug("TaskService | addTask | user: " + user.toString());

        try {
            Chip chip = chipService.findById(chipId);

            log.debug("TaskService | addTask | chip: " + chip.toString());

            ChipUser chipUser = chipUserService.findByChipIdAndUserId(chip.getId(), user.getId());

            log.debug("TaskService | addTask | chipUser: " + chipUser.toString());

            Task task = TaskMapper.taskDTOToTask(taskDTO);

            Set<Day> repeat = new HashSet<>();
            taskDTO.getRepeatId().forEach(dayId -> repeat.add(dayService.findById(dayId)));
            task.setRepeat(repeat);
            task.setChip(chip);
            task.setAddByUser(user);
            task.setCreateDate(LocalDateTime.now());

            log.debug("TaskService | addTask | task: " + task.getName());

            Task savedTask = taskRepository.save(task);
            log.debug("TaskService | addTask | savedTask: " + savedTask.getName());

            userTaskMuteService.addTasksMuteDefaultToNewTask(task, chip);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("TaskService | addTask | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Task findById(Long id) {
        User user = accountService.getAuthenticatedAccount();
        log.debug("TaskService | findById | id: " + id);

        try {
            return taskRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("Task not found", HttpStatus.BAD_REQUEST));

        } catch (Exception e) {
            e.printStackTrace();
            log.error("TaskService | findById | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ITaskDTO findByIdInITaskDTO(Long id) {
        User user = accountService.getAuthenticatedAccount();
        log.debug("TaskService | findByIdInITaskDTO | user: " + user.toString());

        try {

            return taskRepository.findByIdInITaskDTO(id, user.getId())
                    .orElseThrow(() -> new BusinessException("Task not found", HttpStatus.BAD_REQUEST));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("TaskService | findByIdInITaskDTO | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public TaskDTO findByIdForUser(Long id) {
        User user = accountService.getAuthenticatedAccount();
        log.debug("TaskService | findAllInChip | user: " + user.toString());

        try {
             ITaskDTO task = taskRepository.findByIdInITaskDTO(id, user.getId())
                     .orElseThrow(() -> new BusinessException("Task not found", HttpStatus.BAD_REQUEST));

             List<ITaskRepeat> repeats = taskRepository.findAllRepeatDaysInTask(task.getId());

             return TaskMapper.iTaskToTaskDTO(task, repeats);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("TaskService | findByIdForUser | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<SimpleTaskDTO> findAllInChip(Long id) {
        log.debug("TaskService | findAllInChip | chipId: " + id);

        User user = accountService.getAuthenticatedAccount();
        log.debug("TaskService | findAllInChip | user: " + user.toString());

        try {
            List<SimpleTaskDTO> simpleTasks = new ArrayList<>();

            taskRepository.findAllTasksInITaskDTO(user.getId(), id).forEach(iTaskDTO -> {
                simpleTasks.add(TaskMapper.iTaskToSimpleTaskDTO(iTaskDTO));
            });

            return simpleTasks;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("TaskService | findAllInChip | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<SimpleTaskDTO> findMyTasksInChip(Long id) {
        log.debug("TaskService | findMyTasksInChip | chipId: " + id);

        User user = accountService.getAuthenticatedAccount();
        log.debug("TaskService | findMyTasksInChip | user: " + user.toString());

        try {
            List<SimpleTaskDTO> simpleTasks = new ArrayList<>();

            taskRepository.findMyTasksInITaskDTO(user.getId(), id).forEach(iTaskDTO -> {
                simpleTasks.add(TaskMapper.iTaskToSimpleTaskDTO(iTaskDTO));
            });

            return simpleTasks;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("TaskService | findMyTasksInChip | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<SimpleTaskDTO> findOtherTasksInChip(Long id) {
        log.debug("TaskService | findOtherTasksInChip | chipId: " + id);

        User user = accountService.getAuthenticatedAccount();
        log.debug("TaskService | findOtherTasksInChip | user: " + user.toString());

        try {
            List<SimpleTaskDTO> simpleTasks = new ArrayList<>();

            taskRepository.findOtherTasksInITaskDTO(user.getId(), id).forEach(iTaskDTO -> {
                simpleTasks.add(TaskMapper.iTaskToSimpleTaskDTO(iTaskDTO));
            });
            return simpleTasks;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("TaskService | findOtherTasksInChip | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Task> findByChipIdAndRepeatEquals(Long chipId, Day day) {
        log.debug("TaskService | findByChipIdAndRepeatEquals | chipId: " + chipId);
        log.debug("TaskService | findByChipIdAndRepeatEquals | day: " + day.toString());


        try {
            return taskRepository.findByChipIdAndRepeatEquals(chipId, day);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("TaskService | findOtherTasksInChip | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
