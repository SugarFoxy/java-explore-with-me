package ru.practicum.events.compilation.controller;

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
public class ControllerPublicCompilation {
    private final CompilationPublicService service;

    @Autowired
    public ControllerPublicCompilation(CompilationPublicService service) {
        this.service = service;
    }

    @GetMapping
    public List<CompilationDto> getAllCompilations() {
        return service.getAllCompilations();
    }

    @GetMapping("{compId}")
    public CompilationDto getCompilationById(@PathVariable Long compId) {
        return service.getCompilationById(compId);
    }
}
