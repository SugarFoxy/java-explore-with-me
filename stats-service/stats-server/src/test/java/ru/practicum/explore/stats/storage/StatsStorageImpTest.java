package ru.practicum.explore.stats.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.explore.dto.StatsDto;
import ru.practicum.explore.hit.model.Hit;
import ru.practicum.explore.hit.storage.HitRepository;
import ru.practicum.util.DateTimeUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class StatsStorageImpTest {
    final LocalDateTime start = DateTimeUtils.parseDate("2022-09-06 11:00:00");
    final LocalDateTime end = DateTimeUtils.parseDate("2022-09-06 12:00:00");
    @Autowired
    private EntityManager entityManager;
    @Autowired
    HitRepository hitRepository;

    StatsStorageImp statsStorageImp;


    @BeforeEach
    void saveHit() {
        statsStorageImp = new StatsStorageImp(entityManager);
        Hit hit1 = Hit.builder()
                .app("ewm-main-service")
                .ip("192.163.0.1")
                .uri("/events/1")
                .timestamp(DateTimeUtils.parseDate("2022-09-06 11:00:23"))
                .build();
        Hit hit21 = Hit.builder()
                .app("ewm-main-service")
                .ip("192.163.0.1")
                .uri("/events/1")
                .timestamp(DateTimeUtils.parseDate("2022-09-06 11:00:23"))
                .build();
        Hit hit2 = Hit.builder()
                .app("ewm-main-service")
                .ip("192.163.0.1")
                .uri("/events/2")
                .timestamp(DateTimeUtils.parseDate("2022-09-06 11:00:24"))
                .build();
        entityManager.persist(hitRepository.save(hit1));
        entityManager.persist(hitRepository.save(hit21));
        entityManager.persist(hitRepository.save(hit2));
    }

    @AfterEach
    public void afterEach() {
        hitRepository.deleteAll();
    }

    @Test
    void getStats_whenUniqueFalse() {
        List<StatsDto> stats = statsStorageImp.getStats(start, end, null, false);
        assertEquals(2, stats.size());
        assertEquals(stats.get(0).getHits(), 2);
    }

    @Test
    void getStats_whenUniqueTrue() {
        List<StatsDto> stats = statsStorageImp.getStats(start, end, null, true);
        assertEquals(2, stats.size());
        assertEquals(stats.get(0).getHits(), 1);
    }

    @Test
    void getStats_whenUriFound() {
        List<StatsDto> stats = statsStorageImp.getStats(start, end, "/events/1", true);
        assertEquals(1, stats.size());
        assertEquals(stats.get(0).getHits(), 1);
    }
}