package ru.practicum.util.exception.handler;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomExceptionHandler {
}
