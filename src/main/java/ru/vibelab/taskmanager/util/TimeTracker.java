package ru.vibelab.taskmanager.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TimeTracker {
    private static final int MINUTES_PER_HOUR = 60;

    public static int HOURS_PER_DAY = 8;

    public static int DAYS_PER_WEEK = 5;

    private static final int WEEKS_PER_MONTH = 3;

    private static final int MINUTES_PER_DAY = MINUTES_PER_HOUR * HOURS_PER_DAY;

    private static final int MINUTES_PER_WEEK = MINUTES_PER_DAY * DAYS_PER_WEEK;

    private static final int MINUTES_PER_MONTH = MINUTES_PER_DAY * DAYS_PER_WEEK * WEEKS_PER_MONTH;

    public static void setHoursPerDay(int hoursPerDay) {
        HOURS_PER_DAY = hoursPerDay;
    }

    public static void setDaysPerWeek(int daysPerWeek) {
        DAYS_PER_WEEK = daysPerWeek;
    }

    public static String convertToString(long minutes) {
        StringBuilder str = new StringBuilder();

        long months = minutes / MINUTES_PER_MONTH;
        minutes %= MINUTES_PER_MONTH;

        long weeks = minutes / MINUTES_PER_WEEK;
        minutes %= MINUTES_PER_WEEK;

        long days = minutes / MINUTES_PER_DAY;
        minutes %= MINUTES_PER_DAY;

        long hours = minutes / MINUTES_PER_HOUR;
        minutes %= MINUTES_PER_HOUR;

        if (months != 0) {
            str.append(months).append("м ");
        }

        if (weeks != 0) {
            str.append(weeks).append("н ");
        }

        if (days != 0) {
            str.append(days).append("д ");
        }

        if (hours != 0) {
            str.append(hours).append("ч ");
        }

        if (minutes != 0) {
            str.append(minutes).append("мин");
        }

        return str.toString().trim();
    }

    public static Long stringToMinutes(String stringWithTime) {

        if(stringWithTime == null) return null;

        String[] splittedString = stringWithTime.split(" ");
        long months, weeks, hours, days, minutes;
        months = weeks = hours = days = minutes = 0L;


        for (String str : splittedString) {
            if (str.endsWith("м")) months = extractTimeUnit(str, "м");
            if (str.endsWith("н") && !str.contains("мин")) weeks = extractTimeUnit(str, "н");
            if (str.endsWith("д")) days = extractTimeUnit(str, "д");
            if (str.endsWith("ч")) hours = extractTimeUnit(str, "ч");
            if (str.endsWith("мин")) minutes = extractTimeUnit(str, "мин");
        }

        minutes += hours * MINUTES_PER_HOUR + days * MINUTES_PER_DAY +
                weeks * MINUTES_PER_WEEK + months * MINUTES_PER_MONTH;

        return minutes;
    }

    private long extractTimeUnit(String stringPart, String timeUnit) {
        return Long.parseLong(stringPart.replace(timeUnit, ""));
    }


}
