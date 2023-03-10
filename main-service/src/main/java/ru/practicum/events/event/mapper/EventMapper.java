package ru.practicum.events.event.mapper;

import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.events.event.dto.EventFullDto;
import ru.practicum.events.event.dto.EventShortDto;
import ru.practicum.events.event.dto.NewEventDto;
import ru.practicum.events.event.dto.UpdateEventRequest;
import ru.practicum.events.event.model.Event;
import ru.practicum.events.event.model.EventState;
import ru.practicum.users.mapper.UserMapper;
import ru.practicum.users.model.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class EventMapper {
    public static EventShortDto toShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .paid(event.getPaid())
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .eventDate(event.getEventDate())
                .confirmedRequests(event.getConfirmedRequests())
                .views(event.getViews())
                .initiator(UserMapper.toShortDto(event.getInitiator()))
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .build();

    }

    public static Event toEvent(NewEventDto eventDto,
                                User initiator,
                                Category category,
                                Long requestsQuantity,
                                Long views) {
        LocalDateTime dateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        return Event.builder()
                .id(null)
                .annotation(eventDto.getAnnotation())
                .category(category)
                .confirmedRequests(requestsQuantity)
                .created(dateTime)
                .description(eventDto.getDescription())
                .eventDate(eventDto.getEventDate())
                .initiator(initiator)
                .location(eventDto.getLocation())
                .paid(eventDto.getPaid())
                .participantLimit(eventDto.getParticipantLimit())
                .publishedOn(dateTime)
                .requestModeration(eventDto.getRequestModeration())
                .state(EventState.PENDING)
                .title(eventDto.getTitle())
                .views(views)
                .build();
    }

    public static EventFullDto toEventFullDto(Event event) {
        return EventFullDto.builder()
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreated())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .id(event.getId())
                .initiator(UserMapper.toShortDto(event.getInitiator()))
                .location(event.getLocation())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public static Event toEvent(Event event, UpdateEventRequest eventRequest, Category category, EventState state) {
        return Event.builder()
                .id(event.getId())
                .annotation(eventRequest.getAnnotation() != null ?
                        eventRequest.getAnnotation() :
                        event.getAnnotation())
                .category(eventRequest.getCategory() != null ?
                        category :
                        event.getCategory())
                .confirmedRequests(event.getConfirmedRequests())
                .created(event.getCreated())
                .description(eventRequest.getDescription() != null ?
                        eventRequest.getDescription() :
                        event.getDescription())
                .eventDate(eventRequest.getEventDate() != null ?
                        eventRequest.getEventDate() :
                        event.getEventDate())
                .initiator(event.getInitiator())
                .location(eventRequest.getLocation() != null ?
                        eventRequest.getLocation() :
                        event.getLocation())
                .paid(eventRequest.getPaid() != null ?
                        eventRequest.getPaid() :
                        event.getPaid())
                .participantLimit(eventRequest.getParticipantLimit() != null ?
                        eventRequest.getParticipantLimit() :
                        event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(eventRequest.getRequestModeration() != null ?
                        eventRequest.getRequestModeration() :
                        event.getRequestModeration())
                .state(state)
                .title(eventRequest.getTitle() != null ?
                        eventRequest.getTitle() :
                        event.getTitle())
                .views(event.getViews())
                .build();
    }

    public static Event toEvent(EventFullDto eventDto, User initiator) {
        return Event.builder()
                .id(eventDto.getId())
                .annotation(eventDto.getAnnotation())
                .category(CategoryMapper.toCategory(eventDto.getCategory()))
                .confirmedRequests(eventDto.getConfirmedRequests())
                .created(eventDto.getCreatedOn())
                .description(eventDto.getDescription())
                .eventDate(eventDto.getEventDate())
                .initiator(initiator)
                .location(eventDto.getLocation())
                .paid(eventDto.getPaid())
                .participantLimit(eventDto.getParticipantLimit())
                .publishedOn(eventDto.getPublishedOn())
                .requestModeration(eventDto.getRequestModeration())
                .state(eventDto.getState())
                .title(eventDto.getTitle())
                .views(eventDto.getViews())
                .build();
    }
}
