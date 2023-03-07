package ru.practicum.events.event.service.impl;

import org.springframework.stereotype.Service;
import ru.practicum.category.model.Category;
import ru.practicum.events.event.dto.EventFullDto;
import ru.practicum.events.event.dto.UpdateEventRequest;
import ru.practicum.events.event.mapper.EventMapper;
import ru.practicum.events.event.model.Event;
import ru.practicum.events.event.model.EventState;
import ru.practicum.events.event.model.EventStatus;
import ru.practicum.events.event.service.EventAdminService;
import ru.practicum.events.event.storage.EventRepository;
import ru.practicum.events.event.util.CheckTime;
import ru.practicum.users.model.User;
import ru.practicum.util.DateTimeUtils;
import ru.practicum.util.RepositoryObjectCreator;
import ru.practicum.util.exception.ConflictException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.events.event.mapper.EventMapper.toEvent;
import static ru.practicum.events.event.mapper.EventMapper.toEventFullDto;

@Service
public class EventAdminServiceImp implements EventAdminService {
    private final EventRepository eventRepository;
    private final RepositoryObjectCreator objectCreator;

    public EventAdminServiceImp(EventRepository eventRepository,
                                RepositoryObjectCreator checkExistence) {
        this.eventRepository = eventRepository;
        this.objectCreator = checkExistence;
    }

    public List<EventFullDto> getEventsByParams(List<Long> userIds, List<EventState> states, List<Long> categoryIds,
                                                String rangeStart, String rangeEnd, int from, int size) {
        List<User> users = userIds != null ?
                userIds
                        .stream()
                        .map(objectCreator::getUserById)
                        .collect(Collectors.toList()) :
                null;
        List<Category> categories = categoryIds != null ?
                categoryIds
                        .stream()
                        .map(objectCreator::getCategoryById)
                        .collect(Collectors.toList()) :
                null;
        LocalDateTime start = DateTimeUtils.parseDate(rangeStart);
        LocalDateTime end = DateTimeUtils.parseDate(rangeEnd);
        return eventRepository.findByParamsForAdmin(users, states, categories, start, end, from, size)
                .stream()
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    public EventFullDto update(Long eventId, UpdateEventRequest updateEventRequest) {
        Event event = objectCreator.getEventById(eventId);
        if (updateEventRequest.getEventDate() != null) {
            CheckTime.checkTemporaryValidation(updateEventRequest.getEventDate(), event.getPublishedOn(), 1);
        }
        if (!event.getState().equals(EventState.PENDING)) {
            throw new ConflictException("Нельзя изменить. Событие не в ожидании");
        } else {
            if (updateEventRequest.getStateAction().equals(EventStatus.PUBLISH_EVENT)) {
                event.setState(EventState.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now());
            } else {
                event.setState(EventState.CANCELED);
            }
        }
        return toEventFullDto(eventRepository.save(toEvent(event,
                updateEventRequest,
                event.getCategory(),
                event.getState())));
    }
}
