package ru.practicum.events.comments.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.comments.dto.CommentDto;
import ru.practicum.events.comments.dto.NewAndUpdateCommentDto;
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
    public CommentDto createComment(@PathVariable Long userId,
                                    @RequestBody NewAndUpdateCommentDto dto) {
        return service.createComment(userId, dto);
    }

    @PatchMapping("/{commId}")
    public CommentDto updateComment(@PathVariable Long userId,
                                    @PathVariable Long commId,
                                    @RequestBody NewAndUpdateCommentDto dto) {
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
    public CommentDto leaveRating(@PathVariable Long userId,
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
