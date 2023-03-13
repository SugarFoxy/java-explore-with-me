package ru.practicum.events.compilation.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {
    private List<Long> events;
    @NotNull(message = "pinned не может быть пустым")
    private Boolean pinned;
    @NotNull(message = "title не может быть пустым")
    private String title;
}
