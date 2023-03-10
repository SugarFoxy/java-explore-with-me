package ru.practicum.events.event.service;

import ru.practicum.events.event.dto.EventFullDto;
import ru.practicum.events.event.dto.UpdateEventRequest;
import ru.practicum.events.event.model.EventState;

import java.util.List;

public interface EventAdminService {
    List<EventFullDto> getEventsByParams(List<Long> userIds, List<EventState> states, List<Long> categoryIds,
                                                String rangeStart, String rangeEnd, int from, int size);

    EventFullDto update(Long eventId, UpdateEventRequest updateEventRequest);
}
