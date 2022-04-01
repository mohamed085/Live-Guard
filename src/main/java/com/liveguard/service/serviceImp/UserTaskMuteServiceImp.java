package com.liveguard.service.serviceImp;

import com.liveguard.domain.Chip;
import com.liveguard.domain.Task;
import com.liveguard.domain.User;
import com.liveguard.domain.UserTaskMute;
import com.liveguard.exception.BusinessException;
import com.liveguard.payload.ApiResponse;
import com.liveguard.repository.TaskRepository;
import com.liveguard.repository.UserTaskMuteRepository;
import com.liveguard.service.AccountService;
import com.liveguard.service.UserTaskMuteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserTaskMuteServiceImp implements UserTaskMuteService {

    private final UserTaskMuteRepository userTaskMuteRepository;
    private final TaskRepository taskRepository;
    private final AccountService accountService;

    public UserTaskMuteServiceImp(UserTaskMuteRepository userTaskMuteRepository, TaskRepository taskRepository,
                                  AccountService accountService) {
        this.userTaskMuteRepository = userTaskMuteRepository;
        this.taskRepository = taskRepository;
        this.accountService = accountService;
    }

    @Override
    public void addTasksMuteDefaultToNewUser(Long chipId, User user) {
        log.debug("UserTaskMuteService | addTasksMuteDefaultToNewUser | chipId: " + chipId);
        log.debug("UserTaskMuteService | addTasksMuteDefaultToNewUser | user: " + user.getId());

        try {
            List<Task> tasks = taskRepository.findAllByChipId(chipId);
            List<UserTaskMute> userTaskMutes = new ArrayList<>();

            tasks.forEach(task -> {
                log.debug("UserTaskMuteService | addTasksMuteDefaultToNewUser | task: " + task.toString());

                UserTaskMute userTaskMute = new UserTaskMute();
                userTaskMute.setStatus(false);
                userTaskMute.setTask(task);
                userTaskMute.setUser(user);

                log.debug("UserTaskMuteService | addTasksMuteDefaultToNewUser | userTaskMute: " + userTaskMute.toString());
                userTaskMutes.add(userTaskMute);
            });

            userTaskMuteRepository.saveAll(userTaskMutes);

        } catch (Exception e) {
            log.error("UserTaskMuteService | addTasksMuteDefaultToNewUser | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void addTasksMuteDefaultToNewTask(Task task, Chip chip) {
        log.debug("UserTaskMuteService | addTasksMuteDefaultToNewTask | task: " + task.toString());
        log.debug("UserTaskMuteService | addTasksMuteDefaultToNewTask | chip: " + chip.getId());

        try {
            List<UserTaskMute> userTaskMutes = new ArrayList<>();
            chip.getUsers().forEach(chipUser -> {
                UserTaskMute userTaskMute = new UserTaskMute();
                userTaskMute.setStatus(false);
                userTaskMute.setTask(task);
                userTaskMute.setUser(chipUser.getUser());

                log.debug("UserTaskMuteService | addTasksMuteDefaultToNewTask | userTaskMute: " + userTaskMute.toString());
                userTaskMutes.add(userTaskMute);
            });

            userTaskMuteRepository.saveAll(userTaskMutes);

        } catch (Exception e) {
            log.error("UserTaskMuteService | addTasksMuteDefaultToNewTask | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ApiResponse updateMuteStatus(Long taskId, Boolean mute) {
        log.debug("UserTaskMuteService | updateMuteStatus | taskId: " + taskId);
        log.debug("UserTaskMuteService | updateMuteStatus | mute: " + mute);

        User user = accountService.getAuthenticatedAccount();
        log.debug("UserTaskMuteService | updateMuteStatus | user: " + user.toString());

        try {
            userTaskMuteRepository.updateMuteStatus(user.getId(), taskId, mute);

            return new ApiResponse(true, "Task mute updated successfully");
        } catch (Exception e) {
            log.error("UserTaskMuteService | updateMuteStatus | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
