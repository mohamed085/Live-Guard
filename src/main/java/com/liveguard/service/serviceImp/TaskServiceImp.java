package com.liveguard.service.serviceImp;

import com.liveguard.domain.*;
import com.liveguard.dto.*;
import com.liveguard.exception.BusinessException;
import com.liveguard.mapper.TaskMapper;
import com.liveguard.repository.*;
import com.liveguard.service.AccountService;
import com.liveguard.service.TaskService;
import com.liveguard.service.UserTaskMuteService;
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

    private final DayRepository dayRepository;
    private final TaskRepository taskRepository;
    private final ChipRepository chipRepository;
    private final AccountService accountService;
    private final ChipUserRepository chipUserRepository;
    private final UserTaskMuteService userTaskMuteService;
    private final UserRepository userRepository;

    public TaskServiceImp(DayRepository dayRepository, TaskRepository taskRepository,
                          ChipRepository chipRepository, AccountService accountService,
                          ChipUserRepository chipUserRepository, UserTaskMuteService userTaskMuteService, UserRepository userRepository) {
        this.dayRepository = dayRepository;
        this.taskRepository = taskRepository;
        this.chipRepository = chipRepository;
        this.accountService = accountService;
        this.chipUserRepository = chipUserRepository;
        this.userTaskMuteService = userTaskMuteService;
        this.userRepository = userRepository;
    }

    @Override
    public TaskDTO addTask(Long chipId, TaskDTO taskDTO) {
        log.debug("TaskService | addTask | chipId: " + chipId);
        log.debug("TaskService | addTask | taskDTO: " + taskDTO.getName());

        User user = accountService.getAuthenticatedAccount();
        log.debug("TaskService | addTask | user: " + user.toString());

        try {
            Chip chip = chipRepository.findById(chipId)
                    .orElseThrow(() -> new BusinessException("Chip not found", HttpStatus.NOT_FOUND));

            log.debug("TaskService | addTask | chip: " + chip.toString());

            ChipUser chipUser = chipUserRepository.findByChipIdAndUserId(chip.getId(), user.getId())
                    .orElseThrow(() -> new BusinessException("This chip not belong to you", HttpStatus.BAD_REQUEST));

            log.debug("TaskService | addTask | chipUser: " + chipUser.toString());

            Task task = TaskMapper.taskDTOToTask(taskDTO);

            Set<Day> repeat = new HashSet<>();
            taskDTO.getRepeatId().forEach(dayId -> repeat.add(dayRepository.findById(dayId).get()));
            task.setRepeat(repeat);
            task.setChip(chip);
            task.setAddByUser(user);
            task.setCreateDate(LocalDateTime.now());

            log.debug("TaskService | addTask | task: " + task.getName());

            Task savedTask = taskRepository.save(task);
            log.debug("TaskService | addTask | savedTask: " + savedTask.getName());

            userTaskMuteService.addTasksMuteDefaultToNewTask(task, chip);

            return TaskMapper.taskToTaskDTO(savedTask, true);
        } catch (Exception e) {
            log.error("TaskService | addTask | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public TaskDTO findById(Long id) {
        User user = accountService.getAuthenticatedAccount();
        log.debug("TaskService | findAllInChip | user: " + user.toString());

        try {
             ITaskDTO task = taskRepository.findByIdInITaskDTO(id, user.getId())
                     .orElseThrow(() -> new BusinessException("TAsk not found", HttpStatus.BAD_REQUEST));

             List<ITaskRepeat> repeats = taskRepository.findAllRepeatDaysInTask(task.getId());
             return TaskMapper.iTaskToTaskDTO(task, repeats);
        } catch (Exception e) {
            log.error("TaskService | findById | error: " + e.getMessage());
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
            log.error("TaskService | findOtherTasksInChip | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
