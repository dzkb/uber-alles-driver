package com.example.tomek.uberallesdriver.utils;


import java.util.Calendar;

public class DateHelper {

    private static final int GRATER = 1;
    private static final int LOWER = -1;
    private static final int EQUAL = 0;

    public static boolean compareTime(Calendar cal1, Calendar cal2) {
        int hoursFromCal1 = cal1.get(Calendar.HOUR_OF_DAY);
        int minutesFromCal1 = cal1.get(Calendar.MINUTE);

        int hoursFromCal2 = cal2.get(Calendar.HOUR_OF_DAY);
        int minutesFromCal2 = cal2.get(Calendar.MINUTE);

        if (hoursFromCal1 < hoursFromCal2) return false;
        else {
            if (hoursFromCal1 > hoursFromCal2) return true;
            else return minutesFromCal1 >= minutesFromCal2;
        }
    }

    public static int compareDate(Calendar cal1, Calendar cal2) {
        int dayCal1 = cal1.get(Calendar.DAY_OF_MONTH);
        int monthCal1 = cal1.get(Calendar.MONTH);
        int yearCal1 = cal1.get(Calendar.YEAR);

        int dayCal2 = cal2.get(Calendar.DAY_OF_MONTH);
        int monthCal2 = cal2.get(Calendar.MONTH);
        int yearCal2 = cal2.get(Calendar.YEAR);

        if (yearCal1 < yearCal2) return LOWER;
        else {
            if (yearCal1 > yearCal2) return GRATER;
            else {
                if (monthCal1 > monthCal2) return GRATER;
                else {
                    if (monthCal1 < monthCal2) return LOWER;
                    else if (dayCal1 == dayCal2) return EQUAL;
                    else if (dayCal1 < dayCal2) return LOWER;
                    else return GRATER;
                }
            }
        }
    }
}