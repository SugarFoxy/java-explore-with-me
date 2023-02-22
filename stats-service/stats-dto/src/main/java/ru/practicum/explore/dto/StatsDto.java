package ru.practicum.explore.dto;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StatsDto implements Comparable<StatsDto> {
    String app;
    String uri;
    Long hits;

    @Override
    public int compareTo(StatsDto o) {
        return o.hits.compareTo(this.hits);
    }
}
