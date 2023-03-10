package ru.practicum.events.comments.service;

import ru.practicum.events.comments.dto.CommentDto;
import ru.practicum.events.comments.dto.NewAndUpdateCommentDto;

public interface CommentPrivateService {
    CommentDto createComment(Long userId, NewAndUpdateCommentDto dto);

    CommentDto updateComment(Long userId, Long commId, NewAndUpdateCommentDto dto);

    void deleteComment(Long userId, Long commId);

    void leaveRating(Long userId, Long commId, Boolean grade);

    void deleteRating(Long userId, Long commId);
}
