package com.liveguard.util;

import java.util.Random;

public class GenerateCodeUtil {

    public static int generateRandomDigits(int n) {
        int m = (int) Math.pow(10, n - 1);
        return m + new Random().nextInt(9 * m);
    }

}
