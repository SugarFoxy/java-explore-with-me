package ru.practicum.events.request.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.request.dto.RequestDto;
import ru.practicum.events.request.service.RequestPrivateService;
import ru.practicum.util.exception.handler.CustomExceptionHandler;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping( "/users/{userId}/requests")
@CustomExceptionHandler
@Slf4j
public class ControllerPrivateRequest {
    private final RequestPrivateService service;

    @Autowired
    public ControllerPrivateRequest(RequestPrivateService service) {
        this.service = service;
    }

    @GetMapping
    public List<RequestDto> getUserRequests(@NotNull @PathVariable Long userId) {
        log.info("Получен запрос на получение информации о заявках текущего пользователя на участие в чужих событиях!");
        return service.getRequestByUserId(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDto createRequests(@NotNull @PathVariable Long userId,
                                     @NotNull @RequestParam Long eventId) {
        log.info("Получен запрос на добавление запроса " +
                "от текущего пользователя id = {} на участие в событии id = {}", userId, eventId);
        return service.createRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public RequestDto cancel(@NotNull @PathVariable Long userId,
                             @NotNull @PathVariable Long requestId) {
        log.info("Получен запрос на отмену своего запроса id = {} на участие в событии ", requestId);
        return service.cancelRequest(userId, requestId);
    }
}

