package ru.practicum.events.comments.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.events.comments.dto.OutCommentDto;
import ru.practicum.events.comments.service.CommentPublicService;
import ru.practicum.util.exception.handler.CustomExceptionHandler;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/comment")
@CustomExceptionHandler
public class ControllerPublicComment {
    private final CommentPublicService service;

    @Autowired
    public ControllerPublicComment(CommentPublicService service) {
        this.service = service;
    }

    @GetMapping
    public List<OutCommentDto> getCommentByEvent(@NotNull @RequestParam Long eventId,
                                                 @RequestParam(defaultValue = "false") Boolean rating,
                                                 @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                                 @Positive @RequestParam(defaultValue = "10") int size) {
        return service.getCommentByEvent(eventId, rating, from, size);
    }
}
