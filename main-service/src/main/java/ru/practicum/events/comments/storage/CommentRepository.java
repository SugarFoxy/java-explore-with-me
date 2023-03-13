package ru.practicum.events.comments.storage;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.events.comments.model.Comment;
import ru.practicum.events.event.model.Event;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByEvent(Event event, Pageable pageable);
}
