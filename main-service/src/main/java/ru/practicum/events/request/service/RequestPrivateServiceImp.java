package ru.practicum.events.request.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.events.event.model.Event;
import ru.practicum.events.event.model.EventState;
import ru.practicum.events.event.storage.EventRepository;
import ru.practicum.events.request.dto.RequestDto;
import ru.practicum.events.request.mapper.RequestMapper;
import ru.practicum.events.request.model.Request;
import ru.practicum.events.request.model.RequestStatus;
import ru.practicum.events.request.storage.RequestRepository;
import ru.practicum.users.model.User;
import ru.practicum.util.RepositoryObjectCreator;
import ru.practicum.util.exception.ConflictException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestPrivateServiceImp implements RequestPrivateService {
    private final RequestRepository requestRepository;
    private final RepositoryObjectCreator checkExistence;
    private final EventRepository eventRepository;

    @Autowired
    public RequestPrivateServiceImp(RequestRepository requestRepository,
                                    RepositoryObjectCreator checkExistence,
                                    EventRepository eventRepository) {
        this.checkExistence = checkExistence;
        this.requestRepository = requestRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public List<RequestDto> getRequestByUserId(Long userId) {
        User requester = checkExistence.getUserById(userId);
        return requestRepository.findByRequester(requester)
                .stream()
                .map(RequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RequestDto createRequest(Long userId, Long eventId) {
        User user = checkExistence.getUserById(userId);
        Event event = checkExistence.getEventById(eventId);

        if (event.getInitiator().equals(user)) {
            throw new ConflictException("Невозможно запросить собственные мероприятия");
        }
        if (!requestRepository.findByEventAndRequester(event, user).isEmpty()) {
            throw new ConflictException("Нельзя запросить повторно");
        }
        if (event.getState() != EventState.PUBLISHED) {
            throw new ConflictException("Событие не опубликованно");
        }
        if (event.getParticipantLimit() > 0 && event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new ConflictException("Достигнут лимит участников");
        }

        Request request = Request.builder()
                .created(LocalDateTime.now())
                .event(event)
                .requester(user)
                .status(event.getRequestModeration() ? RequestStatus.PENDING : RequestStatus.CONFIRMED)
                .build();

        if (request.getStatus() == RequestStatus.CONFIRMED) {
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepository.save(event);
        }

        return RequestMapper.toDto(requestRepository.save(request));
    }

    @Override
    public RequestDto cancelRequest(Long userId, Long requestId) {
        User user = checkExistence.getUserById(userId);
        Request request = checkExistence.getRequestById(requestId);

        if (!request.getRequester().equals(user)) {
            throw new ConflictException("Отменить рапрос может только хозяин запроса");
        }

        request.setStatus(RequestStatus.CANCELED);
        return RequestMapper.toDto(requestRepository.save(request));
    }
}
