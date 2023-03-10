package ru.practicum.events.event.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.event.dto.EventFullDto;
import ru.practicum.events.event.dto.EventShortDto;
import ru.practicum.events.event.service.EventPublicService;
import ru.practicum.util.exception.handler.CustomExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/events")
@CustomExceptionHandler
@Slf4j
public class ControllerPublicEvent {
    private final EventPublicService service;

    @Autowired
    public ControllerPublicEvent(EventPublicService service) {
        this.service = service;
    }

    @GetMapping
    public List<EventShortDto> getEventsByParams(@RequestParam(required = false) String text,
                                                 @RequestParam(required = false) List<Long> categories,
                                                 @RequestParam(required = false) Boolean paid,
                                                 @RequestParam(required = false) String rangeStart,
                                                 @RequestParam(required = false) String rangeEnd,
                                                 @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                                 @RequestParam(defaultValue = "EVENT_DATE") String sort,
                                                 HttpServletRequest request,
                                                 @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                 @Positive @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("Получен запрос на получение событий с возможностью фильтрации:" +
                 " text ={}, categories = {}, paid = {}, rangeStart = {}, rangeEnd = {}, onlyAvailable = {}, sort = {}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort);
        return service.getEventsByParamsCommon(text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, request, from, size);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventById(@PathVariable Long eventId, HttpServletRequest request) {
        log.info("Получен запрос на получение подробной информации об опубликованном событии id = {}", eventId);
        return service.getEventById(eventId, request);
    }
}


