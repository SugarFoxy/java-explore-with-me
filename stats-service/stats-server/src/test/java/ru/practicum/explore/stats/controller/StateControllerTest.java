package ru.practicum.explore.stats.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.practicum.explore.stats.service.StatsService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatsController.class)
@AutoConfigureMockMvc
class StateControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    @Qualifier("statsServiceImp")
    StatsService service;

    @SneakyThrows
    @Test
    void getStats_whenCorrectParam_thenReturnedClientError() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("start", "2022-09-06 11:00:00");
        params.add("end", "2022-09-06 12:00:00");
        mockMvc.perform(get("/stats").params(params))
                .andExpect(status().is2xxSuccessful());
    }
}