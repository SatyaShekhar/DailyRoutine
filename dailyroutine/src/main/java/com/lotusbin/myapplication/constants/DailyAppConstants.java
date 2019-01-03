package com.lotusbin.myapplication.constants;

import android.content.Intent;
import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DailyAppConstants {

    public static String getDateWithddMMYYYYFormat(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(TaskProperties.DATE_FORMAT);
       return formatter.format(new Date()).toString();
    }

    public static String getDateWithddHHMMSSFormat(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(TaskProperties.TIME_FORMAT_24);
        return formatter.format(new Date()).toString();
    }


    public static String nextDay(String dateString) {
       return getDayAfterXDays(dateString, 1);
    }

    public static String getYesterday(String dateString) {
        return getDayAfterXDays(dateString, -1);
    }

    private static String getDayAfterXDays(String dateString, int afterDays) {
        SimpleDateFormat formatter = new SimpleDateFormat(TaskProperties.DATE_FORMAT);
        try {
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(formatter.parse(dateString));
            calendar.add(Calendar.DAY_OF_YEAR, afterDays);
            return formatter.format(calendar.getTime());
        } catch (ParseException e) {
            throw new IllegalStateException("Unable to handle parameter to find next date");
        }
    }

    /**
     * Return the date retrieved from extra parameter, else return string from current date
     *
     * @param intent
     * @return
     */
    @NonNull
    public static String getDateAsddMMYYYYFormatFromIntent(Intent intent) {
        String string = intent.getStringExtra(TaskProperties.DATE_PROPERTY_STRING);
        if (string != null && !string.isEmpty()) {
            return string;
        }
        return getDateWithddMMYYYYFormat(new Date());
    }

    /**
     * Construct the Date object from the parameters and returns the date in mili seconds
     *
     * @param year
     * @param month the month from 1 (JAN) - 12 (DEC)
     * @param day
     * @param hour
     * @param min
     *
     * @return Date created using the parameters
     */
    public static Date getTimeAsDate(int year, int month, int day, int hour, int min, int sec) {
        try {
            StringBuilder builder = new StringBuilder("");
            builder.append(year).append("-").append(month - 1).append("-").append(day).append(" ");
            builder.append(hour).append(":").append(min).append(":").append(sec);
            return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(builder.toString());
        } catch (ParseException e) {
            throw new IllegalStateException("You have entered some wrong data... please verify", e);
        }
    }


    /**
     *
     * @param startDate the date in dd/mm/yyyy format
     * @param startTime the time in hh:mm:se format
     *
     * @return the date as java.utils.Date
     */
    public static Date getTimeAsDate(String startDate, String startTime) {
        String[] dates = startDate.split("/");
        String[] times = startTime.split(":");
        return getTimeAsDate(Integer.valueOf(dates[0]), Integer.valueOf(dates[1]), Integer.valueOf(dates[2]),
                Integer.valueOf(times[0]), Integer.valueOf(times[1]), Integer.valueOf(times[2]));

    }
}
