package ru.practicum.util.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import ru.practicum.util.DateTimeUtils;

import java.time.LocalDateTime;

@Getter
public class ConflictException extends RuntimeException {
    private final String status;
    private final String timestamp;

    public ConflictException(String message) {
        super(message);
        status = HttpStatus.CONFLICT.toString();
        timestamp = DateTimeUtils.getDateTime(LocalDateTime.now());
    }
}
