package ru.practicum.events.event.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.category.model.Category;
import ru.practicum.events.event.dto.EventFullDto;
import ru.practicum.events.event.dto.EventShortDto;
import ru.practicum.events.event.mapper.EventMapper;
import ru.practicum.events.event.model.Event;
import ru.practicum.events.event.service.EventPublicService;
import ru.practicum.events.event.storage.EventRepository;
import ru.practicum.explore.StatsClient;
import ru.practicum.explore.dto.HitDto;
import ru.practicum.util.DateTimeUtils;
import ru.practicum.util.RepositoryObjectCreator;
import ru.practicum.util.exception.BadRequestException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EventPublicServiceImp implements EventPublicService {
    private final EventRepository eventRepository;
    private final StatsClient client;
    private final RepositoryObjectCreator objectCreator;

    @Autowired
    public EventPublicServiceImp(EventRepository eventRepository,
                                 RepositoryObjectCreator objectCreator,
                                 StatsClient client) {
        this.eventRepository = eventRepository;
        this.objectCreator = objectCreator;
        this.client = client;
    }

    public EventFullDto getEventById(Long eventId, HttpServletRequest request) {
        client.hitRequest(
                new HitDto(null,
                        "mainService",
                        request.getRequestURI(),
                        request.getRemoteAddr(),
                        DateTimeUtils.getDateTime(LocalDateTime.now())));

        Event event = objectCreator.getEventById(eventId);
        Long hits = client.statsRequest(eventId).orElse(0L);
        event.setViews(hits);
        eventRepository.save(event);
        return EventMapper.toEventFullDto(event);
    }

    public List<EventShortDto> getEventsByParamsCommon(String text, List<Long> categoryIds, Boolean paid,
                                                       String rangeStart, String rangeEnd, Boolean onlyAvailable,
                                                       String sort, HttpServletRequest request, int from, int size) {
        client.hitRequest(
                new HitDto(null,
                        "mainService",
                        "/events",
                        request.getRemoteAddr(),
                        DateTimeUtils.getDateTime(LocalDateTime.now())));

        List<Category> categories = categoryIds != null ?
                categoryIds
                        .stream()
                        .map(objectCreator::getCategoryById)
                        .collect(Collectors.toList()) :
                null;
        if (sort == null) {
            sort = "EVENT_DATE";
        }
        switch (sort) {
            case "EVENT_DATE":
                return getSortedEventByDate(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, from, size);
            case "VIEWS":
                return getSortedEventByViews(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, from, size);
            default:
                throw new BadRequestException("Сортировать можно только по EVENT_DATE и VIEWS");
        }
    }


    private List<EventShortDto> getSortedEventByDate(String text, List<Category> categories, Boolean paid,
                                                     String rangeStart, String rangeEnd,
                                                     boolean onlyAvailable, int from, int size) {
        List<Event> gotEvents = eventRepository.findByParamsCommon(
                text,
                categories,
                paid,
                DateTimeUtils.parseDate(rangeStart),
                DateTimeUtils.parseDate(rangeEnd),
                onlyAvailable,
                from,
                size
        );
        return gotEvents.stream().map(EventMapper::toShortDto).collect(Collectors.toList());
    }

    private List<EventShortDto> getSortedEventByViews(String text, List<Category> categories, Boolean paid,
                                                      String rangeStart, String rangeEnd,
                                                      boolean onlyAvailable, int from, int size) {
        List<Event> gotEvents = eventRepository.findByParamsCommon(
                text,
                categories,
                paid,
                DateTimeUtils.parseDate(rangeStart),
                DateTimeUtils.parseDate(rangeEnd),
                onlyAvailable,
                from,
                size
        );

        Map<Long, Long> viewsMap = client.viewsMapRequest(gotEvents
                .stream()
                .map(Event::getId)
                .collect(Collectors.toList()));

        gotEvents.sort((Event e1, Event e2) -> {
            Long v1 = viewsMap.get(e1.getId());
            Long v2 = viewsMap.get(e2.getId());
            return Long.compare(v1 != null ? v1 : 0L, v2 != null ? v2 : 0L);
        });
        return gotEvents
                .stream()
                .skip(from)
                .limit(size)
                .map(EventMapper::toShortDto)
                .collect(Collectors.toList());
    }
}