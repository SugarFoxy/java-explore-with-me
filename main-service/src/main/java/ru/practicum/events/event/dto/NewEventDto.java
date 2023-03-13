package ru.practicum.events.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import ru.practicum.events.event.model.Location;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {
    @NotNull(message = "annotation не должно быть пустым")
    @Size(min = 20, max = 2000)
    private String annotation;
    @NotNull(message = "category не должно быть пустым")
    private Long category;
    @NotNull(message = "description не должно быть пустым")
    @Size(min = 20, max = 7000)
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    @NotNull(message = "location не должно быть пустым")
    private Location location;
    @Value("false")
    private Boolean paid;
    @Value("0")
    private Integer participantLimit;
    @Value("true")
    private Boolean requestModeration;
    @NotNull(message = "title не должно быть пустым")
    @Size(min = 3, max = 120)
    private String title;
}
