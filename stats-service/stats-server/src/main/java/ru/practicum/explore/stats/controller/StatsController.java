package ru.practicum.explore.stats.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explore.dto.StatsDto;
import ru.practicum.explore.stats.service.StatsService;
import ru.practicum.util.exception.handler.CustomExceptionHandler;

import java.util.List;

@RestController
@RequestMapping(path = "/stats")
@CustomExceptionHandler
@Slf4j
public class StatsController {
    private final StatsService service;

    @Autowired
    public StatsController(@Qualifier("statsServiceImp") StatsService service) {
        this.service = service;
    }

    @GetMapping
    public List<StatsDto> getStats(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(required = false, defaultValue = "false") boolean unique
    ) {
        log.info("Запрос на статистику");
        return service.getStats(start, end, uris, unique);
    }
}
