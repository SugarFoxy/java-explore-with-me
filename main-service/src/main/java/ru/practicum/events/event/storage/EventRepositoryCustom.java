package ru.practicum.events.event.storage;

import ru.practicum.events.event.dto.EventAdminSearchDto;
import ru.practicum.events.event.dto.EventPublicSearchDto;
import ru.practicum.events.event.model.Event;

import java.util.List;

public interface EventRepositoryCustom {
    List<Event> findByParamsCommon(EventPublicSearchDto searchDto);

    List<Event> findByParamsForAdmin(EventAdminSearchDto searchDto);
}