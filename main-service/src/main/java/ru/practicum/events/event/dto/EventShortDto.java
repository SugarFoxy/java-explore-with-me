package ru.practicum.events.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.users.dto.UserShortDto;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventShortDto {
    private Long id;
    @NotNull(message = "annotation не должно быть пустым")
    @NotBlank(message = "annotation не должно быть пустым")
    private String annotation;
    @NotNull(message = "category не должно быть пустым")
    private CategoryDto category;
    private Long confirmedRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Future()
    private LocalDateTime eventDate;
    @NotNull(message = "initiator не должно быть пустым")
    private UserShortDto initiator;
    @NotNull(message = "paid не должно быть пустым")
    private Boolean paid;
    @NotNull(message = "title не должно быть пустым")
    @NotBlank(message = "title не должно быть пустым")
    private String title;
    private Long views;
}
