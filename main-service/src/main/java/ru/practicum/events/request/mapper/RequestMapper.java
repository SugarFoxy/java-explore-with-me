package ru.practicum.events.request.mapper;

import ru.practicum.events.request.dto.RequestDto;
import ru.practicum.events.request.model.Request;

import java.time.format.DateTimeFormatter;

public final class RequestMapper {
    private RequestMapper(){}

    public static RequestDto toDto(Request request) {
        return RequestDto.builder()
                .id(request.getId())
                .requester(request.getRequester().getId())
                .event(request.getEvent().getId())
                .created(request.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .status(request.getStatus())
                .build();
    }
}
