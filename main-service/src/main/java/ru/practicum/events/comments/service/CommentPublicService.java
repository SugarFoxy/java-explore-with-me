package ru.practicum.events.comments.service;

import ru.practicum.events.comments.dto.OutCommentDto;

import java.util.List;

public interface CommentPublicService {
    List<OutCommentDto> getCommentByEvent(Long eventId, Boolean rating, int from, int size);
}
