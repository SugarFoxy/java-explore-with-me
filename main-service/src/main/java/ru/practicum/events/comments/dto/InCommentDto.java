package ru.practicum.events.comments.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InCommentDto {
    @NotNull
    @NotBlank
    private String text;
    private Long eventId;
}
