package com.chengxuxiaoba.video.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static Date addYEAR(Date date, Integer value) {
        return add(date, Calendar.YEAR, value);
    }

    public static Date addMonth(Date date, Integer value) {
        return add(date, Calendar.MONTH, value);
    }

    public static Date addDATE(Date date, Integer value) {
        return add(date, Calendar.DATE, value);
    }

    public static Date addHOUR(Date date, Integer value) {
        return add(date, Calendar.HOUR, value);
    }

    public static Date addMINUTE(Date date, Integer value) {
        return add(date, Calendar.MINUTE, value);
    }

    public static Date addSECOND(Date date, Integer value) {
        return add(date, Calendar.SECOND, value);
    }

    public static Date add(Date date, Integer addItem, Integer value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(addItem, value);

        return calendar.getTime();
    }
}
