package ru.practicum.events.comments.service;

import ru.practicum.events.comments.dto.CommentDto;

import java.util.List;

public interface CommentPublicService {
    List<CommentDto> getCommentByEvent(Long eventId, Boolean rating, int from, int size);
}
