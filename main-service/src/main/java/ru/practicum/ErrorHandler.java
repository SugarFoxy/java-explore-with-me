package ru.practicum;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.util.DateTimeUtils;
import ru.practicum.util.exception.BadRequestException;
import ru.practicum.util.exception.ConflictException;
import ru.practicum.util.exception.NotFoundException;
import ru.practicum.util.exception.handler.CustomExceptionHandler;
import ru.practicum.util.exception.handler.ErrorResponse;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice(annotations = CustomExceptionHandler.class)
public class ErrorHandler {

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(final BadRequestException e) {
        log.warn(e.getMessage());
        return new ErrorResponse(
                e.getStatus(),
                e.getMessage(),
                e.getTimestamp()
        );
    }

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.warn(e.getMessage());
        String[] allErrors = e.getAllErrors().toString().split(";");
        String massage = allErrors[allErrors.length - 1];
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.toString(),
                massage,
                DateTimeUtils.getDateTime(LocalDateTime.now())
        );
    }

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        log.warn(e.getMessage());
        return new ErrorResponse(
                e.getStatus(),
                e.getMessage(),
                e.getTimestamp()
        );
    }

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflictException(final ConflictException e) {
        log.warn(e.getMessage());
        return new ErrorResponse(
                e.getStatus(),
                e.getMessage(),
                e.getTimestamp()
        );
    }

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(final IllegalArgumentException e) {
        log.warn(e.getMessage());

        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.toString(),
                e.getMessage(),
                DateTimeUtils.getDateTime(LocalDateTime.now())
        );
    }
}
