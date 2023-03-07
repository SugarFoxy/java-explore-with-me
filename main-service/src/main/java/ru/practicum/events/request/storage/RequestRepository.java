package ru.practicum.events.request.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.events.event.model.Event;
import ru.practicum.events.request.model.Request;
import ru.practicum.users.model.User;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    Boolean existsRequestByEventAndRequester(Event event, User requester);

    List<Request> findByRequester(User requester);

    List<Request> findByEvent(Event event);
}
