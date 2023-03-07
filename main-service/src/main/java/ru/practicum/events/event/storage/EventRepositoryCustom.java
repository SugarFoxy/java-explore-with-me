package ru.practicum.events.event.storage;

import ru.practicum.category.model.Category;
import ru.practicum.events.event.model.Event;
import ru.practicum.events.event.model.EventState;
import ru.practicum.users.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepositoryCustom {
    List<Event> findByParamsCommon(String text, List<Category> categories, Boolean paid,
                                          LocalDateTime rangeStart, LocalDateTime rangeEnd, boolean onlyAvailable,
                                          int from, int size);

    List<Event> findByParamsForAdmin(List<User> users, List<EventState> states, List<Category> categories,
                                            LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size);
}

