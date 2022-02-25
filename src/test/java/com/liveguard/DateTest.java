package com.liveguard;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTest {


    @Test
    void testCurrentDay() {
        DayOfWeek day = LocalDateTime.now().getDayOfWeek();
        System.out.println(day);
    }
}
