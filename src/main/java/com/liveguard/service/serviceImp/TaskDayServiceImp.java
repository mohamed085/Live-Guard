package com.liveguard.service.serviceImp;

import com.liveguard.domain.TaskDay;
import com.liveguard.exception.BusinessException;
import com.liveguard.repository.TaskDayRepository;
import com.liveguard.service.TaskDayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class TaskDayServiceImp implements TaskDayService {

    private final TaskDayRepository taskDayRepository;

    public TaskDayServiceImp(TaskDayRepository taskDayRepository) {
        this.taskDayRepository = taskDayRepository;
    }

    @Override
    public List<TaskDay> findAll() {
        log.debug("TaskDayService | findAll");

        try {
            return taskDayRepository.findAll();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public TaskDay findById(Long id) {
        log.debug("TaskDayService | findById | id: " + id);

        try {
            return taskDayRepository
                    .findById(id)
                    .orElseThrow(() -> new BusinessException("This day not found", HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
