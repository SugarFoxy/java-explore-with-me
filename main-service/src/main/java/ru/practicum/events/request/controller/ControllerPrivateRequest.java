package ru.practicum.events.request.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.request.dto.RequestDto;
import ru.practicum.events.request.service.RequestPrivateService;
import ru.practicum.util.exception.handler.CustomExceptionHandler;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/requests")
@CustomExceptionHandler
public class ControllerPrivateRequest {
    private final RequestPrivateService service;

    @Autowired
    public ControllerPrivateRequest(RequestPrivateService service) {
        this.service = service;
    }

    @GetMapping
    public List<RequestDto> getUserRequests(@NotNull @PathVariable Long userId) {
        return service.getRequestByUserId(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDto createRequests(@NotNull @PathVariable Long userId,
                                     @NotNull @RequestParam Long eventId) {
        return service.createRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public RequestDto cancel(@NotNull @PathVariable Long userId,
                             @NotNull @PathVariable Long requestId) {
        return service.cancelRequest(userId, requestId);
    }
}

