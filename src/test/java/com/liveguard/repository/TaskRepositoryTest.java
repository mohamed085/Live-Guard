package com.liveguard.repository;

import com.liveguard.domain.EnumDay;
import com.liveguard.domain.Day;
import com.liveguard.util.CaseTransfer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private DayRepository taskDayRepository;

    @Test
    void findByChipId() {
    }

    @Test
    void findByChipIdAndAddByUserId() {
    }

    @Test
    void findByChipIdAndRepeatContaining() {
        DayOfWeek dayOfWeek = LocalDateTime.now().getDayOfWeek();
        String day = dayOfWeek.name();
        EnumDay day1 = EnumDay.valueOf(CaseTransfer.toLowerCaseExpectedFirstLetter(day));
        Day day2 = taskDayRepository.findByDay(day1);

        System.out.println(taskRepository.findByChipIdAndRepeatEquals(1L, day2));
    }
}