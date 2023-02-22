package ru.practicum.util.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import ru.practicum.util.DateTimeUtils;

import java.time.LocalDateTime;

@Getter
public class BadRequestException extends RuntimeException {
    private final String status;
    private final String timestamp;

//    public BadRequestException(String message) {
//        super(message);
//        status = HttpStatus.BAD_REQUEST.toString();
//        timestamp = DateTimeUtils.getDateTime(LocalDateTime.now());
//    } пригодиться в будущем

    public BadRequestException(String message, Throwable e) {
        super(message, e);
        status = HttpStatus.BAD_REQUEST.toString();
        timestamp = DateTimeUtils.getDateTime(LocalDateTime.now());

    }
}
