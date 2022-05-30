package com.liveguard.service.serviceImp;

import com.liveguard.domain.Day;
import com.liveguard.domain.EnumDay;
import com.liveguard.exception.BusinessException;
import com.liveguard.repository.DayRepository;
import com.liveguard.service.DayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class DayServiceImp implements DayService {

    private final DayRepository dayRepository;

    public DayServiceImp(DayRepository dayRepository) {
        this.dayRepository = dayRepository;
    }

    @Override
    public List<Day> findAll() {
        log.debug("DayService | findAll");

        try {
            return dayRepository.findAll();

        } catch (Exception e) {
            e.printStackTrace();
            log.error("DayService | findAll | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Day findById(Long id) {
        log.debug("DayService | findById | id: " + id);

        try {
            return dayRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("Day not found", HttpStatus.NOT_FOUND));

        } catch (Exception e) {
            e.printStackTrace();
            log.error("DayService | findById | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Day findByDay(EnumDay enumDay) {
        log.debug("DayService | findByDay | day: " + enumDay);

        try {
            return dayRepository.findByDay(enumDay)
                    .orElseThrow(() -> new BusinessException("Day not found", HttpStatus.NOT_FOUND));

        } catch (Exception e) {
            e.printStackTrace();
            log.error("DayService | findByDay | error: " + e.getMessage());
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
