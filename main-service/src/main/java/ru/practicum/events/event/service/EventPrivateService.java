package ru.practicum.events.event.service;

import ru.practicum.events.event.dto.*;
import ru.practicum.events.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.events.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.events.request.dto.RequestDto;

import java.util.List;

public interface EventPrivateService {
    List<EventShortDto> getEventsByUser(Long userId, Integer from, Integer size);

    EventFullDto addNewEvent(Long userId, NewEventDto newEventDto);

    EventFullDto getUserEvent(Long userId, Long eventId);

    EventFullDto update(Long userId, Long eventId, UpdateEventRequest updateEventRequest);

    List<RequestDto> getParticipationRequests(Long userId, Long eventId);

    EventRequestStatusUpdateResult updateRequestStatus(Long userId,
                                                              Long eventId,
                                                              EventRequestStatusUpdateRequest updateRequest);

    EventFullDto enableComment(Long userId, Long eventId, Boolean disable);
}
