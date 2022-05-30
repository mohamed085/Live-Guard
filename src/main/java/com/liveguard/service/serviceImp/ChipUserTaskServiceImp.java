package com.liveguard.service.serviceImp;

import com.liveguard.domain.*;
import com.liveguard.exception.BusinessException;
import com.liveguard.repository.ChipUserRepository;
import com.liveguard.repository.ChipUserTaskRepository;
import com.liveguard.repository.TaskRepository;
import com.liveguard.service.AccountService;
import com.liveguard.service.ChipUserTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ChipUserTaskServiceImp implements ChipUserTaskService {

    private final ChipUserTaskRepository chipUserTaskRepository;
    private final ChipUserRepository chipUserRepository;
    private final AccountService accountService;
    private final TaskRepository taskRepository;

    public ChipUserTaskServiceImp(ChipUserTaskRepository chipUserTaskRepository, ChipUserRepository chipUserRepository,
                                  AccountService accountService, TaskRepository taskRepository) {
        this.chipUserTaskRepository = chipUserTaskRepository;
        this.chipUserRepository = chipUserRepository;
        this.accountService = accountService;
        this.taskRepository = taskRepository;
    }


    @Override
    public void addChipUserTaskForNewTask(Task task) {
        log.debug("ChipUserTaskService | addChipUserTaskForNewTask | task: " + task.getId());

        User user = accountService.getAuthenticatedAccount();

        List<ChipUser> chipUsers = chipUserRepository.findAllByChipId(task.getChip().getId());

        List<ChipUserTask> chipUserTasks = new ArrayList<>();
        chipUsers.forEach(chipUser -> {
            ChipUserTask chipUserTask = new ChipUserTask();
            chipUserTask.setChipUser(chipUser);
            chipUserTask.setTask(task);
            chipUserTask.setStatus(ChipUserTaskStatus.MUTE);

            if (chipUser.getUser().getId().equals(user.getId())) {
                chipUserTask.setStatus(ChipUserTaskStatus.UNMUTE);
            }


            log.debug("ChipUserTaskService | addChipUserTaskForNewTask | chipUserTask: " + chipUserTask.toString());
            chipUserTasks.add(chipUserTask);
        });

        chipUserTaskRepository.saveAll(chipUserTasks);
    }

    @Override
    public void addChipUserTaskForNewChipUser(ChipUser chipUser) {
        log.debug("ChipUserTaskService | addChipUserTaskForNewChipUser | chipUser: " + chipUser.getId());

        List<Task> tasks = taskRepository.findAllByChipId(chipUser.getChip().getId());
        List<ChipUserTask> chipUserTasks = new ArrayList<>();

        tasks.forEach(task -> {
            ChipUserTask chipUserTask = new ChipUserTask();
            chipUserTask.setChipUser(chipUser);
            chipUserTask.setTask(task);
            chipUserTask.setStatus(ChipUserTaskStatus.MUTE);

            log.debug("ChipUserTaskService | addChipUserTaskForNewTask | chipUserTask: " + chipUserTask.toString());
            chipUserTasks.add(chipUserTask);
        });

        chipUserTaskRepository.saveAll(chipUserTasks);
    }

    @Override
    public ChipUserTask findByTaskIdAndAuthenticatedUser(Long taskId) {
        log.debug("ChipUserTaskService | findByTaskIdAndAuthenticatedUser | taskId: " + taskId);

        User user = accountService.getAuthenticatedAccount();
        log.debug("ChipUserTaskService | findByTaskIdAndAuthenticatedUser | user: " + user.getId());

        return chipUserTaskRepository.findByTaskIdAndUserId(taskId, user.getId())
                .orElseThrow(() -> new BusinessException("Task not found", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public void deleteByTaskIdAndAuthenticatedUser(Long taskId) {
        log.debug("ChipUserTaskService | deleteByTaskIdAndAuthenticatedUser | taskId: " + taskId);

        User user = accountService.getAuthenticatedAccount();
        log.debug("ChipUserTaskService | deleteByTaskIdAndAuthenticatedUser | user: " + user.getId());

        ChipUserTask chipUserTask = findByTaskIdAndAuthenticatedUser(taskId);
        chipUserTaskRepository.delete(chipUserTask);
    }

    @Override
    public List<ChipUserTask> findAllTasksForAuthenticatedUserByChipId(Long chipId) {
        log.debug("ChipUserTaskService | findAllTasksForAuthenticatedUserByChipId | chipId: " + chipId);

        User user = accountService.getAuthenticatedAccount();
        log.debug("ChipUserTaskService | findAllTasksForAuthenticatedUserByChipId | user: " + user.getId());

        return chipUserTaskRepository.findAllByChipAndUser(chipId, user.getId());
    }
}
