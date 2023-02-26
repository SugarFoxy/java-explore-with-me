package ru.practicum.explore.hit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.explore.dto.HitDto;
import ru.practicum.explore.hit.service.HitService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HitController.class)
@AutoConfigureMockMvc
class HitControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    @Qualifier("hitServiceImp")
    HitService service;
    HitDto hitDto;

    @BeforeEach
    void init() {
        hitDto = HitDto.builder()
                .app("ewm-main-service")
                .ip("192.163.0.1")
                .uri("/events/1")
                .timestamp("2022-09-06 11:00:23")
                .build();
    }

    @SneakyThrows
    @Test
    void saveHit_whenAppBlank_thenReturnedClientError() {
        hitDto.setApp("");
        mockMvc.perform(post("/hit")
                        .content(objectMapper.writeValueAsString(hitDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @SneakyThrows
    @Test
    void saveHit_whenUriBlank_thenReturnedClientError() {
        hitDto.setUri("");
        mockMvc.perform(post("/hit")
                        .content(objectMapper.writeValueAsString(hitDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @SneakyThrows
    @Test
    void saveHit_whenIpBlank_thenReturnedClientError() {
        hitDto.setIp("");
        mockMvc.perform(post("/hit")
                        .content(objectMapper.writeValueAsString(hitDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @SneakyThrows
    @Test
    void saveHit_whenTimestampBlank_thenReturnedClientError() {
        hitDto.setTimestamp("");
        mockMvc.perform(post("/hit")
                        .content(objectMapper.writeValueAsString(hitDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @SneakyThrows
    @Test
    void saveHit_whenAppBlank_thenReturnedOk() {
        mockMvc.perform(post("/hit")
                        .content(objectMapper.writeValueAsString(hitDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }
}