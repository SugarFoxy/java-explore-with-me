package ru.practicum.events.comments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.events.comments.dto.CommentDto;
import ru.practicum.events.comments.dto.NewAndUpdateCommentDto;
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
    public CommentDto createComment(Long userId, NewAndUpdateCommentDto dto) {
        Event event = objectCreator.getEventById(dto.getEventId());
        if (!event.getCommentSwitch()){
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
    public CommentDto updateComment(Long userId, Long commId, NewAndUpdateCommentDto dto) {
        Comment comment = objectCreator.getCommentById(commId);
        User user = objectCreator.getUserById(userId);
        Event event = objectCreator.getEventById(dto.getEventId());

        if (!comment.getCommentator().equals(user)) {
            throw new BadRequestException(String
                    .format("Только комментатор id = %d может изменить коментарий id = %d ", userId, commId));
        }
        if (!comment.getEvent().equals(event)) {
            throw new BadRequestException(String
                    .format("У события id = %d отсутствует коментарий id = %d", userId, commId));
        }

        comment.setText(dto.getText());
        return CommentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(Long userId, Long commId) {
        Comment comment = objectCreator.getCommentById(commId);
        User user = objectCreator.getUserById(userId);

        if (!comment.getCommentator().equals(user)) {
            throw new BadRequestException(String
                    .format("Только комментатор может удалить коментарий id = %d ", commId));
        }
        commentRepository.deleteById(commId);
    }

    @Override
    public CommentDto leaveRating(Long userId, Long commId, Boolean grade) {
        if (ratingRepository
                .existsByCommentAndRater(objectCreator.getCommentById(commId), objectCreator.getUserById(userId))) {
            throw new ConflictException("Отавить оценку комментарию можно один раз");
        }
        ratingRepository.save(Grade.builder()
                .comment(objectCreator.getCommentById(commId))
                .rater(objectCreator.getUserById(userId))
                .grade(grade)
                .build());
        Comment comment = objectCreator.getCommentById(commId);
        return CommentMapper.toDto(comment, ratingRepository.countGradeByCommentAndGrade(comment, true)
                - ratingRepository.countGradeByCommentAndGrade(comment, false));
    }

    @Override
    @Transactional
    public void deleteRating(Long userId, Long commId) {
        Comment comment = objectCreator.getCommentById(commId);
        User user = objectCreator.getUserById(userId);
        if (!ratingRepository.existsByCommentAndRater(comment, user)) {
            throw new BadRequestException(String.format("Невозможно удалеть лайк! " +
                    "Пользователь id = %d не ставил оценку комментарию id = %d", userId, commId));
        }
        ratingRepository
                .deleteByCommentAndRater(comment, user);
    }
}
