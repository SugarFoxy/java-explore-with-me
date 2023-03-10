package ru.practicum.events.comments.mapper;

import ru.practicum.events.comments.dto.CommentDto;
import ru.practicum.events.comments.dto.NewAndUpdateCommentDto;
import ru.practicum.events.comments.model.Comment;
import ru.practicum.events.event.model.Event;
import ru.practicum.users.mapper.UserMapper;
import ru.practicum.users.model.User;

import java.time.LocalDateTime;

public class CommentMapper {
    public static CommentDto toDto(Comment comment) {
        return CommentDto.builder()
                .commentator(UserMapper.toShortDto(comment.getCommentator()))
                .commentTime(comment.getCommentTime())
                .id(comment.getId())
                .text(comment.getText())
                .build();
    }

    public static CommentDto toDto(Comment comment, Long rating) {
        return CommentDto.builder()
                .commentator(UserMapper.toShortDto(comment.getCommentator()))
                .commentTime(comment.getCommentTime())
                .id(comment.getId())
                .text(comment.getText())
                .rating(rating)
                .build();
    }

    public static Comment toComment(NewAndUpdateCommentDto dto, LocalDateTime time, User commentator, Event event) {
        return Comment.builder()
                .commentator(commentator)
                .text(dto.getText())
                .commentTime(time)
                .event(event)
                .build();
    }
}
