package ru.practicum.explore.stats.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.practicum.explore.dto.StatsDto;
import ru.practicum.explore.stats.storage.StatsStorageImp;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class StatsServiceImpTest {
    @Mock
    StatsStorageImp storage;
    @InjectMocks
    StatsServiceImp service;

    @Test
    void getStats_whenUriNotFound_thenReturnedList() {
        StatsDto statsDto = new StatsDto();
        List<StatsDto> statsDtoOutRep = List.of(statsDto);
        when(storage.getStats(any(), any(), any(), anyBoolean())).thenReturn(statsDtoOutRep);

        List<StatsDto> usersDto = service.getStats("2022-09-06 11:00:00", "2022-09-06 12:00:00", List.of(), false);

        verify(storage).getStats(any(), any(), any(), anyBoolean());
        assertEquals(1, usersDto.size());
        assertEquals(statsDto, usersDto.get(0));
    }

    @Test
    void getStats_whenUriFound_thenReturnedList() {
        StatsDto statsDto = new StatsDto();
        statsDto.setHits(3L);
        List<StatsDto> statsDtoOutRep = List.of(statsDto);
        List<String> uri = List.of("/event/1", "/event/2");
        when(storage.getStats(any(), any(), any(), anyBoolean())).thenReturn(statsDtoOutRep);

        List<StatsDto> usersDto = service.getStats("2022-09-06 11:00:00", "2022-09-06 12:00:00", uri, false);

        verify(storage, times(2)).getStats(any(), any(), any(), anyBoolean());
        assertEquals(2, usersDto.size());
        assertEquals(statsDto, usersDto.get(0));
        assertEquals(statsDto, usersDto.get(1));
    }
}