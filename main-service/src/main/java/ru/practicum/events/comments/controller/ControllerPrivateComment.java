package ru.practicum.events.comments.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.comments.dto.InputCommentDto;
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
                                       @RequestBody InputCommentDto dto) {
        return service.createComment(userId, dto);
    }

    @PatchMapping("/{commId}")
    public OutCommentDto updateComment(@PathVariable Long userId,
                                       @PathVariable Long commId,
                                       @RequestBody InputCommentDto dto) {
        return service.updateComment(userId, commId, dto);
    }

    @DeleteMapping("/{commId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long userId,
                              @PathVariable Long commId) {
        service.deleteComment(userId, commId);
    }

    @PostMapping("/{commId}/like")
    @ResponseStatus(HttpStatus.CREATED)
    public OutCommentDto leaveRating(@PathVariable Long userId,
                                     @PathVariable Long commId,
                                     @RequestParam Boolean grade) {
        return service.leaveRating(userId, commId, grade);
    }

    @DeleteMapping("/{commId}/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRating(@PathVariable Long userId,
                            @PathVariable Long commId) {
        service.deleteRating(userId, commId);
    }
}
