package ru.practicum.explore.hit.mapper;

import ru.practicum.explore.dto.HitDto;
import ru.practicum.explore.hit.model.Hit;
import ru.practicum.util.DateTimeUtils;

public class HitMapper {
    public static Hit toHit(HitDto dto) {
        return Hit.builder()
                .id(dto.getId())
                .uri(dto.getUri())
                .ip(dto.getIp())
                .app(dto.getApp())
                .timestamp(DateTimeUtils.parseDate(dto.getTimestamp()))
                .build();
    }
}
