package ru.practicum.events.request.dto;

import lombok.*;
import ru.practicum.events.request.model.RequestStatus;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestStatusUpdateRequest {
    List<Long> requestIds;
    RequestStatus status;
}
