package ru.practicum.events.event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.event.dto.EventFullDto;
import ru.practicum.events.event.dto.UpdateEventRequest;
import ru.practicum.events.event.model.EventState;
import ru.practicum.events.event.service.EventAdminService;
import ru.practicum.util.exception.handler.CustomExceptionHandler;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/events")
@Validated
@CustomExceptionHandler
public class ControllerAdminEvent {

    private final EventAdminService service;

    @Autowired
    public ControllerAdminEvent(@Qualifier("eventAdminServiceImp") EventAdminService service) {
        this.service = service;
    }

    @GetMapping
    public List<EventFullDto> findAll(@RequestParam(required = false) List<Long> users,
                                      @RequestParam(required = false) List<EventState> states,
                                      @RequestParam(required = false) List<Long> categories,
                                      @RequestParam(required = false) String rangeStart,
                                      @RequestParam(required = false) String rangeEnd,
                                      @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                      @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {

        return service.getEventsByParams(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto update(@Positive @PathVariable Long eventId, @RequestBody UpdateEventRequest dto) {
        return service.update(eventId, dto);
    }
}
