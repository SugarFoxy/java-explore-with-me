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

    public StatsClient(@Value("${stats-server.url}") String serverUrl) {
        super(serverUrl);
    }

    public void hitRequest(HitDto hitDto) {
        post("/hit", hitDto);
    }

    public Optional<Long> statsRequest(Long eventId) {
        List<String> uris = List.of(String.format("/events/%d",eventId));

        String start = DateTimeUtils.getDateTime(LocalDateTime.now().minusMonths(6));
        String end = DateTimeUtils.getDateTime(LocalDateTime.now().plusSeconds(10));

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("start", start);
        parameters.add("end", end);
        parameters.add("uris", uris.toString());

        List<StatsDto> statsArray = get("/stats", parameters);
        if (statsArray != null) {
            if (statsArray.size() == 0) {
                return Optional.of(0L);
            }
            StatsDto viewStats = statsArray.get(0);
            if (viewStats != null) {
                return Optional.of(viewStats.getHits());
            }
        }
        return Optional.empty();
    }

    public Map<Long, Long> viewsMapRequest(List<Long> eventIds) {
        List<String> uris = eventIds.stream().map(eventId -> "/events/" + eventId).collect(Collectors.toList());
        String start = DateTimeUtils.getDateTime(LocalDateTime.now().minusMonths(6));
        String end = DateTimeUtils.getDateTime(LocalDateTime.now().plusSeconds(10));

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("start", start);
        parameters.add("end", end);
        parameters.add("uris", uris.toString());

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
