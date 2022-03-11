package com.liveguard.repository;

import com.liveguard.domain.Day;
import com.liveguard.domain.EnumDay;
import com.liveguard.util.CaseTransfer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class TaskDayRepositoryTest {

    @Autowired
    private DayRepository taskDayRepository;

    @Test
    void testSaveAll() {
        List<Day> taskDays = Arrays.asList(
                new Day(EnumDay.Saturday),
                new Day(EnumDay.Sunday),
                new Day(EnumDay.Monday),
                new Day(EnumDay.Tuesday),
                new Day(EnumDay.Wednesday),
                new Day(EnumDay.Thursday),
                new Day(EnumDay.Friday)
        );

        taskDayRepository.saveAll(taskDays);

        Iterable<Day> iterable = taskDayRepository.findAll();
        System.out.println(iterable);
        assertNotNull(iterable);
    }

    @Test
    void searchByDay() {
        DayOfWeek dayOfWeek = LocalDateTime.now().getDayOfWeek();
        String day = dayOfWeek.name();
        EnumDay day1 = EnumDay.valueOf(CaseTransfer.toLowerCaseExpectedFirstLetter(day));
        Day taskDay = taskDayRepository.findByDay(day1);

        System.out.println(taskDay.toString());
        assertNotNull(taskDay);
    }
}