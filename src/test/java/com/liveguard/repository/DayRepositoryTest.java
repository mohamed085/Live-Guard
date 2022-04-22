package com.liveguard.repository;

import com.liveguard.domain.Day;
import com.liveguard.domain.EnumDay;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class DayRepositoryTest {

    @Autowired
    DayRepository dayRepository;

    @Test
    void saveAll() {
        List<Day> days = Arrays.asList(
                new Day(EnumDay.Saturday),
                new Day(EnumDay.Sunday),
                new Day(EnumDay.Monday),
                new Day(EnumDay.Tuesday),
                new Day(EnumDay.Wednesday),
                new Day(EnumDay.Thursday),
                new Day(EnumDay.Friday)
        );

        List<Day> savedDays = dayRepository.saveAll(days);
        System.out.println(savedDays);
        assertEquals(savedDays.size(), days.size());
    }

    @Test
    void findByDay() {
    }
}