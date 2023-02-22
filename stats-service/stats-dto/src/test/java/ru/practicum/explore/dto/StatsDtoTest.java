package ru.practicum.explore.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


class StatsDtoTest {
    private JacksonTester<StatsDto> jtHitDto;
    ObjectMapper objectMapper;

    @BeforeEach
    public void createContext() {
        objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    public void itemDtoJsonTest() throws IOException {
        StatsDto statsDto = StatsDto.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .hits(6L)
                .build();

        JsonContent<StatsDto> itemDtoResult = jtHitDto.write(statsDto);
        assertThat(itemDtoResult).extractingJsonPathStringValue("$.app").isEqualTo(statsDto.getApp());
        assertThat(itemDtoResult).extractingJsonPathStringValue("$.uri").isEqualTo(statsDto.getUri());
        assertThat(itemDtoResult).extractingJsonPathValue("$.hits").isEqualTo(statsDto.getHits().intValue());
    }
}