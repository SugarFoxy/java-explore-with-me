package ru.practicum.events.comments.rating.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.events.comments.model.Comment;
import ru.practicum.events.comments.rating.model.Grade;
import ru.practicum.users.model.User;

public interface RatingRepository extends JpaRepository<Grade, Long> {
    Long countGradeByCommentAndGrade(Comment comment, Boolean grade);

    Boolean existsByCommentAndRater(Comment comment, User rater);

    void deleteByCommentAndRater(Comment comment, User user);
}
