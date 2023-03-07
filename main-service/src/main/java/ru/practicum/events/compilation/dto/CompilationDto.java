package ru.practicum.events.compilation.dto;

import lombok.*;
import ru.practicum.events.event.dto.EventFullDto;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompilationDto {
    private Long id;
    private List<EventFullDto> events;
    private Boolean pinned;
    private String title;
}
