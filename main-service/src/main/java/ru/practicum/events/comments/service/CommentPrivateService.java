package ru.practicum.events.comments.service;

import ru.practicum.events.comments.dto.InputCommentDto;
import ru.practicum.events.comments.dto.OutCommentDto;

public interface CommentPrivateService {
    OutCommentDto createComment(Long userId, InputCommentDto dto);

    OutCommentDto updateComment(Long userId, Long commentId, InputCommentDto dto);

    void deleteComment(Long userId, Long commentId);

    OutCommentDto leaveRating(Long userId, Long commentId, Boolean grade);

    void deleteRating(Long userId, Long commentId);
}
