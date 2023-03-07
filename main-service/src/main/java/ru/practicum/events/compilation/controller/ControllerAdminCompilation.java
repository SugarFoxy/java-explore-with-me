package ru.practicum.events.compilation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.compilation.dto.CompilationDto;
import ru.practicum.events.compilation.dto.NewCompilationDto;
import ru.practicum.events.compilation.dto.UpdateCompilationRequest;
import ru.practicum.events.compilation.service.CompilationAdminService;
import ru.practicum.util.exception.handler.CustomExceptionHandler;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/compilations")
@CustomExceptionHandler
public class ControllerAdminCompilation {
    private final CompilationAdminService compilationService;

    @Autowired
    public ControllerAdminCompilation(CompilationAdminService compilationService) {
        this.compilationService = compilationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@Valid @RequestBody NewCompilationDto compilationDto) {
        return compilationService.createCompilation(compilationDto);
    }

    @DeleteMapping("{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Long compId) {
        compilationService.deleteCompilation(compId);
    }

    @PatchMapping("{compId}")
    public CompilationDto updateCompilation(@PathVariable Long compId,
                                            @RequestBody UpdateCompilationRequest dto) {
        return compilationService.updateCompilation(compId, dto);

    }
}
