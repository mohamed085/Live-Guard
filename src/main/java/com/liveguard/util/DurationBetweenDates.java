package com.liveguard.util;

import java.time.LocalDateTime;
import java.util.Date;

public class DurationBetweenDates {

    public static String findDifference(LocalDateTime localDateTime) {
        Date date = DateConverterUtil.convertLocalDateTimeToDate(localDateTime);
        Date dateNow = new Date();

        long difference_In_Time = dateNow.getTime() - date.getTime();
        long difference_In_Seconds = (difference_In_Time / 1000) % 60;

        long difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60;

        long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;

        long difference_In_Years = (difference_In_Time / (1000l * 60 * 60 * 24 * 365));

        long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;

        String difference = "about ";

        if (difference_In_Years > 0) {
            difference += difference_In_Years + " years";
        }
        else {
            if (difference_In_Days > 0) {
                difference += difference_In_Days + " days";
            }
            else {
                if (difference_In_Hours > 0) {
                    difference += difference_In_Hours + " hours";
                }
                else {
                    if (difference_In_Minutes > 0) {
                        difference += difference_In_Minutes + " minutes";
                    }
                    else {
                        difference += difference_In_Seconds + " seconds";
                    }
                }
            }
        }


        return difference;
    }
}
