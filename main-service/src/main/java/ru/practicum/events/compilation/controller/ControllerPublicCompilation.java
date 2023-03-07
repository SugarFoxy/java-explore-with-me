package ru.practicum.events.compilation.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public List<CompilationDto> getAllCompilations() {
        log.info("Получен запрос на получение подборок событий");
        return service.getAllCompilations();
    }

    @GetMapping("{compId}")
    public CompilationDto getCompilationById(@PathVariable Long compId) {
        log.info("Получен запрос на получение подборок событий по его id = {}", compId);
        return service.getCompilationById(compId);
    }
}
