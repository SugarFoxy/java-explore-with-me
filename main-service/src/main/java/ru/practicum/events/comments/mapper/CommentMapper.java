package ru.practicum.events.comments.mapper;

import ru.practicum.events.comments.dto.InCommentDto;
import ru.practicum.events.comments.dto.OutCommentDto;
import ru.practicum.events.comments.model.Comment;
import ru.practicum.events.event.model.Event;
import ru.practicum.users.mapper.UserMapper;
import ru.practicum.users.model.User;

import java.time.LocalDateTime;

public final class CommentMapper {
    private CommentMapper(){}

    public static OutCommentDto toDto(Comment comment) {
        return OutCommentDto.builder()
                .commentator(UserMapper.toShortDto(comment.getCommentator()))
                .commentTime(comment.getCommentTime())
                .id(comment.getId())
                .text(comment.getText())
                .build();
    }

    public static OutCommentDto toDto(Comment comment, Long rating) {
        return OutCommentDto.builder()
                .commentator(UserMapper.toShortDto(comment.getCommentator()))
                .commentTime(comment.getCommentTime())
                .id(comment.getId())
                .text(comment.getText())
                .rating(rating)
                .build();
    }

    public static Comment toComment(InCommentDto dto, LocalDateTime time, User commentator, Event event) {
        return Comment.builder()
                .commentator(commentator)
                .text(dto.getText())
                .commentTime(time)
                .event(event)
                .build();
    }
}
