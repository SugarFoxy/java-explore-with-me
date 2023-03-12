package ru.practicum.events.comments.dto;

import lombok.*;
import ru.practicum.users.dto.UserShortDto;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OutCommentDto {
    private Long id;
    private UserShortDto commentator;
    private String text;
    private LocalDateTime commentTime;
    private Long rating;
}
