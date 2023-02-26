package ru.practicum.explore.hit.mapper;

import org.junit.jupiter.api.Test;
import ru.practicum.explore.dto.HitDto;
import ru.practicum.explore.hit.model.Hit;
import ru.practicum.util.DateTimeUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HitMapperTest {
    @Test
    void toHit() {
        HitDto hitDto = HitDto.builder()
                .id(1L)
                .app("ewm-main-service")
                .ip("192.163.0.1")
                .uri("/events/1")
                .timestamp("2022-09-06 11:00:23")
                .build();
        Hit hit = HitMapper.toHit(hitDto);
        assertEquals(hitDto.getId(), hit.getId());
        assertEquals(hitDto.getApp(), hit.getApp());
        assertEquals(hitDto.getUri(), hit.getUri());
        assertEquals(hitDto.getIp(), hit.getIp());
        assertEquals(DateTimeUtils.parseDate(hitDto.getTimestamp()), hit.getTimestamp());
    }
}