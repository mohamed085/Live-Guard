package com.liveguard.util;

import java.util.Locale;

public class CaseTransfer {

    public static String toLowerCaseExpectedFirstLetter(String str) {
        return str.substring(0, 1) + str.substring(1, str.length()).toLowerCase(Locale.ROOT);
    }
}
