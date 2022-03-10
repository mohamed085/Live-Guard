package com.liveguard.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DistanceCalculatorTest {

    @Test
    void distance() {
        System.out.println(DistanceCalculator.distance(32.9697, -96.80322, 29.46786, -98.53506) + " Miles\n");
    }
}