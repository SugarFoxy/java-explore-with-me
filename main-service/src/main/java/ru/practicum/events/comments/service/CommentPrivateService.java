package ru.practicum.events.comments.service;

import ru.practicum.events.comments.dto.InCommentDto;
import ru.practicum.events.comments.dto.OutCommentDto;

public interface CommentPrivateService {
    OutCommentDto createComment(Long userId, InCommentDto dto);

    OutCommentDto updateComment(Long userId, Long commentId, InCommentDto dto);

    void deleteComment(Long userId, Long commentId);

    OutCommentDto leaveRating(Long userId, Long commentId, Boolean grade);

    void deleteRating(Long userId, Long commentId);
}
