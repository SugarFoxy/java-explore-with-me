package ru.practicum.explore.stats.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.explore.dto.StatsDto;
import ru.practicum.explore.stats.storage.StatsStorage;
import ru.practicum.util.DateTimeUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatsServiceImp implements StatsService {
    private final StatsStorage storage;

    @Autowired
    public StatsServiceImp(StatsStorage storage) {
        this.storage = storage;
    }

    @Override
    public List<StatsDto> getStats(String start, String end, List<String> uri, boolean unique) {
        LocalDateTime parsStart = DateTimeUtils.parseDate(start);
        LocalDateTime parsEnd = DateTimeUtils.parseDate(end);

        if (uri.isEmpty()) {
            return storage.getStats(parsStart, parsEnd, null, unique)
                    .stream()
                    .sorted(StatsDto::compareTo)
                    .collect(Collectors.toList());
        } else {
            return uri
                    .stream()
                    .flatMap(url -> storage.getStats(parsStart, parsEnd, url.substring(1,url.length()-1), unique).stream())
                    .sorted(StatsDto::compareTo)
                    .collect(Collectors.toList());
        }
    }
}
