package ru.practicum.events.compilation.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewCompilationDto {
    private List<Long> events;
    @NotNull
    private Boolean pinned;
    @NotNull
    private String title;
}
