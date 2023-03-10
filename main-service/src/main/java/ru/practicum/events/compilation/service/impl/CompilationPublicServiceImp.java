package ru.practicum.events.compilation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.events.compilation.dto.CompilationDto;
import ru.practicum.events.compilation.mapper.CompilationMapper;
import ru.practicum.events.compilation.service.CompilationPublicService;
import ru.practicum.events.compilation.storage.CompilationRepository;
import ru.practicum.util.RepositoryObjectCreator;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompilationPublicServiceImp implements CompilationPublicService {
    private final CompilationRepository compilationRepository;
    private final RepositoryObjectCreator objectCreator;

    @Autowired
    public CompilationPublicServiceImp(CompilationRepository compilationRepository,
                                       RepositoryObjectCreator objectCreator) {
        this.compilationRepository = compilationRepository;
        this.objectCreator = objectCreator;
    }

    @Override
    public List<CompilationDto> getAllCompilations(Boolean pinned, int from, int size) {
        return compilationRepository.findAllByPinned(pinned, PageRequest.of(from,size))
                .stream()
                .map(CompilationMapper::toCompilationDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilationById(Long compId) {
        return CompilationMapper.toCompilationDto(objectCreator.getCompilationById(compId));
    }
}