package ru.practicum.explore.stats.storage;

import ru.practicum.explore.dto.StatsDto;

import java.time.LocalDateTime;
import java.util.List;


public interface StatsStorage {
    List<StatsDto> getStats(LocalDateTime start, LocalDateTime end, String uri, boolean unique);
}
