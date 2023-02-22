package ru.practicum.util;

import ru.practicum.util.exception.BadRequestException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static LocalDateTime parseDate(String dateString) {
        if (dateString == null) {
            return null;
        }
        try {
            return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
        } catch (RuntimeException ex) {
            throw new BadRequestException("Не корректный формат даты", ex);
        }
    }

    public static String getDateTime(LocalDateTime localDateTime) {
        return DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(localDateTime);
    }
}
