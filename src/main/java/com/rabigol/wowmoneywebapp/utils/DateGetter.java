package com.rabigol.wowmoneywebapp.utils;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * Created by Artur.Ziaev on 08.11.2016.
 */
public class DateGetter {
    public static String dateGetter() {
        String currentDateAndTime;
        currentDateAndTime = (Calendar.getInstance().getTime()).toString();
        return currentDateAndTime;
    }
}
