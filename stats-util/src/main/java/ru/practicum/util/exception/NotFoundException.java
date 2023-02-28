package ru.practicum.util.exception;

import org.springframework.http.HttpStatus;
import ru.practicum.util.DateTimeUtils;

import java.time.LocalDateTime;

public class NotFoundException extends RuntimeException{
    private final String status;
    private final String timestamp;

    public NotFoundException(String message) {
        super(message);
        status = HttpStatus.NOT_FOUND.toString();
        timestamp = DateTimeUtils.getDateTime(LocalDateTime.now());
    }
}
