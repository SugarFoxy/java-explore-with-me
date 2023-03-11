package ru.practicum.events.event.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.events.event.dto.EventFullDto;
import ru.practicum.events.event.dto.EventShortDto;
import ru.practicum.events.event.dto.NewEventDto;
import ru.practicum.events.event.dto.UpdateEventRequest;
import ru.practicum.events.event.mapper.EventMapper;
import ru.practicum.events.event.model.Event;
import ru.practicum.events.event.model.EventState;
import ru.practicum.events.event.service.EventPrivateService;
import ru.practicum.events.event.storage.EventRepository;
import ru.practicum.events.event.util.CheckTime;
import ru.practicum.events.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.events.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.events.request.dto.RequestDto;
import ru.practicum.events.request.mapper.RequestMapper;
import ru.practicum.events.request.model.Request;
import ru.practicum.events.request.model.RequestStatus;
import ru.practicum.events.request.storage.RequestRepository;
import ru.practicum.users.model.User;
import ru.practicum.util.RepositoryObjectCreator;
import ru.practicum.util.exception.BadRequestException;
import ru.practicum.util.exception.ConflictException;
import ru.practicum.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.events.event.mapper.EventMapper.toEvent;
import static ru.practicum.events.event.mapper.EventMapper.toEventFullDto;

@Service
public class EventPrivateServiceImp implements EventPrivateService {
    private final EventRepository eventRepository;
    private final RepositoryObjectCreator objectCreator;
    private final RequestRepository requestRepository;

    public EventPrivateServiceImp(EventRepository eventRepository,
                                  RepositoryObjectCreator objectCreator,
                                  RequestRepository requestRepository) {
        this.eventRepository = eventRepository;
        this.objectCreator = objectCreator;
        this.requestRepository = requestRepository;
    }

    @Override
    public List<EventShortDto> getEventsByUser(Long userId, Integer from, Integer size) {
        return eventRepository.findEventsByInitiator(objectCreator.getUserById(userId),
                        PageRequest.of(from, size))
                .stream()
                .map(EventMapper::toShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto addNewEvent(Long userId, NewEventDto newEventDto) {
        CheckTime.checkTemporaryValidation(newEventDto.getEventDate(), LocalDateTime.now(), 2);
        Event event = toEvent(newEventDto,
                objectCreator.getUserById(userId),
                objectCreator.getCategoryById(newEventDto.getCategory()), 0L, 0L);
        event.setCommentSwitch(true);
        return toEventFullDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto getUserEvent(Long userId, Long eventId) {
        return toEventFullDto(eventRepository
                .findEventByIdAndInitiator(eventId, objectCreator.getUserById(userId))
                .orElseThrow(() -> new NotFoundException(String.format("У пользователя %d не существует события - %d",
                        userId, eventId))));
    }

    @Override
    public EventFullDto update(Long userId, Long eventId, UpdateEventRequest updateEventRequest) {
        if (updateEventRequest.getEventDate() != null) {
            CheckTime.checkTemporaryValidation(updateEventRequest.getEventDate(), LocalDateTime.now(), 2);
        }

        Event event = EventMapper.toEvent(getUserEvent(userId, eventId), objectCreator.getUserById(userId));

        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Нельзя изменить. Событие уже опубликованно");
        }

        EventState state;
        switch (updateEventRequest.getStateAction()) {
            case SEND_TO_REVIEW:
                state = EventState.PENDING;
                break;
            case CANCEL_REVIEW:
                state = EventState.CANCELED;
                break;
            default:
                throw new BadRequestException("Статус не соответствует модификатору доступа");
        }

        return toEventFullDto(eventRepository.save(toEvent(event,
                updateEventRequest,
                event.getCategory(),
                state)));
    }

    @Override
    public List<RequestDto> getParticipationRequests(Long userId, Long eventId) {
        User user = objectCreator.getUserById(userId);
        Event event = objectCreator.getEventById(eventId);

        if (!event.getInitiator().equals(user)) {
            throw new BadRequestException("Только инициатор может получать запросы на события");
        }

        return requestRepository.findByEvent(event)
                .stream()
                .map(RequestMapper::toDto)
                .collect(Collectors.toList());

    }

    @Override
    public EventRequestStatusUpdateResult updateRequestStatus(Long userId,
                                                              Long eventId,
                                                              EventRequestStatusUpdateRequest updateRequest) {
        User user = objectCreator.getUserById(userId);
        Event event = objectCreator.getEventById(eventId);

        if (!event.getInitiator().equals(user)) {
            throw new BadRequestException("Принять заявки может только инициализатор события");
        }
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() <= event.getConfirmedRequests()) {
            throw new ConflictException("Лимит по заявкам достигнут");
        }

        List<Request> requests = updateRequest.getRequestIds()
                .stream()
                .map(objectCreator::getRequestById)
                .collect(Collectors.toList());

        switch (updateRequest.getStatus()) {
            case CONFIRMED:
                return getUpdateResultConfirmed(requests, event);
            case REJECTED:
                return getUpdateResultRejected(requests);
            default:
                throw new BadRequestException("Некорректный статус изменения");
        }
    }

    @Override
    public EventFullDto enableComment(Long userId, Long eventId, Boolean disable) {
        User user = objectCreator.getUserById(userId);
        Event event = objectCreator.getEventById(eventId);
        if (!event.getInitiator().equals(user)) {
            throw new BadRequestException("Включать и выключать коментарии может только инициализатор события");
        }
        event.setCommentSwitch(disable);
        return toEventFullDto(eventRepository.save(event));
    }

    private EventRequestStatusUpdateResult getUpdateResultConfirmed(List<Request> requests, Event event) {
        List<RequestDto> confirmed = new ArrayList<>();
        List<RequestDto> rejected = new ArrayList<>();

        for (Request request : requests) {
            if (!request.getStatus().equals(RequestStatus.PENDING)) {
                throw new ConflictException("Статус заявки должен быть PENDING");
            }
            if (event.getParticipantLimit() <= event.getConfirmedRequests()) {
                request.setStatus(RequestStatus.REJECTED);
                rejected.add(RequestMapper.toDto(request));
            } else {
                request.setStatus(RequestStatus.CONFIRMED);
                event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                confirmed.add(RequestMapper.toDto(request));
            }
            requestRepository.save(request);
        }

        return EventRequestStatusUpdateResult.builder()
                .confirmedRequests(confirmed)
                .rejectedRequests(rejected)
                .build();
    }


    private EventRequestStatusUpdateResult getUpdateResultRejected(List<Request> requests) {
        List<RequestDto> rejected = new ArrayList<>();

        for (Request request : requests) {
            if (!request.getStatus().equals(RequestStatus.PENDING)) {
                throw new ConflictException("Статус заявки должен быть PENDING");
            }
            request.setStatus(RequestStatus.REJECTED);
            requestRepository.save(request);
            rejected.add(RequestMapper.toDto(request));
        }

        return EventRequestStatusUpdateResult.builder()
                .confirmedRequests(new ArrayList<>())
                .rejectedRequests(rejected)
                .build();
    }
}
