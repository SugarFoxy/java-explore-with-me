package ru.practicum.events.compilation.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.compilation.dto.CompilationDto;
import ru.practicum.events.compilation.service.CompilationPublicService;
import ru.practicum.util.exception.handler.CustomExceptionHandler;

import java.util.List;

@RestController
@RequestMapping("/compilations")
@CustomExceptionHandler
@Slf4j
public class ControllerPublicCompilation {
    private final CompilationPublicService service;

    @Autowired
    public ControllerPublicCompilation(CompilationPublicService service) {
        this.service = service;
    }

    @GetMapping
    public List<CompilationDto> getAllCompilations(@RequestParam(required = false) Boolean pinned,
                                                   @RequestParam(defaultValue = "0") Integer from,
                                                   @RequestParam(defaultValue = "10") Integer size) {
        log.info("Получен запрос на получение подборок событий");
        return service.getAllCompilations(pinned, from, size);
    }

    @GetMapping("{compId}")
    public CompilationDto getCompilationById(@PathVariable Long compId) {
        log.info("Получен запрос на получение подборок событий по его id = {}", compId);
        return service.getCompilationById(compId);
    }
}