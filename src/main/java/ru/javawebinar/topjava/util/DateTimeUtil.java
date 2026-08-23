package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<? super T>> boolean isBetweenHalfOpen(T value, T start, T end) {
        return value.compareTo(start) >= 0 && value.compareTo(end) < 0;
    }

    public static LocalDateTime atStartOfDayOrMin(LocalDate startDate) {
        if (startDate == null) {
            return LocalDateTime.MIN;
        }
        return startDate.atStartOfDay();
    }

    public static LocalDateTime atStartOfNextDayOrMax(LocalDate endDate) {
        if (endDate == null) {
            return LocalDateTime.MAX;
        }
        return endDate.plusDays(1).atStartOfDay();
    }

    public static LocalTime defaultToMin(LocalTime startTime) {
        if (startTime == null) {
            return LocalTime.MIN;
        }
        return startTime;
    }

    public static LocalTime defaultToMax(LocalTime endTime) {
        if (endTime == null) {
            return LocalTime.MAX;
        }
        return endTime;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}