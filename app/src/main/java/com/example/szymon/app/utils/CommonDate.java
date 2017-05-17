package com.example.szymon.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by dzaku_000 on 2017-05-17.
 */

public class CommonDate {
    private static final String ISO8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    private static final String TIME_FORMAT = "HH:mm";

    public static Date parse(String input) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(ISO8601_FORMAT, Locale.ENGLISH);
        return dateFormat.parse(input);
    }

    public static String getFormattedTime(String input) {
        Date parsedDate = null;
        try {
            parsedDate = parse(input);
        } catch (ParseException e) {
            return input;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parsedDate);

        return new SimpleDateFormat(TIME_FORMAT).format(calendar.getTime());
    }
}
