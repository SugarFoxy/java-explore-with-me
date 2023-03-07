package ru.practicum.events.compilation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.events.compilation.dto.CompilationDto;
import ru.practicum.events.compilation.dto.NewCompilationDto;
import ru.practicum.events.compilation.dto.UpdateCompilationRequest;
import ru.practicum.events.compilation.mapper.CompilationMapper;
import ru.practicum.events.compilation.model.Compilation;
import ru.practicum.events.compilation.service.CompilationAdminService;
import ru.practicum.events.compilation.storage.CompilationRepository;
import ru.practicum.util.RepositoryObjectCreator;

import java.util.stream.Collectors;

@Service
public class CompilationAdminServiceImp implements CompilationAdminService {
    private final CompilationRepository compilationRepository;
    private final RepositoryObjectCreator objectCreator;

    @Autowired
    public CompilationAdminServiceImp(CompilationRepository compilationRepository,
                                      RepositoryObjectCreator repositoryObjectCreator) {
        this.compilationRepository = compilationRepository;
        this.objectCreator = repositoryObjectCreator;
    }

    @Override
    public CompilationDto createCompilation(NewCompilationDto compilationDto) {
        Compilation compilation = CompilationMapper.toCompilation(compilationDto,
                compilationDto.getEvents()
                        .stream()
                        .map(objectCreator::getEventById)
                        .collect(Collectors.toList())
        );
        return CompilationMapper.toCompilationDto(compilationRepository.save(compilation));
    }

    @Override
    public void deleteCompilation(Long compId) {
        compilationRepository.deleteById(compId);
    }

    @Override
    public CompilationDto updateCompilation(Long compId, UpdateCompilationRequest dto) {
        Compilation compilation = objectCreator.getCompilationById(compId);
        if (dto.getEvents() != null) {
            compilation.setEvents(dto.getEvents()
                    .stream()
                    .map(objectCreator::getEventById)
                    .collect(Collectors.toSet()));
        }
        if (dto.getPinned() != null) {
            compilation.setPinned(dto.getPinned());
        }
        if (dto.getTitle() != null) {
            compilation.setTitle(dto.getTitle());
        }

        return CompilationMapper.toCompilationDto(compilationRepository.save(compilation));
    }
}
