package com.liveguard.util;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class CaseTransferTest {

    @Test
    void toLowerCaseExpectedFirstLetter() {
        DayOfWeek day = LocalDateTime.now().getDayOfWeek();
        String str1 = day.name();
        String str2 = CaseTransfer.toLowerCaseExpectedFirstLetter(str1);
        System.out.println(str2);
    }
}