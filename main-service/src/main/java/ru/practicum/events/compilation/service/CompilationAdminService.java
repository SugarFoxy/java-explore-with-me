package ru.practicum.events.compilation.service;

import ru.practicum.events.compilation.dto.CompilationDto;
import ru.practicum.events.compilation.dto.NewCompilationDto;
import ru.practicum.events.compilation.dto.UpdateCompilationRequest;

public interface CompilationAdminService {
    CompilationDto createCompilation(NewCompilationDto compilationDto);

    void deleteCompilation(Long compId);

    CompilationDto updateCompilation(Long compId, UpdateCompilationRequest dto);
}
