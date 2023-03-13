package ru.practicum.events.comments.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.comments.dto.InCommentDto;
import ru.practicum.events.comments.dto.OutCommentDto;
import ru.practicum.events.comments.service.CommentPrivateService;
import ru.practicum.util.exception.handler.CustomExceptionHandler;

@RestController
@RequestMapping("/users/{userId}/comments")
@CustomExceptionHandler
public class ControllerPrivateComment {
    private final CommentPrivateService service;

    @Autowired
    public ControllerPrivateComment(CommentPrivateService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OutCommentDto createComment(@PathVariable Long userId,
                                       @RequestBody InCommentDto dto) {
        return service.createComment(userId, dto);
    }

    @PatchMapping("/{commentId}")
    public OutCommentDto updateComment(@PathVariable Long userId,
                                       @PathVariable Long commentId,
                                       @RequestBody InCommentDto dto) {
        return service.updateComment(userId, commentId, dto);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long userId,
                              @PathVariable Long commentId) {
        service.deleteComment(userId, commentId);
    }

    @PostMapping("/{commentId}/like")
    @ResponseStatus(HttpStatus.CREATED)
    public OutCommentDto leaveRating(@PathVariable Long userId,
                                     @PathVariable Long commentId,
                                     @RequestParam Boolean grade) {
        return service.leaveRating(userId, commentId, grade);
    }

    @DeleteMapping("/{commentId}/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRating(@PathVariable Long userId,
                            @PathVariable Long commentId) {
        service.deleteRating(userId, commentId);
    }
}
