package ru.practicum.events.comments.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.users.dto.UserShortDto;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class CommentDto {
    private Long id;
    private UserShortDto commentator;
    private String text;
    private LocalDateTime commentTime;
    private Long rating;
}
