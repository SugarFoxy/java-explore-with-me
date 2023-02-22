package ru.practicum.explore.hit.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.practicum.explore.dto.HitDto;
import ru.practicum.explore.hit.storage.HitRepository;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class HitServiceImpTest {
    @Mock
    HitRepository repository;
    @InjectMocks
    HitServiceImp service;

    @Test
    void createHit_whenCorrect_thenSave() {
        HitDto hitDto = HitDto.builder()
                .uri("/events/1")
                .app("ewm-main-service")
                .ip("192.163.0.1")
                .timestamp("2022-09-06 11:00:23")
                .build();
        service.saveRequest(hitDto);
        verify(repository).save(any());
    }
}