package com.liveguard.service.serviceImp;

import com.liveguard.domain.Chip;
import com.liveguard.domain.Task;
import com.liveguard.domain.User;
import com.liveguard.domain.UsersTasksMute;
import com.liveguard.exception.BusinessException;
import com.liveguard.repository.UsersTasksMuteRepository;
import com.liveguard.service.ChipService;
import com.liveguard.service.UsersTasksMuteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class UsersTasksMuteServiceImp implements UsersTasksMuteService {

    private final UsersTasksMuteRepository usersTasksMuteRepository;

    public UsersTasksMuteServiceImp(UsersTasksMuteRepository usersTasksMuteRepository) {
        this.usersTasksMuteRepository = usersTasksMuteRepository;
    }


    @Override
    public void addTasksMuteDefaultToNewTask(Chip chip, Task task) {
        log.debug("UsersTasksMuteService | addTasksMuteDefaultToNewTask | chip id: " + chip.getId());
        log.debug("UsersTasksMuteService | addTasksMuteDefaultToNewTask | task id: " + task.getId());

        chip.getUsers().forEach(user -> {
            log.debug("UsersTasksMuteService | addTasksMuteDefaultToNewTask | user id: " + user.getId());
            UsersTasksMute usersTasksMute = new UsersTasksMute();
            usersTasksMute.setTask(task);
            usersTasksMute.setUser(user);
            usersTasksMute.setStatus(false);

            try {
                UsersTasksMute savedUsersTasksMute = usersTasksMuteRepository.save(usersTasksMute);
                log.debug("UsersTasksMuteService | addUsersMuteDefaultToAddNewChipToUser | savedUsersTasksMute id: " + savedUsersTasksMute.getId());
            } catch (Exception exception) {
                throw new BusinessException("Failed to save users tasks mute", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        });

    }

    @Override
    public void addUsersMuteDefaultToAddNewChipToUser(Chip chip, User user) {
        log.debug("UsersTasksMuteService | addUsersMuteDefaultToAddNewChipToUser | chip id: " + chip.getId());
        log.debug("UsersTasksMuteService | addUsersMuteDefaultToAddNewChipToUser | user id: " + user.getId());

        chip.getTasks().forEach(task -> {
            log.debug("UsersTasksMuteService | addUsersMuteDefaultToAddNewChipToUser | task id: " + task.getId());
            UsersTasksMute usersTasksMute = new UsersTasksMute();
            usersTasksMute.setTask(task);
            usersTasksMute.setUser(user);
            usersTasksMute.setStatus(false);

            try {
                UsersTasksMute savedUsersTasksMute = usersTasksMuteRepository.save(usersTasksMute);
                log.debug("UsersTasksMuteService | addUsersMuteDefaultToAddNewChipToUser | savedUsersTasksMute id: " + savedUsersTasksMute.getId());
            } catch (Exception exception) {
                throw new BusinessException("Failed to save users tasks mute", HttpStatus.INTERNAL_SERVER_ERROR);
            }

        });
    }

    @Override
    public UsersTasksMute findByTaskIdAndUserId(Long taskId, Long userId) {
        log.debug("UsersTasksMuteService | findByTaskIdAndUserId | taskId: " + taskId);
        log.debug("UsersTasksMuteService | findByTaskIdAndUserId | userId: " + userId);

        return usersTasksMuteRepository.findByTaskIdAndUserId(taskId, userId);
    }

    @Override
    @Transactional
    public void updateMuteStatusByUserIdAndTaskId(Long userId, Long taskId, Boolean status) {
        log.debug("UsersTasksMuteService | updateMuteStatusByUserIdAndTaskId | userId: " + userId);
        log.debug("UsersTasksMuteService | updateMuteStatusByUserIdAndTaskId | taskId: " + taskId);
        log.debug("UsersTasksMuteService | updateMuteStatusByUserIdAndTaskId | status: " + status);

        usersTasksMuteRepository.updateMuteStatus(userId, taskId, status);
    }
}
