package ru.practicum.events.compilation.mapper;

import ru.practicum.events.compilation.dto.CompilationDto;
import ru.practicum.events.compilation.dto.NewCompilationDto;
import ru.practicum.events.compilation.model.Compilation;
import ru.practicum.events.event.mapper.EventMapper;
import ru.practicum.events.event.model.Event;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public final class CompilationMapper {
    private CompilationMapper(){}

    public static Compilation toCompilation(NewCompilationDto compilationDto, List<Event> collect) {
        return Compilation.builder()
                .events(new HashSet<>(collect))
                .title(compilationDto.getTitle())
                .pinned(compilationDto.getPinned())
                .build();
    }

    public static CompilationDto toCompilationDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .events(compilation.getEvents()
                        .stream()
                        .map(EventMapper::toEventFullDto)
                        .collect(Collectors.toList())
                )
                .pinned(compilation.isPinned())
                .title(compilation.getTitle())
                .build();
    }
}
