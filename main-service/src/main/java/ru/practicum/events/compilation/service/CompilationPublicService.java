package ru.practicum.events.compilation.service;

import ru.practicum.events.compilation.dto.CompilationDto;

import java.util.List;

public interface CompilationPublicService {
    List<CompilationDto> getAllCompilations();

    CompilationDto getCompilationById(Long compId);
}
