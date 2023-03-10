package ru.practicum.events.event.service;

import ru.practicum.events.event.dto.EventFullDto;
import ru.practicum.events.event.dto.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventPublicService {
    EventFullDto getEventById(Long eventId, HttpServletRequest request);

    List<EventShortDto> getEventsByParamsCommon(String text, List<Long> categoryIds, Boolean paid,
                                                String rangeStart, String rangeEnd, Boolean onlyAvailable,
                                                String sort, HttpServletRequest request, int from, int size);
}
