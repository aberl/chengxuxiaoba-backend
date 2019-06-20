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

    public static int differentDays(Date originDate, Date targetDate) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(originDate);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(targetDate);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
    }
}
