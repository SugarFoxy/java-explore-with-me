package ru.practicum.events.event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.event.dto.EventFullDto;
import ru.practicum.events.event.dto.EventShortDto;
import ru.practicum.events.event.dto.NewEventDto;
import ru.practicum.events.event.dto.UpdateEventRequest;
import ru.practicum.events.event.service.EventPrivateService;
import ru.practicum.events.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.events.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.events.request.dto.RequestDto;
import ru.practicum.util.exception.handler.CustomExceptionHandler;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/events")
@CustomExceptionHandler
public class ControllerEventPrivate {
    private final EventPrivateService service;

    @Autowired
    public ControllerEventPrivate(EventPrivateService service) {
        this.service = service;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(@Positive @PathVariable Long userId, @Valid @RequestBody NewEventDto dto) {
        return service.addNewEvent(userId, dto);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(@Positive @PathVariable Long userId,
                                    @Positive @PathVariable Long eventId,
                                    @RequestBody UpdateEventRequest dto) {
        return service.update(userId, eventId, dto);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventByIdWhereUserIsOwner(@Positive @PathVariable Long userId,
                                                     @Positive @PathVariable Long eventId) {
        return service.getUserEvent(userId, eventId);
    }

    @GetMapping()
    public List<EventShortDto> findAllWhereUserIsOwner(@Positive @PathVariable Long userId,
                                                       @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                       @Positive @RequestParam(defaultValue = "10") Integer size) {
        return service.getEventsByUser(userId, from, size);
    }

    @GetMapping("/{eventId}/requests")
    public List<RequestDto> getRequests(@Positive @PathVariable Long userId,
                                        @Positive @PathVariable Long eventId) {
        return service.getParticipationRequests(userId, eventId);
    }


    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateRequest(@PathVariable Long userId,
                                                        @PathVariable Long eventId,
                                                        @RequestBody EventRequestStatusUpdateRequest updateRequest) {
        return service.updateRequestStatus(userId, eventId, updateRequest);
    }
}
