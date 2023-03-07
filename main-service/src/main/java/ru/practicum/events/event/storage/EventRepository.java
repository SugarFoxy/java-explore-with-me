package ru.practicum.events.event.storage;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.category.model.Category;
import ru.practicum.events.event.model.Event;
import ru.practicum.users.model.User;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryCustom {
    List<Event> findEventsByInitiator(User user, Pageable pageable);

    Optional<Event> findEventByIdAndInitiator(Long id, User user);

    Boolean existsByCategory(Category category);
}
