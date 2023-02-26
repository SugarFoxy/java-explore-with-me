package ru.practicum.explore.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class HitDto {
    Long id;
    @NotBlank(message = "Отсутствует название приложения")
    String app;
    @NotBlank(message = "Отсутствует uri запроса")
    String uri;
    @NotBlank(message = "Отсутствует ip")
    String ip;
    @NotBlank(message = "Отсутствует время запроса")
    String timestamp;
}
