package ru.practicum.events.event.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.category.model.Category;
import ru.practicum.events.event.model.EventState;
import ru.practicum.users.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
public class EventAdminSearchDto {
    private List<User> users;
    private List<EventState> states;
    private List<Category> categories;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private int from;
    private int size;
}
