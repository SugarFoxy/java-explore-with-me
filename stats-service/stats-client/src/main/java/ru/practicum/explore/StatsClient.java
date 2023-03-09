package ru.practicum.explore;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.practicum.explore.dto.HitDto;
import ru.practicum.explore.dto.StatsDto;
import ru.practicum.util.DateTimeUtils;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatsClient extends BaseClient {
    private final Integer START_DATE_OFFSET = 6;

    public StatsClient(@Value("${stats-server.url}") String serverUrl) {
        super(serverUrl);
    }

    public void hitRequest(HitDto hitDto) {
        post("/hit", hitDto);
    }

    public Optional<Long> statsRequest(Long eventId) {
        String start = DateTimeUtils.getDateTime(LocalDateTime.now().minusMonths(START_DATE_OFFSET));
        String end = DateTimeUtils.getDateTime(LocalDateTime.now());

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("start", start);
        parameters.add("end", end);
        parameters.add("uris", String.format("/events/%d",eventId));

        List<StatsDto> stats = get("/stats", parameters);
        if (stats != null) {
            if (stats.isEmpty()) {
                return Optional.empty();
            }
            StatsDto viewStats = stats.get(0);
            if (viewStats != null) {
                return Optional.of(viewStats.getHits());
            }
        }
        return Optional.empty();
    }

    public Map<Long, Long> viewsMapRequest(List<Long> eventIds) {
        List<String> uris = eventIds.stream().map(eventId -> "/events/" + eventId).collect(Collectors.toList());
        String start = DateTimeUtils.getDateTime(LocalDateTime.now().minusMonths(START_DATE_OFFSET));
        String end = DateTimeUtils.getDateTime(LocalDateTime.now());

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("start", start);
        parameters.add("end", end);

        for (String uri : uris) {
            parameters.add("uris", uri);
        }

        List<StatsDto> statsArray = get("/stats", parameters);
        if (statsArray != null) {
            Map<Long, Long> viewsMap = new HashMap<>();
            statsArray.forEach(
                    viewStat -> {
                        String eventId = viewStat.getUri().split("/")[2];
                        viewsMap.put(Long.parseLong(eventId), viewStat.getHits());
                    }
            );
            return viewsMap;
        } else {
            return new HashMap<>();
        }
    }
}
