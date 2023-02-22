package ru.practicum.explore.stats.service;

import ru.practicum.explore.dto.StatsDto;

import java.util.List;

public interface StatsService {
    List<StatsDto> getStats(String start, String end, List<String> uris, boolean unique);
}
