package com.liveguard;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;

public class DateTest {


    @Test
    void testCurrentDay() {
        DayOfWeek day = LocalDateTime.now().getDayOfWeek();
        System.out.println(day.name().toLowerCase(Locale.ROOT));

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime.toLocalTime());

        LocalTime time = LocalTime.now();

        System.out.println(LocalTime.MIDNIGHT);
        System.out.println(time);
        System.out.println(time.isAfter(LocalTime.MIDNIGHT));
    }
}
