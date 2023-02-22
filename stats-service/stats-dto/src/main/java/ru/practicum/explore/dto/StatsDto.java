package ru.practicum.explore.dto;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StatsDto {
    String app;
    String uri;
    Long hits;
}
