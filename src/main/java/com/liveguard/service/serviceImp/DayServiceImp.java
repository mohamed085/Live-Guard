package com.liveguard.service.serviceImp;

import com.liveguard.domain.Day;
import com.liveguard.exception.BusinessException;
import com.liveguard.repository.DayRepository;
import com.liveguard.service.DayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DayServiceImp implements DayService {

    private final DayRepository taskDayRepository;

    public DayServiceImp(DayRepository taskDayRepository) {
        this.taskDayRepository = taskDayRepository;
    }

    @Override
    public List<Day> findAll() {
        log.debug("TaskDayService | findAll");

        try {
            return taskDayRepository.findAll();
        } catch (Exception e) {
            log.error("TaskDayService | findAll | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Day findById(Long id) {
        log.debug("TaskDayService | findById | id: " + id);

        try {
            return taskDayRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("This day not found", HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            log.error("TaskDayService | findById | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
