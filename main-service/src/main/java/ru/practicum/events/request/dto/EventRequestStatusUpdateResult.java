package ru.practicum.events.request.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
public class EventRequestStatusUpdateResult {
    private List<RequestDto> confirmedRequests;
    private List<RequestDto> rejectedRequests;
}
