package ru.practicum.events.event.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.category.model.Category;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
public class EventPublicSearchDto {
    private String text;
    private List<Category> categories;
    private Boolean paid;
    private java.time.LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private Boolean onlyAvailable;
    private int from;
    private int size;
}
