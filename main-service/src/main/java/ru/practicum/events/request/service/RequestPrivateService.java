package ru.practicum.events.request.service;

import ru.practicum.events.request.dto.RequestDto;

import java.util.List;

public interface RequestPrivateService {
    List<RequestDto> getRequestByUserId(Long userId);

    RequestDto createRequest(Long userId, Long eventId);

    RequestDto cancelRequest(Long userId, Long requestId);
}
