package ru.practicum.explore.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


class HitDtoTest {
    private JacksonTester<HitDto> jtHitDto;
    ObjectMapper objectMapper;

    @BeforeEach
    public void createContext() {
        objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    public void itemDtoJsonTest() throws IOException {
        HitDto hitDto = HitDto.builder()
                .id(1L)
                .app("ewm-main-service")
                .ip("192.163.0.1")
                .uri("/events/1")
                .timestamp("2022-09-06 11:00:23")
                .build();

        JsonContent<HitDto> itemDtoResult = jtHitDto.write(hitDto);

        assertThat(itemDtoResult).extractingJsonPathNumberValue("$.id").isEqualTo(hitDto.getId().intValue());
        assertThat(itemDtoResult).extractingJsonPathStringValue("$.app").isEqualTo(hitDto.getApp());
        assertThat(itemDtoResult).extractingJsonPathStringValue("$.uri").isEqualTo(hitDto.getUri());
        assertThat(itemDtoResult).extractingJsonPathStringValue("$.ip").isEqualTo(hitDto.getIp());
        assertThat(itemDtoResult).extractingJsonPathValue("$.timestamp").isEqualTo(hitDto.getTimestamp());
    }
}