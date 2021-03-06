package com.turlygazhy.tool;

import com.turlygazhy.entity.Week;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Yerassyl_Turlygazhy on 06-Mar-17.
 */
public class DateUtil {
    private static SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy");

    public static Date getThisMonday() {
        Date date = new Date();
        while (!date.toString().contains("Mon")) {
            date.setDate(date.getDate() - 1);
        }
        return date;
    }

    public static Date getThisSunday() {
        Date date = new Date();
        while (!date.toString().contains("Sun")) {
            date.setDate(date.getDate() + 1);
        }
        return date;
    }

    public static Date getNextMonth() {
        Date date = new Date();
        date.setDate(date.getDate() + 1);
        while (date.getDate() != 1) {
            date.setDate(date.getDate() + 1);
        }
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(1);
        return date;
    }

    public static Date getNextWeek() {
        Date date = new Date();
        date.setDate(date.getDate() + 1);
        while (!date.toString().contains("Mon")) {
            date.setDate(date.getDate() + 1);
        }
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(1);
        return date;
    }

    public static Date getNextNight() {
        Date date = new Date();
        date.setDate(date.getDate() + 1);
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(1);
        return date;
    }

    public static boolean checkHour(int hour) {
        Date date = new Date();
        return date.getHours() == hour;
    }

    public static Date getHour(int hour) {
        Date date = new Date();
        if (date.getHours() >= hour) {
            date.setDate(date.getDate() + 1);
        }
        date.setHours(hour);
        date.setMinutes(0);
        date.setSeconds(1);
        return date;
    }

    public static boolean isNewWeek() {
        Date date = new Date();
        return date.toString().contains("Mon");
    }

    public static boolean isNewMonth() {
        return new Date().getDate() == 1;
    }

    public static String getPastDay() {
        Date date = new Date();
        date.setDate(date.getDate() - 1);
        return format.format(date);
    }

    public static List<String> getLastMonthSundaysListAsString() {
        List<String> result = new ArrayList<>();
        Date date = new Date();
        date.setDate(date.getDate() - 1);
        int month = date.getMonth();
        while (true) {
            if (month > date.getMonth()) {
                break;
            }

            String dateAsString = date.toString();
            if (dateAsString.contains("Sun")) {
                result.add(format.format(date));
            }
            date.setDate(date.getDate() - 1);
        }
        return result;
    }

    public static Date getThisMonthStartDay() {
        return null;
    }

    public static List<Week> getLastMonthWeeks() {
        List<Week> weeks = new ArrayList<>();
        Date date = new Date();
        date.setDate(date.getDate() - 1);
        int month = date.getMonth();
        boolean monthEnded = false;
        Week week = null;
        List<Date> days = new ArrayList<>();
        while (true) {
            String dateAsString = date.toString();
            if (month > date.getMonth()) {
                monthEnded = true;
            }
            if (dateAsString.contains("Sun")) {
                if (week != null) {
                    week.setDays(days);
                    weeks.add(week);
                    days = new ArrayList<>();
                }
                if (monthEnded) {
                    break;
                }
                week = new Week();
            }
            if (week != null) {
                days.add((Date) date.clone());
            }
            date.setDate(date.getDate() - 1);
        }
        return weeks;
    }

    public static Date getLastMonthFirstDay() {
        Date date = new Date();
        date.setMonth(date.getMonth() - 1);
        date.setDate(1);
        return date;
    }

    public static Date getLastMonthLastDay() {
        Date date = new Date();
        int month = date.getMonth();//todo а что если январь
        while (true) {
            date.setDate(date.getDate() - 1);
            if (month > date.getMonth()) {
                break;
            }
        }
        return date;
    }

    public static Date getLastWeekMonday() {
        Date date = new Date();
        date.setDate(date.getDate() - 1);
        while (!date.toString().contains("Mon")) {
            date.setDate(date.getDate() - 1);
        }
        return date;
    }

    public static Date getLastWeekSunday() {
        Date date = new Date();
        while (!date.toString().contains("Sun")) {
            date.setDate(date.getDate() - 1);
        }
        return date;
    }

    public static String getMonthInRussian(int month) {
        String string = null;

        switch (month){
            case 1:
                string = "января";
                break;
            case 2:
                string = "февраля";
                break;
            case 3:
                string = "марта";
                break;
            case 4:
                string = "апреля";
                break;
            case 5:
                string = "мая";
                break;
            case 6:
                string = "июня";
                break;
            case 7:
                string = "июля";
                break;
            case 8:
                string = "августа";
                break;
            case 9:
                string = "сентября";
                break;
            case 10:
                string = "октября";
                break;
            case 11:
                string = "ноября";
                break;
            case 12:
                string = "декабря";
                break;
        }

        return string;
    }

    public static String dayOfWeekInRussian(int day){
        String string = null;
        switch (day){
            case 1:
                string = "понедельник";
                break;
            case 2:
                string = "вторник";
                break;
            case 3:
                string = "среда";
                break;
            case 4:
                string = "четверг";
                break;
            case 5:
                string = "пятница";
                break;
            case 6:
                string = "суббота";
                break;
            case 7:
                string = "воскресенье";
                break;
        }
        return string;
    }

    public static Date getNextMonthEnd(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.DAY_OF_MONTH,Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    public static Date getLastDayOfThisMonth(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.DAY_OF_MONTH,Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

}