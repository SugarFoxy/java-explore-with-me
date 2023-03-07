package ru.practicum.events.request.dto;

import lombok.*;
import ru.practicum.events.request.model.RequestStatus;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestDto {
    private String created;
    private Long event;
    private Long requester;
    private RequestStatus status;
    private Long id;
}

