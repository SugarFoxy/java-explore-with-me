package ru.practicum.events.comments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.events.comments.dto.InputCommentDto;
import ru.practicum.events.comments.dto.OutCommentDto;
import ru.practicum.events.comments.mapper.CommentMapper;
import ru.practicum.events.comments.model.Comment;
import ru.practicum.events.comments.rating.model.Grade;
import ru.practicum.events.comments.rating.storage.RatingRepository;
import ru.practicum.events.comments.storage.CommentRepository;
import ru.practicum.events.event.model.Event;
import ru.practicum.users.model.User;
import ru.practicum.util.RepositoryObjectCreator;
import ru.practicum.util.exception.BadRequestException;
import ru.practicum.util.exception.ConflictException;

import java.time.LocalDateTime;

@Service
public class CommentPrivateServiceImpl implements CommentPrivateService {
    private final CommentRepository commentRepository;
    private final RepositoryObjectCreator objectCreator;
    private final RatingRepository ratingRepository;

    @Autowired
    public CommentPrivateServiceImpl(CommentRepository commentRepository,
                                     RepositoryObjectCreator objectCreator,
                                     RatingRepository ratingRepository) {
        this.commentRepository = commentRepository;
        this.objectCreator = objectCreator;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public OutCommentDto createComment(Long userId, InputCommentDto dto) {
        Event event = objectCreator.getEventById(dto.getEventId());
        if (!event.getCommentSwitch()) {
            throw new ConflictException("Невозможно добавить комментарий! У события отключены комментарии!");
        }
        return CommentMapper.toDto(commentRepository.save(CommentMapper.toComment(
                dto,
                LocalDateTime.now(),
                objectCreator.getUserById(userId),
                event)
        ));
    }

    @Override
    public OutCommentDto updateComment(Long userId, Long commentId, InputCommentDto dto) {
        Comment comment = objectCreator.getCommentById(commentId);

        if (!comment.getCommentator().getId().equals(userId)) {
            throw new BadRequestException(String
                    .format("Только комментатор может изменить коментарий id = %d ", commentId));
        }
        if (!comment.getEvent().getId().equals(dto.getEventId())) {
            throw new BadRequestException(String
                    .format("У события id = %d отсутствует коментарий id = %d", userId, commentId));
        }

        comment.setText(dto.getText());
        return CommentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(Long userId, Long commId) {
        Comment comment = objectCreator.getCommentById(commId);

        if (!comment.getCommentator().getId().equals(userId)) {
            throw new BadRequestException(String
                    .format("Только комментатор может удалить коментарий id = %d ", commId));
        }
        commentRepository.deleteById(commId);
    }

    @Override
    public OutCommentDto leaveRating(Long userId, Long commentId, Boolean grade) {
        if (ratingRepository
                .existsByCommentAndRater(objectCreator.getCommentById(commentId), objectCreator.getUserById(userId))) {
            throw new ConflictException("Отавить оценку комментарию можно один раз");
        }
        ratingRepository.save(Grade.builder()
                .comment(objectCreator.getCommentById(commentId))
                .rater(objectCreator.getUserById(userId))
                .grade(grade)
                .build());
        Comment comment = objectCreator.getCommentById(commentId);
        return CommentMapper.toDto(comment, ratingRepository.countGradeByCommentAndGrade(comment, true)
                - ratingRepository.countGradeByCommentAndGrade(comment, false));
    }

    @Override
    @Transactional
    public void deleteRating(Long userId, Long commentId) {
        Comment comment = objectCreator.getCommentById(commentId);
        User user = objectCreator.getUserById(userId);
        if (!ratingRepository.existsByCommentAndRater(comment, user)) {
            throw new BadRequestException(String.format("Невозможно удалеть лайк! " +
                    "Пользователь id = %d не ставил оценку комментарию id = %d", userId, commentId));
        }
        ratingRepository
                .deleteByCommentAndRater(comment, user);
    }
}
